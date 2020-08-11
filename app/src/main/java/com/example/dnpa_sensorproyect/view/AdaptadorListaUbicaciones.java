package com.example.dnpa_sensorproyect.view;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.model.GPSLocation;
import com.example.dnpa_sensorproyect.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AdaptadorListaUbicaciones extends ArrayAdapter<GPSLocation> {
    private Context context;
    private List<GPSLocation> objects;

    public AdaptadorListaUbicaciones(@NonNull Context context, int resource, @NonNull List<GPSLocation> objects) {
        super(context, resource, objects);
        this.context=context;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_ubicaciones, parent, false);
        }
        User usuario = VistaInicial.usuario;
        final GPSLocation location=getItem(position);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombreUsuario);
        TextView correo= (TextView) convertView.findViewById(R.id.correoUsuario);
        TextView ubiLongitud =(TextView)convertView.findViewById(R.id.ubiLongitud);
        TextView ubiLatitud = (TextView)convertView.findViewById(R.id.ubiLatitud);
        nombre.setText((usuario.getUserID()));
        correo.setText(usuario.getEmail());
        ubiLongitud.setText("Longitud: "+location.getLongitud());
        ubiLatitud.setText("Latitud: "+location.getLatitud());

        return convertView;
    }

}
