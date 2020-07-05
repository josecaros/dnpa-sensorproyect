package com.example.dnpa_sensorproyect.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dnpa_sensorproyect.R;
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
            //Compartir
        }
        return super.onContextItemSelected(item);
    }

    private void listarUbicaciones() {
        adapter = new AdaptadorListaUbicaciones(this,R.id.listaUbicaciones, VistaInicial.usuario.getPlace() );
        lista.setAdapter(adapter);

    }
}
