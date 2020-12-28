package com.example.client.interfaces;

import com.example.client.models.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("Empleados")
    Call<List<Empleado>> getEmpleados();

    @GET("Empleados/{id}")
    Call<Empleado> getEmpleado(@Path("id") int id);

    @PUT("Empleados/{id}")
    Call<Empleado> putEmpleado(@Path("id")int id, @Body Empleado emp);

    @POST("Empleados")
    Call<Empleado> postEmpelado(@Body Empleado emp);

    @DELETE("Empleados/{id}")
    Call<Integer> deleteEmpelado(@Path("id") int id);
}
