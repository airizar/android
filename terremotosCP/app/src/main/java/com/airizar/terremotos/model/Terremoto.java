package com.airizar.terremotos.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cursomovil on 25/03/15.
 */
public class Terremoto implements Serializable {

    /**
     * Identificador
     */
    private String _id;
    /**
     * Coordenada
     */
    private Coordenada coord;
    /**
     * Fecha
     */
    private Date time;

    private double magnitud;

    private String lugar;

    private String url;

    public Terremoto(String _id, Coordenada coord, Date time, double magnitud, String lugar) {
        this._id = _id;
        this.coord = coord;
        this.time = time;
        this.magnitud = magnitud;
        this.lugar = lugar;
    }

    public Terremoto() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Coordenada getCoord() {
        return coord;
    }

    public void setCoord(Coordenada coord) {
        this.coord = coord;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = new Date(time);
    }

    public double getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getLugar();
    }

}
