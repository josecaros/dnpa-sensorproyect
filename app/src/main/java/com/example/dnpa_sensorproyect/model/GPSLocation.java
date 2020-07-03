package com.example.dnpa_sensorproyect.model;

import androidx.annotation.NonNull;

public class GPSLocation {
    private String longitud;
    private String latitud;
    public GPSLocation(){}

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    @NonNull
    @Override
    public String toString() {
        return "Longitud: "+longitud+"\n"+"Latitud: "+latitud;
    }
}
