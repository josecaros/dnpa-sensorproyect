package com.example.dnpa_sensorproyect.model;

import androidx.annotation.NonNull;

public class GPSLocation {
    private double longitud;
    private double latitud;
    private double velocidad;

    public GPSLocation(double longitud, double latitud, double velocidad){
        this.longitud = longitud;
        this.latitud = latitud;
        this.velocidad = velocidad;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    @NonNull
    @Override
    public String toString() {
        return "Longitud: "+longitud+"\n"+"Latitud: "+latitud;
    }
}
