package com.airizar.terremotos.model;

import java.io.Serializable;

/**
 * Created by cursomovil on 25/03/15.
 */
public class Coordenada implements Serializable {
    /**
     * Latitud
     */
    private double lat;
    /**
     * Longitud
     */
    private double lon;
    /**
     * Profundidad
     */
    private double produndidad;

    public Coordenada(double lat, double lon, double produndidad) {
        this.lat = lat;
        this.lon = lon;
        this.produndidad = produndidad;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getProdundidad() {
        return produndidad;
    }

    public void setProdundidad(double produndidad) {
        this.produndidad = produndidad;
    }
}
