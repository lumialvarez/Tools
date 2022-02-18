/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lmalvarez.tools.system.model;

/**
 *
 * @author lmalvarez
 */
public class PingEco {

    private int secuencia;
    private double tiempo;
    private int ttl;

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "PingEco{" + "secuencia=" + secuencia + ", tiempo=" + tiempo + ", ttl=" + ttl + '}';
    }

}
