package com.example.client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.client.Config.Config;
import com.example.client.interfaces.APIService;
import com.example.client.models.Empleado;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends AppCompatActivity {
    private boolean isNew = true;
    private APIService service = Config.getRetrofit().create(APIService.class);
    private Button btnSaveOrEdit, btnDelete;
    private EditText edtNombre, edtApellidoP, edtApellidoM, edtCorreo;
    private int idEmpleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        load();
    }
    private void load()
    {
        btnSaveOrEdit = findViewById(R.id.btnSaveOrEdit);
        edtNombre = findViewById(R.id.editTextNombre);
        edtApellidoP = findViewById(R.id.editTextApellidoPaterno);
        edtApellidoM = findViewById(R.id.editTextApellidoMaterno);
        edtCorreo = findViewById(R.id.editTextCorreo);
        idEmpleado = getIntent().getExtras().getInt("idEmpleado");
        if(idEmpleado != 0)
        {
            isNew = false;
            Empleado emp = (Empleado)getIntent().getExtras().getSerializable("object");
            edtNombre.setText(emp.getNombre());
            edtApellidoP.setText(emp.getApellidoPaterno());
            edtApellidoM.setText(emp.getApellidoMaterno());
            edtCorreo.setText(emp.getCorreo());
            btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteData(idEmpleado);

                        }
                    }
            );
        }
        btnSaveOrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empleado = new Empleado();
                empleado.setId(idEmpleado);
                empleado.setNombre(edtNombre.getText().toString());
                empleado.setApellidoPaterno(edtApellidoP.getText().toString());
                empleado.setApellidoMaterno(edtApellidoM.getText().toString());
                empleado.setCorreo(edtCorreo.getText().toString());
                if(isNew) {
                    if (validateData(
                            edtNombre.getText().toString().trim(),
                            edtApellidoP.getText().toString().trim(),
                            edtApellidoM.getText().toString().trim(),
                            edtCorreo.getText().toString().trim()))
                        postData(empleado);
                    else
                        Toast.makeText(getApplicationContext(), "Por favor llena todos los datos", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(validateData(
                            edtNombre.getText().toString().trim(),
                            edtApellidoP.getText().toString().trim(),
                            edtApellidoM.getText().toString().trim(),
                            edtCorreo.getText().toString().trim()))
                        putData(empleado);
                    else
                        Toast.makeText(getApplicationContext(), "Por favor llena todos los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateData(String nombre, String apellidoPaterno, String apellidoMaterno, String email){
        if(nombre.isEmpty() || nombre.length() == 0 || nombre.equals("") || nombre == null)
            return false;
        else if(apellidoMaterno.isEmpty() || apellidoMaterno.length() == 0 || apellidoMaterno.equals("") || apellidoMaterno == null)
            return  false;
        else if(apellidoPaterno.isEmpty() || apellidoPaterno.length() == 0 || apellidoPaterno.equals("") || apellidoPaterno == null)
            return false;
        else if (email.isEmpty() || email.length() == 0 || email.equals("") || email == null)
            return  false;
        else
            return true;
    }
    private void putData(Empleado emp)
    {
        Call<Empleado> empleadoCall = service.putEmpleado(emp.getId(),emp);
        empleadoCall.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if(response.isSuccessful())
                    finish();
            }
            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }
    private void postData(Empleado emp)
    {
        Call<Empleado> empleadoCall = service.postEmpelado(emp);
        empleadoCall.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if(response.isSuccessful())
                    finish();
            }
            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }
    private void deleteData(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewActivity.this);
        builder.setMessage("¿Seguro de querer eliminarlo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCall(id);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteCall(int id)
    {
        Call<Integer> empleCall = service.deleteEmpelado(id);
        empleCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful())
                    finish();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }
}