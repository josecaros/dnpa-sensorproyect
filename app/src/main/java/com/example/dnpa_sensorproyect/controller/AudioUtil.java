package com.example.dnpa_sensorproyect.controller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class AudioUtil {
    private static final String TAG="AudioUtil";
    public static boolean hasMicrophone(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
    public static MediaRecorder prepareRecoder(String sdCardPath) throws IOException {
        MediaRecorder recorder = new MediaRecorder();
        //set a custom listener that just logs any messages
        /*RecorderErrorLoggerListener recorderListener = new RecorderErrorLoggerListener();
        recorder.setOnErrorListener(recorderListener);
        recorder.setOnInfoListener(recorderListener);*/
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        Log.d(TAG, "recording to: " + sdCardPath);
        recorder.setOutputFile(sdCardPath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.prepare();
        return recorder;
    }
}
