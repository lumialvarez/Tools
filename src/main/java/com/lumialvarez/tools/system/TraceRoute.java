/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tools.system;

/**
 *
 * @author luigu
 */
public class TraceRoute {

    private int index;
    private String nombre;
    private String ip;
    private float tiempo1;
    private float tiempo2;
    private float tiempo3;
    private boolean alcanzable;
    private float tiempoAnalisis;

    public TraceRoute() {
        this.tiempo1 = 0;
        this.tiempo2 = 0;
        this.tiempo3 = 0;
        this.tiempoAnalisis = -1;
        this.alcanzable = true;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getTiempo1() {
        return tiempo1;
    }

    public void setTiempo1(float tiempo1) {
        this.tiempo1 = tiempo1;
    }

    public float getTiempo2() {
        return tiempo2;
    }

    public void setTiempo2(float tiempo2) {
        this.tiempo2 = tiempo2;
    }

    public float getTiempo3() {
        return tiempo3;
    }

    public void setTiempo3(float tiempo3) {
        this.tiempo3 = tiempo3;
    }

    public boolean isAlcanzable() {
        return alcanzable;
    }

    public void setAlcanzable(boolean alcanzable) {
        this.alcanzable = alcanzable;
    }

    public float getTiempoAnalisis() {
        return tiempoAnalisis;
    }

    public void setTiempoAnalisis(float tiempoAnalisis) {
        this.tiempoAnalisis = tiempoAnalisis;
    }

    @Override
    public String toString() {
        return "TraceRoute{" + "index=" + index + ", nombre=" + nombre + ", ip=" + ip + ", tiempo1=" + tiempo1 + ", tiempo2=" + tiempo2 + ", tiempo3=" + tiempo3 + ", alcanzable=" + alcanzable + ", tiempoAnalisis=" + tiempoAnalisis + '}';
    }

}
