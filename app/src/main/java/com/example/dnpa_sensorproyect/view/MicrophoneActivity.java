package com.example.dnpa_sensorproyect.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.controller.MaxAmplitudeRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MicrophoneActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    public static String TAG = "MainActivity";
    private Button play, record, stop;
    public static final int REQUEST_PERMISION_CODE = 100;
    File audio;
    MaxAmplitudeRecorder recorder;
    MediaPlayer player;
    MediaPlayer.OnCompletionListener complet = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone);
        play = (Button)findViewById(R.id.play);
        record = (Button)findViewById(R.id.record);
        stop = (Button)findViewById(R.id.stop);
        stop.setEnabled(false);
        play.setEnabled(false);
        solicitarPermisos();
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio = new File(Environment.getExternalStorageDirectory()+"/"+ UUID.randomUUID()+".3gp");
                double clip =10;
                long time= (long) clip;
                recorder = new MaxAmplitudeRecorder(time,audio.getAbsolutePath());
                try{
                    recorder.startRecord();
                }catch (IOException e){
                    e.printStackTrace();
                }
                stop.setEnabled(true);
                record.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.done();
                stop.setEnabled(false);
                terminarGrabar(1000);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
                play.setEnabled(false);
                stop.setEnabled(false);
                record.setEnabled(false);
            }
        });
    }

    private void terminarGrabar(int msegs) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                try{
                    player=new MediaPlayer();
                    player.setOnCompletionListener(complet);
                    try{
                        player.setDataSource(audio.getAbsolutePath());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    try{
                        player.prepare();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    play.setEnabled(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, msegs);

    }

    public boolean permisos(){
        int granted = PackageManager.PERMISSION_GRANTED;
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!=granted &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=granted &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=granted);
    }

    public void solicitarPermisos(){
        if (permisos()) {
            // Permission is not granted
            // Should we show an explanation?
            /*if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {*/
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISION_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            //}
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        record.setEnabled(true);
    }
}
