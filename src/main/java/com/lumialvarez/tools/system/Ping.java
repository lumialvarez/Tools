/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumialvarez.tools.system;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luigu
 */
public class Ping {

    private List<PingEco> ecos;
    private String endpoint;
    private int enviados;
    private int recibidos;
    private int perdidos;
    private float minimo;
    private float maximo;
    private float media;

    public Ping() {
        ecos = new ArrayList<>();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<PingEco> getEcos() {
        return ecos;
    }

    public void setEcos(List<PingEco> ecos) {
        this.ecos = ecos;
    }

    public int getEnviados() {
        return enviados;
    }

    public void setEnviados(int enviados) {
        this.enviados = enviados;
    }

    public int getRecibidos() {
        return recibidos;
    }

    public void setRecibidos(int recibidos) {
        this.recibidos = recibidos;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public void setPerdidos(int perdidos) {
        this.perdidos = perdidos;
    }

    public float getMinimo() {
        return minimo;
    }

    public void setMinimo(float minimo) {
        this.minimo = minimo;
    }

    public float getMaximo() {
        return maximo;
    }

    public void setMaximo(float maximo) {
        this.maximo = maximo;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Ping{" + "cos= " + ecos.size() + " items, endpoint=" + endpoint + ", enviados=" + enviados + ", recibidos=" + recibidos + ", perdidos=" + perdidos + ", minimo=" + minimo + ", maximo=" + maximo + ", media=" + media + '}';
    }

}
