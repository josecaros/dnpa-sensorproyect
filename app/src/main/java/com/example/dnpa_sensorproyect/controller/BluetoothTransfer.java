package com.example.dnpa_sensorproyect.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.dnpa_sensorproyect.R;

import java.io.File;
import java.util.List;

public class BluetoothTransfer extends AppCompatActivity {
    private static final int DISCOVER_DURATION=300;
    private static final int REQ_BLU=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_transfer);
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
}
