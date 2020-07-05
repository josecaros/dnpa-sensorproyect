package com.example.dnpa_sensorproyect.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dnpa_sensorproyect.R;
import com.example.dnpa_sensorproyect.model.GPSLocation;
import com.example.dnpa_sensorproyect.model.User;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

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
