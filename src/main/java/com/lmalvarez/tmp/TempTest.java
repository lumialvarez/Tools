/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lmalvarez.tmp;

import com.lmalvarez.tools.Shell;
import com.lmalvarez.tools.system.TraceRoute;

import java.util.List;

/**
 *
 * @author luigu
 */
public class TempTest {

    private static String pingExampleWindows = "Haciendo ping a www.google.com [172.217.8.68] con 32 bytes de datos:\n"
            + "Respuesta desde 172.217.8.68: bytes=32 tiempo=85ms TTL=115\n"
            + "Respuesta desde 172.217.8.68: bytes=32 tiempo=63ms TTL=115\n"
            + "Tiempo de espera agotado para esta solicitud.\n"
            + "Respuesta desde 172.217.8.68: bytes=32 tiempo=59ms TTL=115\n"
            + "\n"
            + "Estadísticas de ping para 172.217.8.68:\n"
            + "    Paquetes: enviados = 4, recibidos = 3, perdidos = 1\n"
            + "    (25% perdidos),\n"
            + "Tiempos aproximados de ida y vuelta en milisegundos:\n"
            + "    Mínimo = 59ms, Máximo = 85ms, Media = 68ms"
            + "";
    private static String pingExampleLinux = "PING www.google.com (172.217.2.68) 56(84) bytes of data.\n"
            + "64 bytes from ord08s13-in-f4.1e100.net (172.217.2.68): icmp_seq=1 ttl=114 time=55.2 ms\n"
            + "64 bytes from ord08s13-in-f4.1e100.net (172.217.2.68): icmp_seq=2 ttl=114 time=53.6 ms\n"
            + "64 bytes from ord08s13-in-f4.1e100.net (172.217.2.68): icmp_seq=4 ttl=114 time=51.2 ms\n"
            + "\n"
            + "--- www.google.com ping statistics ---\n"
            + "4 packets transmitted, 4 received, 0% packet loss, time 8ms\n"
            + "rtt min/avg/max/mdev = 51.241/53.054/55.174/1.495 ms";

    public static void main(String args[]) throws Exception {
        //System.out.println(Shell.executeCommand("tracert www.google.com"));

        List<TraceRoute> list = Shell.traceRoute("www.google.com", true, true);

        for (TraceRoute tr : list) {
            System.out.println(tr.toString());
        }
        //System.getProperties().list(System.out);
        //Ping ping = ShellUtils.extractPingWindows(pingExampleWindows);
        //Ping ping = ShellUtils.extractPingLinux(pingExampleLinux);
//        Ping ping = Shell.ping("www.google.com");
//        
//        System.out.println(ping.toString());
//        for(PingEco eco : ping.getEcos()){
//            System.out.println(eco.toString());
//        }
//        List<String> list = new ArrayList<>();
//        list.add("192.168.0.1");
//        list.add("192.168.1.254");
//        list.add("www.google.com");
//
//        List<Ping> pings = Shell.ping(list);
//
//        for (Ping p : pings) {
//            System.out.println(p.toString());
//            for (PingEco eco : p.getEcos()) {
//                System.out.println(eco.toString());
//            }
//        }
    }

}
