package com.example.dnpa_sensorproyect.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.view.VistaInicial;

public class ControladorAceleracion implements SensorEventListener {
    private static final String TAG ="ControladorAceleracion";
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;
    private float[] acelLineal;
    private double vInicial=0;
    private long time;
    private long lastTime;
    private boolean isFlat;



    @Override
    public void onSensorChanged(SensorEvent event) {

        //obtenemos la aceleracion lineal sin la gravedad
        acelLineal = obtenerAceleracionLineal(event);

        //Tiempo transcurrido
        time=System.currentTimeMillis();
        long interval = time - lastTime;
        lastTime=time;
        double estimado = ((double)interval)/1000;

        //Calcular aceleracion total
        float linearAccTotal =calcularAceleracionTotal(acelLineal);

        double velocidad = vInicial+ (double)linearAccTotal*estimado;

        isFlat = isFlat(event);

        //imprimir en consola
        Log.i("aceleracionLinealTotal",linearAccTotal+"");
        Log.i("Tiempo transcurrido",estimado+"");

        Log.i("Velocidad",velocidad+"");

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

    public boolean isFlat(SensorEvent event)
    {
        float[] g = new float[3];
        g = event.values.clone();

        double norm_Of_g = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);

        // Normalizar el vector del acelerometro
        g[0] = (float) (g[0]/norm_Of_g);
        g[1] = (float) (g[1]/norm_Of_g);
        g[2] = (float) (g[2]/norm_Of_g);

        int inclination = (int) Math.round(Math.toDegrees(Math.acos(g[2])));

        String msg = "X: " + g[0] + " Y: " + g[1] + " Z: " + g[2];

        if (inclination < 25 || inclination > 155)
        {
            return true;
        }
        return false;
    }

    public float[] getAcelLineal(){
        return acelLineal;
    }

    public boolean getisFlat(){return isFlat;}

}
