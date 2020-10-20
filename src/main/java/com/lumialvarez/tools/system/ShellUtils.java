/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tools.system;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author luigu
 */
public class ShellUtils {

    public static List<TraceRoute> extractTraceRoute(String text) throws Exception {
        switch (OperativeSystem.getCurrentOS()) {
            case OperativeSystem.WINDOWS:
                return extractTraceRouteWindows(text);
            case OperativeSystem.LINUX:
                return extractTraceRouteLinux(text);
            default:
                throw new UnsupportedOperationException("Traceroute not Implemented for " + OperativeSystem.getCurrentOS());
        }
    }

    private static List<TraceRoute> extractTraceRouteWindows(String text) {
        List<TraceRoute> list = new ArrayList<>();
        int index = 0;
        int primerSalto = -1;
        int ultimoSalto = -1;
        String[] lines = text.split("\n");

        for (String s : lines) {
            if (s.contains(" 1 ")) {
                primerSalto = index;
            }

            if (primerSalto >= 0 && s.length() == 0) {
                ultimoSalto = index - 1;
                break;
            }
            index++;
        }

        for (int i = primerSalto; i <= ultimoSalto; i++) {
            String linea = lines[i].replaceAll(" ms", "");

            TraceRoute traceRoute = new TraceRoute();
            //1     5 ms     9 ms     5 ms  192.168.0.1 
            StringTokenizer tokens = new StringTokenizer(linea);
            String tIntex = tokens.nextToken();
            String tTiempo1 = tokens.nextToken();
            String tTiempo2 = tokens.nextToken();
            String tTiempo3 = tokens.nextToken();
            String tEndpoint = tokens.nextToken();
            String tIp;
            if (!tEndpoint.contains(".")) {
                tEndpoint = "unknown";
                tIp = "";
            } else {
                if (tokens.hasMoreTokens()) {
                    tIp = tokens.nextToken();
                    tIp = tIp.replace("[", "").replace("]", "");
                } else {
                    tIp = tEndpoint;
                }
            }

            traceRoute.setIndex(Integer.parseInt(tIntex));
            traceRoute.setTiempo1(tTiempo1.equals("*") ? -1 : Float.parseFloat(tTiempo1));
            traceRoute.setTiempo2(tTiempo2.equals("*") ? -1 : Float.parseFloat(tTiempo2));
            traceRoute.setTiempo3(tTiempo3.equals("*") ? -1 : Float.parseFloat(tTiempo3));
            traceRoute.setNombre(tEndpoint);
            traceRoute.setIp(tIp);

            if (traceRoute.getTiempo1() == -1 && traceRoute.getTiempo2() == -1 && traceRoute.getTiempo3() == -1) {
                traceRoute.setAlcanzable(false);
            }
            list.add(traceRoute);
        }

        return list;
    }

    private static List<TraceRoute> extractTraceRouteLinux(String text) {

        List<TraceRoute> list = new ArrayList<>();
        int index = 0;
        int primerSalto = -1;
        int ultimoSalto = -1;
        String[] lines = text.split("\n");

        for (String s : lines) {
            if (s.contains(" 1 ")) {
                primerSalto = index;
            }

            ultimoSalto = index;

            index++;
        }

        for (int i = primerSalto; i <= ultimoSalto; i++) {
            String linea = lines[i].replaceAll(" ms", "");

            TraceRoute traceRoute = new TraceRoute();
            //1     5 ms     9 ms     5 ms  192.168.0.1 

            StringTokenizer tokens = new StringTokenizer(linea);
            String tIntex = tokens.nextToken();

            String tEndpoint = tokens.nextToken();
            String tIp;
            String tTiempo1;
            if (tEndpoint.equals("*")) {
                tTiempo1 = tEndpoint;
                tEndpoint = "unknown";
                tIp = "";
            } else {
                tIp = tokens.nextToken();
                tIp = tIp.replace("(", "").replace(")", "");
                tTiempo1 = tokens.nextToken();
            }

            String tTiempo2 = tokens.nextToken();
            if (tTiempo2.chars().filter(ch -> ch == '.').count() > 1) {
                tTiempo2 = tokens.nextToken();
                if (tTiempo2.chars().filter(ch -> ch == '.').count() > 1) {
                    tTiempo2 = tokens.nextToken();
                }
            }

            String tTiempo3 = tokens.nextToken();
            if (tTiempo3.chars().filter(ch -> ch == '.').count() > 1) {
                tTiempo3 = tokens.nextToken();
                if (tTiempo3.chars().filter(ch -> ch == '.').count() > 1) {
                    tTiempo3 = tokens.nextToken();
                }
            }

            traceRoute.setIndex(Integer.parseInt(tIntex));
            traceRoute.setTiempo1(tTiempo1.equals("*") ? -1 : Float.parseFloat(tTiempo1));
            traceRoute.setTiempo2(tTiempo2.equals("*") ? -1 : Float.parseFloat(tTiempo2));
            traceRoute.setTiempo3(tTiempo3.equals("*") ? -1 : Float.parseFloat(tTiempo3));
            traceRoute.setNombre(tEndpoint);
            traceRoute.setIp(tIp);
            
            if (traceRoute.getTiempo1() == -1 && traceRoute.getTiempo2() == -1 && traceRoute.getTiempo3() == -1) {
                traceRoute.setAlcanzable(false);
            }

            list.add(traceRoute);

        }

        return list;
    }
}
