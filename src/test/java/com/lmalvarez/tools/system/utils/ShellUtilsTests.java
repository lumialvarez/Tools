package com.lmalvarez.tools.system.utils;


import com.lmalvarez.tools.system.model.Ping;
import com.lmalvarez.tools.system.model.TraceRoute;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ShellUtilsTests {

    @Test
    public void extractPingWindows4of4() {
        String endpoint = "www.google.com";
        String pingExampleWindows = "Haciendo ping a www.google.com [142.250.78.68] con 32 bytes de datos:\n" +
                "Respuesta desde 142.250.78.68: bytes=32 tiempo=31ms TTL=116\n" +
                "Respuesta desde 142.250.78.68: bytes=32 tiempo=42ms TTL=116\n" +
                "Respuesta desde 142.250.78.68: bytes=32 tiempo=27ms TTL=116\n" +
                "Respuesta desde 142.250.78.68: bytes=32 tiempo=32ms TTL=116\n" +
                "\n" +
                "Estadísticas de ping para 142.250.78.68:\n" +
                "    Paquetes: enviados = 4, recibidos = 4, perdidos = 0\n" +
                "    (0% perdidos),\n" +
                "Tiempos aproximados de ida y vuelta en milisegundos:\n" +
                "    Mínimo = 27ms, Máximo = 42ms, Media = 33ms\n";

        try {
            Ping ping = ShellUtils.extractPingWindows(pingExampleWindows, endpoint);

            assertEquals(ping.getEndpoint(),endpoint);
            assertEquals(ping.getEnviados(), 4);
            assertEquals(ping.getRecibidos(), 4);
            assertEquals(ping.getPerdidos(), 0);
            assertEquals(ping.getMaximo(), 42);
            assertEquals(ping.getMinimo(), 27);
            assertEquals(ping.getMedia(), 33);
            assertEquals(ping.getEcos().get(0).getSecuencia(), 1);
            assertEquals(ping.getEcos().get(0).getTiempo(), 31);
            assertEquals(ping.getEcos().get(0).getTtl(), 116);
            assertEquals(ping.getEcos().get(1).getSecuencia(), 2);
            assertEquals(ping.getEcos().get(1).getTiempo(), 42);
            assertEquals(ping.getEcos().get(1).getTtl(), 116);
            assertEquals(ping.getEcos().get(2).getSecuencia(), 3);
            assertEquals(ping.getEcos().get(2).getTiempo(), 27);
            assertEquals(ping.getEcos().get(2).getTtl(), 116);
            assertEquals(ping.getEcos().get(3).getSecuencia(), 4);
            assertEquals(ping.getEcos().get(3).getTiempo(), 32);
            assertEquals(ping.getEcos().get(3).getTtl(), 116);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void extractPingWindows3of4() {
        String endpoint = "www.google.com";
        String pingExampleWindows = "Haciendo ping a www.google.com [172.217.8.68] con 32 bytes de datos:\n"
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

        try {
            Ping ping = ShellUtils.extractPingWindows(pingExampleWindows, endpoint);

            assertEquals(ping.getEndpoint(),endpoint);
            assertEquals(ping.getEnviados(), 4);
            assertEquals(ping.getRecibidos(), 3);
            assertEquals(ping.getPerdidos(), 1);
            assertEquals(ping.getMaximo(), 85);
            assertEquals(ping.getMinimo(), 59);
            assertEquals(ping.getMedia(), 68);
            assertEquals(ping.getEcos().get(0).getSecuencia(), 1);
            assertEquals(ping.getEcos().get(0).getTiempo(), 85);
            assertEquals(ping.getEcos().get(0).getTtl(), 115);
            assertEquals(ping.getEcos().get(1).getSecuencia(), 2);
            assertEquals(ping.getEcos().get(1).getTiempo(), 63);
            assertEquals(ping.getEcos().get(1).getTtl(), 115);
            assertEquals(ping.getEcos().get(2).getSecuencia(), 3);
            assertEquals(ping.getEcos().get(2).getTiempo(), -1);
            assertEquals(ping.getEcos().get(2).getTtl(), -1);
            assertEquals(ping.getEcos().get(3).getSecuencia(), 4);
            assertEquals(ping.getEcos().get(3).getTiempo(), 59);
            assertEquals(ping.getEcos().get(3).getTtl(), 115);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void extractPingLinux4of4() {
        String endpoint = "www.google.com";
        String pingExampleLinux = "PING www.google.com (172.217.13.228) 56(84) bytes of data.\n" +
                "64 bytes from iad23s61-in-f4.1e100.net (172.217.13.228): icmp_seq=1 ttl=54 time=1.18 ms\n" +
                "64 bytes from iad23s61-in-f4.1e100.net (172.217.13.228): icmp_seq=2 ttl=54 time=1.15 ms\n" +
                "64 bytes from iad23s61-in-f4.1e100.net (172.217.13.228): icmp_seq=3 ttl=54 time=1.25 ms\n" +
                "64 bytes from iad23s61-in-f4.1e100.net (172.217.13.228): icmp_seq=4 ttl=54 time=1.14 ms\n" +
                "\n" +
                "--- www.google.com ping statistics ---\n" +
                "4 packets transmitted, 4 received, 0% packet loss, time 3004ms\n" +
                "rtt min/avg/max/mdev = 1.140/1.182/1.254/0.055 ms\n";

        try {
            Ping ping = ShellUtils.extractPingLinux(pingExampleLinux, endpoint);

            assertEquals(ping.getEndpoint(),endpoint);
            assertEquals(ping.getEnviados(), 4);
            assertEquals(ping.getRecibidos(), 4);
            assertEquals(ping.getPerdidos(), 0);
            assertEquals(ping.getMaximo(), 1.254);
            assertEquals(ping.getMinimo(), 1.14);
            assertEquals(ping.getMedia(), 1.182);
            assertEquals(ping.getEcos().get(0).getSecuencia(), 1);
            assertEquals(ping.getEcos().get(0).getTiempo(), 1.18);
            assertEquals(ping.getEcos().get(0).getTtl(), 54);
            assertEquals(ping.getEcos().get(1).getSecuencia(), 2);
            assertEquals(ping.getEcos().get(1).getTiempo(), 1.15);
            assertEquals(ping.getEcos().get(1).getTtl(), 54);
            assertEquals(ping.getEcos().get(2).getSecuencia(), 3);
            assertEquals(ping.getEcos().get(2).getTiempo(), 1.25);
            assertEquals(ping.getEcos().get(2).getTtl(), 54);
            assertEquals(ping.getEcos().get(3).getSecuencia(), 4);
            assertEquals(ping.getEcos().get(3).getTiempo(), 1.14);
            assertEquals(ping.getEcos().get(3).getTtl(), 54);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void extractTraceRouteWindows() {
        String traceRouteExampleWindows = "Traza a la dirección www.google.com [172.217.28.100]\n" +
                "sobre un máximo de 30 saltos:\n" +
                "\n" +
                "  1    33 ms    11 ms     3 ms  192.168.3.1\n" +
                "  2     6 ms     7 ms    11 ms  192.168.1.254\n" +
                "  3    18 ms    19 ms    14 ms  10.166.41.246\n" +
                "  4    17 ms    20 ms    26 ms  10.166.41.246\n" +
                "  5    15 ms    14 ms    23 ms  10.166.41.245\n" +
                "  6    33 ms    31 ms    29 ms  static-adsl200-24-33-143.epm.net.co [200.24.33.143]\n" +
                "  7    24 ms    39 ms    44 ms  209.85.168.176\n" +
                "  8    37 ms    31 ms    29 ms  108.170.236.203\n" +
                "  9    43 ms    43 ms    27 ms  74.125.252.61\n" +
                " 10    51 ms    27 ms    22 ms  bog02s07-in-f4.1e100.net [172.217.28.100]\n" +
                "\n" +
                "Traza completa.\n";

        try {
            List<TraceRoute> lstTracert = ShellUtils.extractTraceRouteWindows(traceRouteExampleWindows);

            assertEquals(lstTracert.size(), 10);

            assertEquals(lstTracert.get(0).getIndex(), 1);
            assertEquals(lstTracert.get(0).getIp(), "192.168.3.1");
            assertEquals(lstTracert.get(0).getNombre(), "192.168.3.1");
            assertEquals(lstTracert.get(0).getTiempo1(), 33);
            assertEquals(lstTracert.get(0).getTiempo2(), 11);
            assertEquals(lstTracert.get(0).getTiempo3(), 3);

            assertEquals(lstTracert.get(5).getIndex(), 6);
            assertEquals(lstTracert.get(5).getIp(), "200.24.33.143");
            assertEquals(lstTracert.get(5).getNombre(), "static-adsl200-24-33-143.epm.net.co");
            assertEquals(lstTracert.get(5).getTiempo1(), 33);
            assertEquals(lstTracert.get(5).getTiempo2(), 31);
            assertEquals(lstTracert.get(5).getTiempo3(), 29);

            assertEquals(lstTracert.get(9).getIndex(), 10);
            assertEquals(lstTracert.get(9).getIp(), "172.217.28.100");
            assertEquals(lstTracert.get(9).getNombre(), "bog02s07-in-f4.1e100.net");
            assertEquals(lstTracert.get(9).getTiempo1(), 51);
            assertEquals(lstTracert.get(9).getTiempo2(), 27);
            assertEquals(lstTracert.get(9).getTiempo3(), 22);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void extractTraceRouteLinux() {
        String traceRouteExampleLinux = "traceroute to www.google.com (142.250.65.68), 30 hops max, 60 byte packets\n" +
                " 1  216.182.230.251 (216.182.230.251)  57.599 ms 216.182.230.247 (216.182.230.247)  5.443 ms 216.182.226.200 (216.182.226.200)  21.536 ms\n" +
                " 2  100.66.12.102 (100.66.12.102)  17.270 ms 100.66.13.204 (100.66.13.204)  17.402 ms 100.65.65.64 (100.65.65.64)  9.423 ms\n" +
                " 3  100.66.32.28 (100.66.32.28)  6.243 ms 100.66.14.18 (100.66.14.18)  3.235 ms 100.66.14.172 (100.66.14.172)  20.293 ms\n" +
                " 4  100.66.6.175 (100.66.6.175)  20.741 ms 100.66.34.204 (100.66.34.204)  19.016 ms 100.66.6.103 (100.66.6.103)  17.132 ms\n" +
                " 5  100.66.5.121 (100.66.5.121)  38.241 ms 100.66.6.65 (100.66.6.65)  9.600 ms 100.66.5.169 (100.66.5.169)  21.975 ms\n" +
                " 6  100.65.15.33 (100.65.15.33)  0.342 ms 100.65.15.161 (100.65.15.161)  0.367 ms 100.65.15.209 (100.65.15.209)  10.665 ms\n" +
                " 7  52.93.29.55 (52.93.29.55)  0.790 ms 100.65.15.241 (100.65.15.241)  0.256 ms 52.93.29.45 (52.93.29.45)  0.717 ms\n" +
                " 8  100.100.36.32 (100.100.36.32)  1.025 ms 100.100.28.34 (100.100.28.34)  0.939 ms 52.93.29.43 (52.93.29.43)  1.003 ms\n" +
                " 9  100.100.28.106 (100.100.28.106)  5.422 ms 100.95.7.0 (100.95.7.0)  0.890 ms 99.83.113.89 (99.83.113.89)  3.428 ms\n" +
                "10  iad68f87-in-f4.1e100.net (100.100.36.34)  1.039 ms * *\n" +
                "11  100.100.36.40 (100.100.36.40)  0.909 ms 99.83.113.89 (99.83.113.89)  2.949 ms 108.170.240.97 (108.170.240.97)  1.908 ms\n" +
                "12  99.83.113.89 (99.83.113.89)  2.248 ms * *\n" +
                "13  209.85.251.82 (209.85.251.82)  1.132 ms  1.070 ms 74.125.252.38 (74.125.252.38)  3.367 ms\n" +
                "14  142.251.52.63 (142.251.52.63)  1.117 ms iad23s91-in-f4.1e100.net (142.250.65.68)  1.055 ms 216.239.47.126 (216.239.47.126)  1.743 ms\n";

        try {
            List<TraceRoute> lstTracert = ShellUtils.extractTraceRouteLinux(traceRouteExampleLinux);

            assertEquals(lstTracert.size(), 14);

            assertEquals(lstTracert.get(0).getIndex(), 1);
            assertEquals(lstTracert.get(0).getIp(), "216.182.230.251");
            assertEquals(lstTracert.get(0).getNombre(), "216.182.230.251");
            assertEquals(lstTracert.get(0).getTiempo1(), 57.599);
            assertEquals(lstTracert.get(0).getTiempo2(), 5.443);
            assertEquals(lstTracert.get(0).getTiempo3(), 21.536);

            assertEquals(lstTracert.get(5).getIndex(), 6);
            assertEquals(lstTracert.get(5).getIp(), "100.65.15.33");
            assertEquals(lstTracert.get(5).getNombre(), "100.65.15.33");
            assertEquals(lstTracert.get(5).getTiempo1(), 0.342);
            assertEquals(lstTracert.get(5).getTiempo2(), 0.367);
            assertEquals(lstTracert.get(5).getTiempo3(), 10.665);

            assertEquals(lstTracert.get(9).getIndex(), 10);
            assertEquals(lstTracert.get(9).getIp(), "100.100.36.34");
            assertEquals(lstTracert.get(9).getNombre(), "iad68f87-in-f4.1e100.net");
            assertEquals(lstTracert.get(9).getTiempo1(), 1.039);
            assertEquals(lstTracert.get(9).getTiempo2(), -1);
            assertEquals(lstTracert.get(9).getTiempo3(), -1);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
