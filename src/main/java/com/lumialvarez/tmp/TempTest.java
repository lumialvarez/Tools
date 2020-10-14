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

    private static String traceExample = "\n"
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

    public static void main(String args[]) throws Exception {
        //System.out.println(Shell.executeCommand("tracert www.google.com"));
        
        List<TraceRoute> list = ShellUtils.interpretWindows(traceExample);
        
        for(TraceRoute tr : list){
            System.out.println(tr.toString());
        }
    }

}
