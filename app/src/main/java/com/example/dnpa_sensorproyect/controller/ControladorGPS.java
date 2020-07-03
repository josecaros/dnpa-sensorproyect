package com.example.dnpa_sensorproyect.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import com.example.dnpa_sensorproyect.model.GPSLocation;
import java.util.ArrayList;


public class ControladorGPS  extends BroadcastReceiver {
    private GPSLocation ubicacion = new GPSLocation();
    private String TAG = "LocationBroadcastReceiver";

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
        ubicacion.setLatitud(location.getLatitude());
        ubicacion.setLongitud(location.getLongitude());
        ubicacion.setVelocidad(location.getSpeed());
    }

    public GPSLocation getLocation(){
        GPSLocation ubicacion = new GPSLocation();
        ubicacion.setLatitud(this.ubicacion.getLatitud());
        ubicacion.setLongitud(this.ubicacion.getLongitud());
        ubicacion.setVelocidad(this.ubicacion.getVelocidad());
        return ubicacion;
    }

}
