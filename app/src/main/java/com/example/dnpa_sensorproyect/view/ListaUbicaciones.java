package com.example.dnpa_sensorproyect.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.controller.BluetoothTransfer;
import com.example.dnpa_sensorproyect.model.GPSLocation;

import java.util.ArrayList;

public class ListaUbicaciones extends AppCompatActivity {
    ListView lista;
    AdaptadorListaUbicaciones adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ubicaciones);
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
            Intent intent = new Intent(this, Camera2.class);
            startActivityForResult(intent,200);
        }else{}
        return super.onContextItemSelected(item);
    }

    private void listarUbicaciones() {
        adapter = new AdaptadorListaUbicaciones(this,R.id.listaUbicaciones, VistaInicial.usuario.getPlace() );
        lista.setAdapter(adapter);

    }
}
