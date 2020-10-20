/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tmp;

import com.lumialvarez.tools.Shell;
import com.lumialvarez.tools.system.TraceRoute;
import com.lumialvarez.tools.system.ShellUtils;
import java.util.List;

/**
 *
 * @author luigu
 */
public class TempTest {

    private static String traceExampleWindows = "\n"
            + "Traza a la dirección www.google.com [172.217.8.68]\n"
            + "sobre un máximo de 30 saltos:\n"
            + "\n"
            + "  1     5 ms     9 ms     5 ms  192.168.0.1 \n"
            + "  2     6 ms     4 ms     4 ms  192.168.1.254 \n"
            + "  3     *        *        *     Tiempo de espera agotado para esta solicitud.\n"
            + "  4    21 ms    14 ms    12 ms  10.166.41.1 \n"
            + "  5    16 ms    18 ms    27 ms  10.166.41.245 \n"
            + "  6    32 ms    23 ms    43 ms  static-adsl200-24-33-143.epm.net.co [200.24.33.143] \n"
            + "  7    27 ms    27 ms    30 ms  209.85.168.176 \n"
            + "  8    28 ms    23 ms    25 ms  108.170.236.203 \n"
            + "  9    24 ms    24 ms    48 ms  108.170.253.212 \n"
            + " 10    49 ms    38 ms    39 ms  64.233.175.235 \n"
            + " 11    73 ms    75 ms    73 ms  72.14.233.103 \n"
            + " 12    75 ms    59 ms    55 ms  209.85.247.83 \n"
            + " 13    77 ms    71 ms    71 ms  64.233.174.188 \n"
            + " 14    55 ms    59 ms    63 ms  108.170.249.17 \n"
            + " 15    68 ms    53 ms    64 ms  72.14.233.19 \n"
            + " 16    56 ms    53 ms    53 ms  mia07s47-in-f4.1e100.net [172.217.8.68] \n"
            + "\n"
            + "Traza completa.\n"
            + "";
    private static String traceExampleLinux = "traceroute to www.google.com (172.217.2.132), 30 hops max, 60 byte packets\n"
            + " 1  192.168.0.1 (192.168.0.1)  0.593 ms  0.311 ms  0.405 ms\n"
            + " 2  192.168.1.254 (192.168.1.254)  2.031 ms  2.556 ms  3.386 ms\n"
            + " 3  * * *\n"
            + " 4  10.166.41.1 (10.166.41.1)  19.748 ms  19.958 ms  20.659 ms\n"
            + " 5  10.166.41.245 (10.166.41.245)  21.716 ms  20.712 ms  20.823 ms\n"
            + " 6  static-adsl200-24-33-143.epm.net.co (200.24.33.143)  32.047 ms  23.190 ms  29.222 ms\n"
            + " 7  74.125.147.96 (74.125.147.96)  28.565 ms 209.85.168.176 (209.85.168.176)  24.485 ms 74.125.147.96 (74.125.147.96)  30.418 ms\n"
            + " 8  10.23.207.30 (10.23.207.30)  29.534 ms 108.170.253.215 (108.170.253.215)  30.760 ms 10.252.167.62 (10.252.167.62)  28.875 ms\n"
            + " 9  142.250.231.162 (142.250.231.162)  28.080 ms 74.125.252.60 (74.125.252.60)  36.968 ms 172.253.79.8 (172.253.79.8)  37.550 ms\n"
            + "10  108.170.231.115 (108.170.231.115)  70.846 ms 108.170.253.200 (108.170.253.200)  37.721 ms 108.170.253.196 (108.170.253.196)  38.083 ms\n"
            + "11  108.170.253.17 (108.170.253.17)  65.738 ms 209.85.252.109 (209.85.252.109)  46.653 ms 108.170.253.17 (108.170.253.17)  54.221 ms\n"
            + "12  108.170.253.1 (108.170.253.1)  69.520 ms 216.239.41.119 (216.239.41.119)  59.054 ms 216.239.59.15 (216.239.59.15)  59.668 ms\n"
            + "13  108.170.253.17 (108.170.253.17)  58.220 ms  56.906 ms  55.859 ms\n"
            + "14  216.239.59.61 (216.239.59.61)  59.491 ms 216.239.59.15 (216.239.59.15)  60.064 ms 216.239.59.61 (216.239.59.61)  63.625 ms\n"
            + "15  yyz08s14-in-f132.1e100.net (172.217.2.132)  53.996 ms  55.374 ms  54.792 ms";

    public static void main(String args[]) throws Exception {
        //System.out.println(Shell.executeCommand("tracert www.google.com"));

        List<TraceRoute> list = Shell.traceRoute("www.google.com");

        for (TraceRoute tr : list) {
            System.out.println(tr.toString());
        }

        //System.getProperties().list(System.out);
    }

}
