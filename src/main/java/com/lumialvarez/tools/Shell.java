/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tools;

import com.lumialvarez.tools.system.OperativeSystem;
import com.lumialvarez.tools.system.Ping;
import com.lumialvarez.tools.system.ShellUtils;
import com.lumialvarez.tools.system.TraceRoute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author Lumialvarez
 */
public class Shell {
    private static final int DEFAULT_PING_REPETICIONES = 4;

    public static String executeCommand(String command) throws Exception {
        StringBuilder output = new StringBuilder();
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            InputStreamReader isr = new InputStreamReader(process.getInputStream(), "CP850");
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            throw new Exception("Error al ejecutar el comando del sistema", e);
        }
        return output.toString();
    }
    
    public static Ping ping(String endpoint) throws Exception {
        return ping(endpoint, DEFAULT_PING_REPETICIONES);
    }
    
    public static Ping ping(String endpoint, int repeticiones) throws Exception {
        System.out.println("Start Ping ("+ repeticiones + ") to " + endpoint);
        String output = "";
        switch (OperativeSystem.getCurrentOS()) {
            case OperativeSystem.WINDOWS:
                output = executeCommand("ping -n " + repeticiones + " " + endpoint);
                break;
            case OperativeSystem.LINUX:
                output = executeCommand("ping -c " + repeticiones + " " + endpoint);
                break;
            default:
                throw new UnsupportedOperationException("Traceroute not Implemented for " + OperativeSystem.getCurrentOS());
        }
        System.out.println("End Ping");
        return ShellUtils.extractPing(output);
    }

    public static List<TraceRoute> traceRoute(String endpoint) throws Exception {
        System.out.println("Start TraceRoute to " + endpoint);
        String output = "";
        switch (OperativeSystem.getCurrentOS()) {
            case OperativeSystem.WINDOWS:
                output = executeCommand("tracert " + endpoint);
                break;
            case OperativeSystem.LINUX:
                output = executeCommand("traceroute " + endpoint);
                break;
            default:
                throw new UnsupportedOperationException("Traceroute not Implemented for " + OperativeSystem.getCurrentOS());
        }
        System.out.println("End TraceRoute");
        return ShellUtils.extractTraceRoute(output);
    }
}
