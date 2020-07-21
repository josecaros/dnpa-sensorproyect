package com.example.dnpa_sensorproyect.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnpa_sensorproyect.R;

import java.io.File;
import java.util.List;
import java.util.Set;

public class BluetoothTransfer extends AppCompatActivity {
    private static final int DISCOVER_DURATION=300;
    private static final int REQ_BLU=1;
    private static final int REQUEST_ENABLE_BT =0;
    private static final int REQUEST_DISCOVER_BT=2;
    private TextView mStatusBlueTv, mPairedTv;
    private Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;
    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_transfer);
        mStatusBlueTv = (TextView)findViewById(R.id.estado);
        mPairedTv = (TextView)findViewById(R.id.paired);
        mOnBtn=(Button)findViewById(R.id.encenderB);
        mOffBtn=(Button)findViewById(R.id.apagarB);
        mDiscoverBtn=(Button)findViewById(R.id.descubrirB);
        mPairedBtn = (Button)findViewById(R.id.obtenerDispositivosB);
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        solicitarPermisos();
        checkBluetooth();
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isEnabled()){
                    mensaje("Encendiendo bluetooth");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                }else
                    mensaje("Bluetooth ya encendido");
            }
        });
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    mensaje("Apagando Bluetooth");
                }else {
                    mensaje("Bluetooth ya esta apagado");
                }
            }
        });
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isDiscovering()){
                    mensaje("Haciendo tu dispositivo visible");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBlueAdapter.isEnabled()){
                    mPairedTv.setText("Paired Divices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for(BluetoothDevice device: devices){
                        mPairedTv.append("\nDevice"+device.getName()+", "+device);
                    }
                }else{
                    mensaje("Enciende el bluetooth");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode== RESULT_OK){
                    mensaje("Bluetooth encendido");
                }else
                    mensaje("no se pudo encender bluetooth");
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void checkBluetooth() {
        if(mBlueAdapter==null){
            mStatusBlueTv.setText("Bluetooth no disponible");
        }else {
            mStatusBlueTv.setText("Bluetooth disponible");
        }
    }

    private void mensaje(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private void solicitarPermisos() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!=PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_ADMIN)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN}, REQ_BLU);
        }else {
            Toast.makeText(this, "Permisos activados", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQ_BLU:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{

                }
                return;
            }
        }
    }

    public void sendViaBluetooth(View v){
        BluetoothAdapter btAdapter=BluetoothAdapter.getDefaultAdapter();
        if(btAdapter==null){
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_LONG).show();
        }else {
            enableBluetooth();
        }
    }
    private void enableBluetooth() {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,DISCOVER_DURATION);
        startActivityForResult(discoveryIntent,REQ_BLU);
        Toast.makeText(this, "Hasta aqui", Toast.LENGTH_LONG).show();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==DISCOVER_DURATION && resultCode==REQ_BLU){
            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            File f = new File(Environment.getExternalStorageDirectory(), "md5sum.txt");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            PackageManager pm =getPackageManager();
            List<ResolveInfo>appList = pm.queryIntentActivities(intent,0);
            if(appList.size()>0){
                String packageName = null;
                String classname=null;
                boolean found =false;
                for(ResolveInfo info: appList){
                    packageName=info.activityInfo.packageName;
                    if(packageName.equals("com.android.bluetooth")){
                        classname=info.activityInfo.name;
                        found=true;
                        break;
                    }
                }
                if(!found){
                    Toast.makeText(this, "bluetooth no encontrado", Toast.LENGTH_LONG).show();
                }else {
                    intent.setClassName(packageName,classname);
                    startActivity(intent);
                }
            }else {
                Toast.makeText(this, "Bluetooth fue cacelado", Toast.LENGTH_LONG).show();
            }
        }

    }
     */
}
