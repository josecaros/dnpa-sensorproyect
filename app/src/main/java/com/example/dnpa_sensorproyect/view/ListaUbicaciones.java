package com.example.dnpa_sensorproyect.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.controller.BluetoothTransfer;
import com.example.dnpa_sensorproyect.model.GPSLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ListaUbicaciones extends AppCompatActivity {
    ListView lista;
    AdaptadorListaUbicaciones adapter;
    private StorageReference mStorageRef;
    File ubicTemp;
    FileWriter myWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ubicaciones);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        lista = (ListView) findViewById(R.id.listaUbicaciones);
        listarUbicaciones();
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.context_share,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId()== R.id.compartirBlue){
            GPSLocation aux = VistaInicial.usuario.getPlace().get(info.position);
            Intent intent=new Intent(this, BluetoothTransfer.class);
            intent.putExtra("Ubicacion", "Latitud: "+aux.getLatitud()+"\t"+"Longitud: "+aux.getLongitud());
            startActivityForResult(intent,0);
        } else if (item.getItemId() == R.id.addClip) {
            Intent intent = new Intent(this, MicrophoneActivity.class);
            startActivityForResult(intent,100);

        }else if (item.getItemId()==R.id.addPhoto){
            GPSLocation auxUbic = VistaInicial.usuario.getPlace().get(info.position);
            ubicTemp = new File(Environment.getExternalStorageDirectory()+"/"+ UUID.randomUUID()+".txt");

            try {
                myWriter = new FileWriter(ubicTemp.getAbsolutePath());
                myWriter.write("Latitud: " + auxUbic.getLatitud() + "\n");
                myWriter.write("Longitud: " + auxUbic.getLongitud()+ "\n");
                myWriter.write("Velocidad: " + auxUbic.getVelocidad());
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            subirUbicacion(ubicTemp);
            
            Intent intent = new Intent(this, Camera2.class);
            startActivityForResult(intent,200);
        }else{

        }
        return super.onContextItemSelected(item);
    }

    private void listarUbicaciones() {
        adapter = new AdaptadorListaUbicaciones(this,R.id.listaUbicaciones, VistaInicial.usuario.getPlace() );
        lista.setAdapter(adapter);

    }

    private void subirUbicacion(File file) {
        Uri uri_file = Uri.fromFile(file.getAbsoluteFile());
        StorageReference ubicRef = mStorageRef.child("Usuario").child("Ubicaciones").child(uri_file.getLastPathSegment());

        ubicRef.putFile(uri_file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Log.e("", "Ubicacion Subida");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("", "Error al subir la Ubicacion");
                    }
                });
    }
}
