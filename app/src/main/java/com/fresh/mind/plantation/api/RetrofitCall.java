package com.fresh.mind.plantation.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by AND I5 on 10/27/17.
 */

public class RetrofitCall {

    private static Retrofit retrofit = null;

    //public static String BASE_URL = "http://plantation.kambaaincorporation.in/webservices/";

    public static String BASE_URL = "http://plantation.roughnote.in/webservices/";
    //public static String BASE_URL = "http://roughnote.in/plantation/webservices/";


    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                /*.retryOnConnectionFailure(true)*/
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    /*.baseUrl("http://statusdev.kambaa.com/")*/
                    .build();
        }
        return retrofit;
    }

}
