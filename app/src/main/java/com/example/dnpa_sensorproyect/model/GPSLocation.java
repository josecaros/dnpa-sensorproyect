package com.example.dnpa_sensorproyect.model;

import androidx.annotation.NonNull;

public class GPSLocation {
    private double longitud;
    private double latitud;

    public GPSLocation(double longitud, double latitud){
        this.longitud = longitud;
        this.latitud = latitud;
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

    @NonNull
    @Override
    public String toString() {
        return "Longitud: "+longitud+"\n"+"Latitud: "+latitud;
    }
}
