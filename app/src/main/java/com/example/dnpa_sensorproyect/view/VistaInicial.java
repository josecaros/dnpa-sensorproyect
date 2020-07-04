package com.example.dnpa_sensorproyect.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.controller.ControladorAceleracion;
import com.example.dnpa_sensorproyect.controller.ControladorGPS;
import com.example.dnpa_sensorproyect.model.GPSLocation;

public class VistaInicial extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = "VistaInicial";
    private SensorManager sensorManager;
    private Sensor sensor;
    private ControladorAceleracion acceListener;
    private ControladorGPS gps;
    public static GPSLocation ubic = new GPSLocation();
    Button startApp, stopApp,miUbicacion,listaUbicaciones;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_inicial);
        startApp= (Button)findViewById(R.id.startApp);
        stopApp=(Button)findViewById(R.id.stopApp);
        miUbicacion=(Button)findViewById(R.id.miUbicacion);
        listaUbicaciones=(Button)findViewById(R.id.listaUbicaciones);
        info=(TextView)findViewById(R.id.testText);

        startApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
                initAcelerometer();
                //initOrientation();
                enableOptions();
            }
        });
        stopApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(acceListener,sensor);
                disableOptions();
            }
        });

        miUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setText(ControladorGPS.obtenerUbicacion()+"");
            }
        });



        /*if(acceListener.getisFlat()){
            sensorManager.unregisterListener(acceListener,sensor);
            disableOptions();
        }*/


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (gps != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);
            registerReceiver(gps, intentFilter);
        } else {
            Log.d(TAG, "broadcastReceiver is null");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(gps);
    }

    public boolean checkLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        initGPS();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "Location not allowed");
                }
                return;
            }
        }
    }

    private void initGPS(){
        Intent intent = new Intent(this, ControladorGPS.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(//sendBroadcast(...)
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, pendingIntent);
    }
    private void initAcelerometer(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        acceListener = new ControladorAceleracion();
        sensorManager.registerListener(acceListener, sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void initOrientation(){

    }

    private void enableOptions(){
        startApp.setEnabled(false);
        stopApp.setEnabled(true);
        miUbicacion.setEnabled(true);
        listaUbicaciones.setEnabled(true);
    }
    public void disableOptions(){
        startApp.setEnabled(true);
        stopApp.setEnabled(false);
        miUbicacion.setEnabled(false);
        listaUbicaciones.setEnabled(false);
    }
}
