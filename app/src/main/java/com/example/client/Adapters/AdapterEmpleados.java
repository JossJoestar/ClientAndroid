package com.example.client.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.client.MainActivity;
import com.example.client.NewActivity;
import com.example.client.R;
import com.example.client.models.Empleado;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class AdapterEmpleados extends ArrayAdapter<Empleado>  {
    List<Empleado> empleadoList;
    Context context;
    Button editBtn;


    public AdapterEmpleados(Context context, List<Empleado> list) {
        super(context, R.layout.element,list);
        empleadoList = list;
        this.context  = context;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.element,null);
        TextView n = view.findViewById(R.id.txtNombre);
        TextView a = view.findViewById(R.id.txtApellido);
        TextView c = view.findViewById(R.id.txtCorreo);
        String apellidos = empleadoList.get(position).getApellidoPaterno().toString() + empleadoList.get(position).getApellidoMaterno().toString() +".";
        n.setText(empleadoList.get(position).getNombre());
        a.setText(apellidos);
        c.setText(empleadoList.get(position).getCorreo());

        editBtn = view.findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewActivity.class);
                Empleado em = new Empleado();
                intent.putExtra("idEmpleado",empleadoList.get(position).getId());
                Empleado emp = new Empleado();
                emp.setId(empleadoList.get(position).getId());
                emp.setNombre(empleadoList.get(position).getNombre());
                emp.setApellidoPaterno(empleadoList.get(position).getApellidoPaterno());
                emp.setApellidoMaterno(empleadoList.get(position).getApellidoMaterno());
                emp.setCorreo(empleadoList.get(position).getCorreo());
                intent.putExtra("object", (Serializable) emp);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
