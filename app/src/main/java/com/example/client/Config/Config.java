package com.example.client.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    private static  final String BASEUrl="http://192.168.0.9:81/sistema/public/api/";
    private  static Retrofit retrofit;
    public static Retrofit getRetrofit()
    {
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASEUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
