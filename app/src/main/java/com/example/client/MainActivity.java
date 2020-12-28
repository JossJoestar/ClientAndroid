package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.AutoText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.client.Adapters.AdapterEmpleados;
import com.example.client.Config.Config;
import com.example.client.interfaces.APIService;
import com.example.client.models.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private AdapterEmpleados adapterEmpleados;
    private APIService service = Config.getRetrofit().create(APIService.class);
    private Button btnNew, btnEdit, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lista);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                Empleado em = new Empleado();
                intent.putExtra("idEmpleado",0);
                startActivity(intent);
            }
        });
        getPublicaciones();
    }
    private void getPublicaciones()
    {
        Call<List<Empleado>> listCall = service.getEmpleados();
        listCall.enqueue(new Callback<List<Empleado>>() {
            @Override
            public void onResponse(Call<List<Empleado>> call, Response<List<Empleado>> response) {
                if(response.isSuccessful()){
                    adapterEmpleados = new AdapterEmpleados(getApplicationContext(), response.body());
                    lv.setAdapter(adapterEmpleados);
                }
            }
            @Override
            public void onFailure(Call<List<Empleado>> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPublicaciones();
    }
}