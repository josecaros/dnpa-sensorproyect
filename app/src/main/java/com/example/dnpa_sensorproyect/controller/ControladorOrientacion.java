package com.example.dnpa_sensorproyect.controller;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dnpa_sensorproyect.model.GPSLocation;
import com.example.dnpa_sensorproyect.view.VistaInicial;

public class ControladorOrientacion extends AppCompatActivity implements SensorEventListener {
    private static final String TAG ="ControladorOrientacion";
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    public float[] aceleracion;

    private boolean isFlat;
    public VistaInicial vista;

    public ControladorOrientacion(VistaInicial vista){
        this.vista = vista;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //obtenemos la aceleracion lineal sin la gravedad
        aceleracion =obtenerAceleracionLineal(event);

        //cambiar disposicion de la pantalla usando los ejes X y Y
        if( (aceleracion[1]>-Math.sin(30) && aceleracion[1]<Math.sin(30)) || aceleracion[0] > Math.cos(30) ){
            vista.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }else if( (aceleracion[0] < -Math.cos(30)) || (aceleracion[1]>-Math.sin(30) && aceleracion[1]<Math.sin(30)) ){
            vista.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float[] obtenerAceleracionLineal(SensorEvent event){
        final float alpha = (float)0.8;
        float [] g = new float[3];
        float [] linAcel = new float[3];

        // Aplicando el filtro de paso baja para aislar el valor de la gravedad de la tierra.
        g[0] = alpha * g[0] + (1 - alpha) * event.values[0];
        g[1] = alpha * g[1] + (1 - alpha) * event.values[1];
        g[2] = alpha * g[2] + (1 - alpha) * event.values[2];

        // Eliminar la gravedad
        linAcel[0] = event.values[0] - g[0];
        linAcel[1] = event.values[1] - g[1];
        linAcel[2] = event.values[2] - g[2];

        return linAcel;
    }

}
