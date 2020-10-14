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

    public static List<TraceRoute> interpretWindows(String text) {
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
            System.out.println(lines[i]);
            String linea = lines[i].replaceAll(" ms", "");

            TraceRoute traceRoute = new TraceRoute();
            //1     5 ms     9 ms     5 ms  192.168.0.1 
            try {
                StringTokenizer tokens = new StringTokenizer(linea);
                String tIntex = tokens.nextToken();
                String tTiempo1 = tokens.nextToken();
                String tTiempo2 = tokens.nextToken();
                String tTiempo3 = tokens.nextToken();
                String tEndpoint = tokens.nextToken();
                String tIp;
                if (tokens.hasMoreTokens()) {
                    tIp = tokens.nextToken();
                    tIp = tIp.replace("[", "").replace("]", "");
                } else {
                    tIp = tEndpoint;
                }

                traceRoute.setIndex(Integer.parseInt(tIntex));
                traceRoute.setTiempo1(Float.parseFloat(tTiempo1));
                traceRoute.setTiempo2(Float.parseFloat(tTiempo2));
                traceRoute.setTiempo3(Float.parseFloat(tTiempo3));
                traceRoute.setNombre(tEndpoint);
                traceRoute.setIp(tIp);

                list.add(traceRoute);
            } catch (NumberFormatException numberFormatException) {
            }

        }

        return list;
    }
}
