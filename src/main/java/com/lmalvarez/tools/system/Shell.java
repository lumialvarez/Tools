/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lmalvarez.tools.system;

import com.lmalvarez.tools.system.model.Ping;
import com.lmalvarez.tools.system.model.TraceRoute;
import com.lmalvarez.tools.system.utils.ShellUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static List<Ping> ping(List<String> endpoints) throws Exception {
        return ping(endpoints, DEFAULT_PING_REPETICIONES);
    }

    public static List<Ping> ping(List<String> endpoints, int repeticiones) throws Exception {
        List<Ping> pings = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        endpoints.stream().map(s -> new Thread() {
            @Override
            public void run() {
                try {
                    pings.add(ping(s, repeticiones));
                } catch (Exception ex) {
                    Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).forEachOrdered(thread -> {
            executor.execute(thread);
        });
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        return pings;
    }

    public static Ping ping(String endpoint) throws Exception {
        return ping(endpoint, DEFAULT_PING_REPETICIONES);
    }

    public static Ping ping(String endpoint, int repeticiones) throws Exception {
        System.out.println("Start Ping (" + repeticiones + ") to " + endpoint);
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
        System.out.println("End Ping to " + endpoint);
        return ShellUtils.extractPing(output, endpoint);
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

    public static List<TraceRoute> traceRoute(String endpoint, boolean verificacionAlcanzable, boolean analisisCompleto) throws Exception {
        List<TraceRoute> lstTraceRoute = traceRoute(endpoint);
        if (verificacionAlcanzable) {
            List<String> list = new ArrayList<>();
            for (TraceRoute tr : lstTraceRoute) {
                if (tr.isAlcanzable()) {
                    list.add(tr.getIp());
                }
            }
            List<Ping> lstPing = ping(list);

            for (TraceRoute tr : lstTraceRoute) {
                for(Ping p : lstPing){
                    if(tr.getIp().equals(p.getEndpoint())){
                        tr.setAlcanzable(p.getRecibidos()>0);
                    }
                }
            }
        }
        
        if (analisisCompleto) {
            List<String> list = new ArrayList<>();
            for (TraceRoute tr : lstTraceRoute) {
                if (tr.isAlcanzable()) {
                    list.add(tr.getIp());
                }
            }
            List<Ping> lstPing = ping(list, 20);

            for (TraceRoute tr : lstTraceRoute) {
                for(Ping p : lstPing){
                    if(tr.getIp().equals(p.getEndpoint())){
                        tr.setTiempoAnalisis(p.getMedia());
                    }
                }
            }
        }
        return lstTraceRoute;
    }
}
