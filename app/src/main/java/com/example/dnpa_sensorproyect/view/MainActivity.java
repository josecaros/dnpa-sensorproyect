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
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.controller.ControladorGPS;
import com.example.dnpa_sensorproyect.model.User;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button login;
    private TextView idUser, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        idUser = (TextView) findViewById(R.id.idUser);
        email = (TextView)findViewById(R.id.correoUser);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VistaInicial.class);
                intent.putExtra("idUser", idUser.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }
}
