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

    private String TAG = "LocationBroadcastReceiver";
    private ArrayList<GPSLocation> ubicaciones =  new ArrayList<GPSLocation>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {

            String locationKey = LocationManager.KEY_LOCATION_CHANGED;
            Location location = (Location) intent.getExtras().get(locationKey);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double speed = location.getSpeed();

            //almacenamos los valores de la longitud y latitud en un arreglo de GPSLocation
            ubicaciones.add(new GPSLocation(longitude, latitude, speed));

            Log.d(TAG,  ubicaciones.get(0).getLatitud()+","+ ubicaciones.get(0).getLongitud());
            Toast.makeText(context, ubicaciones.get(0).getLatitud()+","+ubicaciones.get(0).getLatitud(), Toast.LENGTH_LONG).show();

        }

        else if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {

            String locationKey = LocationManager.KEY_PROVIDER_ENABLED;
            Location location = (Location) intent.getExtras().get(locationKey);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double speed = location.getSpeed();

            //almacenamos los valores de la longitud y latitud en un arreglo de GPSLocation
            ubicaciones.add(new GPSLocation(longitude, latitude, speed));

            Log.d(TAG,  ubicaciones.get(0).getLatitud()+","+ ubicaciones.get(0).getLongitud());
            Toast.makeText(context, ubicaciones.get(0).getLatitud()+","+ubicaciones.get(0).getLatitud(), Toast.LENGTH_LONG).show();

        }

        else if (intent.hasExtra(LocationManager.KEY_PROXIMITY_ENTERING)) {

            String locationKey = LocationManager.KEY_PROXIMITY_ENTERING;
            Location location = (Location) intent.getExtras().get(locationKey);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double speed = location.getSpeed();

            //almacenamos los valores de la longitud y latitud en un arreglo de GPSLocation
            ubicaciones.add(new GPSLocation(longitude, latitude, speed));

            Log.d(TAG,  ubicaciones.get(0).getLatitud()+","+ ubicaciones.get(0).getLongitud());
            Toast.makeText(context, ubicaciones.get(0).getLatitud()+","+ubicaciones.get(0).getLatitud(), Toast.LENGTH_LONG).show();
        }

        else if (intent.hasExtra(LocationManager.KEY_STATUS_CHANGED)) {
            String locationKey = LocationManager.KEY_STATUS_CHANGED;
            Location location = (Location) intent.getExtras().get(locationKey);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double speed = location.getSpeed();

            //almacenamos los valores de la longitud y latitud en un arreglo de GPSLocation
            ubicaciones.add(new GPSLocation(longitude, latitude, speed));

            Log.d(TAG,  ubicaciones.get(0).getLatitud()+","+ ubicaciones.get(0).getLongitud());
            Toast.makeText(context, ubicaciones.get(0).getLatitud()+","+ubicaciones.get(0).getLatitud(), Toast.LENGTH_LONG).show();
        }
    }

    public GPSLocation get(){

        return null;
    }

}
