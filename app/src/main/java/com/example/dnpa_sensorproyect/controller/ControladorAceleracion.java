package com.example.dnpa_sensorproyect.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

public class ControladorAceleracion implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    private float[] acelLineal;
    private float acelTotal;
    private float tiempoTranscurrido;

    public ControladorAceleracion(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] tmpAcel;
        float aTotal;

        tmpAcel = event.values.clone();
        //Calcular aceleracion lineal
        //obtenemos la aceleracion lineal sin la gravedad
        acelLineal = obtenerAceleracionLineal(event);

        //Calcular aceleracion total
        acelTotal = calcularAceleracionTotal(acelLineal);

        //tiempo transcurrido en nanosegundos
        tiempoTranscurrido = SystemClock.elapsedRealtimeNanos();

        //imprimir en consola
        String msg = "Tiempo transcurrido: " + SystemClock.elapsedRealtime()*0.0001 + ", Aceleracion X: "
                + acelLineal[0] + ", Aceleracion Y: " + acelLineal[1] + ", Aceleracion Z: " + acelLineal[2]
                + ", Aceleracion Total: " + acelTotal;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private float[] obtenerAceleracionLineal(SensorEvent event){
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

    private float calcularAceleracionTotal(float[] acel){
        // Aceleracion Total = sqrt(aceleracionX^2 + aceleracionY^2 + aceleracionZ^2)
        return (float) Math.sqrt(acel[0]*acel[0] + acel[1]*acel[1] + acel[2]*acel[2]);
    }

    public float[] getAcelLineal(){
        return acelLineal;
    }

    public float getAcelTotal(){
        return acelTotal;
    }
}
