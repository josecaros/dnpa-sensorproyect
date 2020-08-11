package com.example.dnpa_sensorproyect.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dnpa_sensorproyect.model.GPSLocation;
import com.example.dnpa_sensorproyect.view.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ControladorGPS  extends BroadcastReceiver {
    private String TAG = "ControladorGPS";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        String locationKey="";
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
            locationKey = LocationManager.KEY_LOCATION_CHANGED;
        }
        else if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
            locationKey = LocationManager.KEY_PROVIDER_ENABLED;
        }
        else if (intent.hasExtra(LocationManager.KEY_PROXIMITY_ENTERING)) {
            locationKey = LocationManager.KEY_PROXIMITY_ENTERING;
        }
        else if (intent.hasExtra(LocationManager.KEY_STATUS_CHANGED)) {
            locationKey = LocationManager.KEY_STATUS_CHANGED;
        }
        Location location = (Location) intent.getExtras().get(locationKey);
        VistaInicial.ubic.setLatitud(location.getLatitude());
        VistaInicial.ubic.setLongitud(location.getLongitude());
        VistaInicial.ubic.setVelocidad(location.getSpeed());


    }

    public static GPSLocation obtenerUbicacion(){
        GPSLocation locate = new GPSLocation();
        locate.setLatitud(VistaInicial.ubic.getLatitud());
        locate.setLongitud(VistaInicial.ubic.getLongitud());
        locate.setVelocidad(VistaInicial.ubic.getVelocidad());
        return locate;
    }

}
