package com.example.dnpa_sensorproyect.controller;

import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class MaxAmplitudeRecorder {
    private static final String TAG = "MaxAmplitudeRecorder";
    private long clipTime;
    private AmplitudeClipListener clipListener;
    private boolean continueRecording;
    private MediaRecorder recorder;
    private String tmpAudioFile;
    private AsyncTask task;

    public MaxAmplitudeRecorder(long clipTime, String tmpAudioFile, AmplitudeClipListener clipListener, AsyncTask task) {
        this.clipTime = clipTime;
        this.clipListener = clipListener;
        this.tmpAudioFile = tmpAudioFile;
        this.task = task;
    }
    public MaxAmplitudeRecorder(long clipTime, String tmpAudioFile) {
        this.clipTime = clipTime;
        this.tmpAudioFile = tmpAudioFile;
    }

    public boolean startRecording() throws IOException {
        recorder= AudioUtil.prepareRecoder(tmpAudioFile);
        recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                stopRecording();
            }
        });
        recorder.start();
        continueRecording=true;
        boolean heard = false;
        recorder.getMaxAmplitude();
        while(continueRecording){
            Log.d(TAG, "grabando");
            waitClipTime();
            if((!continueRecording || ((task!=null) && task.isCancelled()))){
                break;
            }
            int maxAmplitude = recorder.getMaxAmplitude();
            Log.d(TAG, "Current Max Amplitude: "+maxAmplitude);
            heard = clipListener.heard(maxAmplitude);
            if(heard){
                stopRecording();
            }
        }
        Log.d(TAG, "Detener grabacion de maxima amplitud");
        done();
        return heard;
    }
    public void startRecord() throws IOException{
        recorder= AudioUtil.prepareRecoder(tmpAudioFile);
        recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                stopRecording();
            }
        });
        recorder.start();
        /*continueRecording=true;
        recorder.getMaxAmplitude();
        while(continueRecording){
            Log.d(TAG, "grabando");
            waitClipTime();
            int maxAmplitude = recorder.getMaxAmplitude();
            Log.d(TAG, "Current Max Amplitude: "+maxAmplitude);
        }
        Log.d(TAG, "Detener grabacion de maxima amplitud");
        done();*/
    }

    public void done() {
        Log.d(TAG,"Se detuvo al completar");
        if(recorder!=null){
            try{
                recorder.stop();
            }catch (Exception e){
                Log.d(TAG, "fallar al detener");
                return;
            }
            recorder.release();
        }
    }

    private void waitClipTime() {
        try{
            Thread.sleep(clipTime);
        }catch (InterruptedException e){
            Log.d(TAG,"Interrumpido");
        }
    }

    private void stopRecording() {
        continueRecording=false;
    }

    private boolean isRecording(){
        return continueRecording;
    }
}
