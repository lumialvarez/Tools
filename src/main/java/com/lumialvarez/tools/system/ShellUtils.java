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

    public static Ping extractPing(String text, String endpoint) throws Exception {
        switch (OperativeSystem.getCurrentOS()) {
            case OperativeSystem.WINDOWS:
                return extractPingWindows(text, endpoint);
            case OperativeSystem.LINUX:
                return extractPingLinux(text, endpoint);
            default:
                throw new UnsupportedOperationException("Traceroute not Implemented for " + OperativeSystem.getCurrentOS());
        }
    }

    public static Ping extractPingWindows(String text, String endpoint) {
        Ping ping = new Ping();
        ping.setEndpoint(endpoint);
        int index = 0;
        int indexPrimerEco = -1;
        int indexUltimoEco = -1;
        int indexPaquetes = -1;
        int indexTiempos = -1;
        String[] lines = text.split("\n");
        for (String s : lines) {
            if (indexUltimoEco == -1) {
                if (indexPrimerEco == -1 && (s.contains("=") || s.toLowerCase().contains("tiempo") || s.toLowerCase().contains("time"))) {
                    indexPrimerEco = index;
                }
                if (indexPrimerEco >= 0 && s.length() == 0) {
                    indexUltimoEco = index - 1;
                }
            } else if (indexPaquetes == -1) {
                if (s.contains("=")) {
                    indexPaquetes = index;
                }
            } else if (indexTiempos == -1) {
                if (s.contains("=")) {
                    indexTiempos = index;
                    break;
                }
            }
            index++;
        }
        for (int i = indexPrimerEco; i <= indexUltimoEco; i++) {
            String linea = lines[i];
            if (linea.contains(":")) {
                linea = linea.replaceAll("ms", "").split(":")[1];
                StringTokenizer tokens = new StringTokenizer(linea);
                String tBytes = tokens.nextToken().split("=")[1];
                String tTiempo = tokens.nextToken().split("=")[1];
                String tTtl = tokens.nextToken().split("=")[1];
                PingEco eco = new PingEco();
                eco.setSecuencia(i + 1 - indexPrimerEco);
                eco.setTiempo(Float.parseFloat(tTiempo));
                eco.setTtl(Integer.parseInt(tTtl));
                ping.getEcos().add(eco);
            } else {
                PingEco eco = new PingEco();
                eco.setSecuencia(i + 1 - indexPrimerEco);
                eco.setTiempo(-1);
                eco.setTtl(-1);
                ping.getEcos().add(eco);
            }
        }
        if (indexPaquetes >= 0) {
            String lineaPaquetes = lines[indexPaquetes];
            StringTokenizer tokens = new StringTokenizer(lineaPaquetes);
            String tEnviados = null;
            String tRecibidos = null;
            String tPerdidos = null;
            while (tokens.hasMoreTokens()) {
                if (tokens.nextToken().equals("=")) {
                    if (tEnviados == null) {
                        tEnviados = tokens.nextToken().replaceAll(",", "");
                    } else if (tRecibidos == null) {
                        tRecibidos = tokens.nextToken().replaceAll(",", "");
                    } else if (tPerdidos == null) {
                        tPerdidos = tokens.nextToken().replaceAll(",", "");
                    }
                }
            }
            ping.setEnviados(Integer.parseInt(tEnviados));
            ping.setRecibidos(Integer.parseInt(tRecibidos));
            ping.setPerdidos(Integer.parseInt(tPerdidos));
        }
        if (indexTiempos >= 0) {
            String lineaTiempos = lines[indexTiempos].replaceAll("ms", "");
            StringTokenizer tokens = new StringTokenizer(lineaTiempos);
            String tMinimo = null;
            String tMaximo = null;
            String tMedia = null;
            while (tokens.hasMoreTokens()) {
                if (tokens.nextToken().equals("=")) {
                    if (tMinimo == null) {
                        tMinimo = tokens.nextToken().replaceAll(",", "");
                    } else if (tMaximo == null) {
                        tMaximo = tokens.nextToken().replaceAll(",", "");
                    } else if (tMedia == null) {
                        tMedia = tokens.nextToken().replaceAll(",", "");
                    }
                }
            }
            ping.setMinimo(Float.parseFloat(tMinimo));
            ping.setMaximo(Float.parseFloat(tMaximo));
            ping.setMedia(Float.parseFloat(tMedia));
        }

        return ping;
    }

    public static Ping extractPingLinux(String text, String endpoint) {
        Ping ping = new Ping();
        ping.setEndpoint(endpoint);
        int index = 0;
        int indexPrimerEco = -1;
        int indexUltimoEco = -1;
        int indexPaquetes = -1;
        int indexTiempos = -1;
        String[] lines = text.split("\n");
        for (String s : lines) {
            if (indexUltimoEco == -1) {
                if (indexPrimerEco == -1 && s.contains(":")) {
                    indexPrimerEco = index;
                }
                if (indexPrimerEco >= 0 && s.length() == 0) {
                    indexUltimoEco = index - 1;
                }
            }
            if (s.contains("---")) {
                indexPaquetes = index + 1;
                indexTiempos = index + 2;
                break;
            }
            index++;
        }
        for (int i = indexPrimerEco; i <= indexUltimoEco; i++) {
            String linea = lines[i];
            linea = linea.split(":")[1];
            StringTokenizer tokens = new StringTokenizer(linea);
            String tSeq = tokens.nextToken().split("=")[1];
            String tTtl = tokens.nextToken().split("=")[1];
            String tTiempo = tokens.nextToken().split("=")[1];
            PingEco eco = new PingEco();
            eco.setSecuencia(Integer.parseInt(tSeq));
            eco.setTiempo(Float.parseFloat(tTiempo));
            eco.setTtl(Integer.parseInt(tTtl));
            ping.getEcos().add(eco);
        }
        if (indexPaquetes >= 0) {
            String lineaPaquetes = lines[indexPaquetes];
            String[] items = lineaPaquetes.split(",");
            String tEnviados = null;
            String tRecibidos = null;
            tEnviados = items[0].trim().split(" ")[0].trim();
            tRecibidos = items[1].trim().split(" ")[0].trim();
            ping.setEnviados(Integer.parseInt(tEnviados));
            ping.setRecibidos(Integer.parseInt(tRecibidos));
            ping.setPerdidos(ping.getEnviados() - ping.getRecibidos());
        }
        if (indexTiempos >= 0) {
            String lineaTiempos = lines[indexTiempos];
            String[] items = lineaTiempos.split("=");
            String tMinimo = null;
            String tMaximo = null;
            String tMedia = null;
            lineaTiempos = items[1].trim();
            tMinimo = lineaTiempos.split("/")[0].trim();
            tMedia = lineaTiempos.split("/")[1].trim();
            tMaximo = lineaTiempos.split("/")[2].trim();
            ping.setMinimo(Float.parseFloat(tMinimo));
            ping.setMaximo(Float.parseFloat(tMaximo));
            ping.setMedia(Float.parseFloat(tMedia));
        }
        if (ping.getEnviados() > ping.getEcos().size()) {
            List<PingEco> tmp = new ArrayList<>();
            int indice = 0;
            for (int i = 1; i <= ping.getEnviados(); i++) {
                PingEco eco = new PingEco();
                if (ping.getEcos().size() >= (indice + 1) && ping.getEcos().get(indice).getSecuencia() == i) {
                    eco.setSecuencia(ping.getEcos().get(indice).getSecuencia());
                    eco.setTiempo(ping.getEcos().get(indice).getTiempo());
                    eco.setTtl(ping.getEcos().get(indice).getTtl());
                    indice++;
                } else {
                    eco.setSecuencia(i);
                    eco.setTiempo(-1);
                    eco.setTtl(-1);
                }
                tmp.add(eco);
            }
            ping.setEcos(tmp);
        }
        return ping;
    }
}
