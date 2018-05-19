package com.carousell.brainbreaker.reddit.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brainbreaker on 19/05/18.
 */

public class RetrofitAPIClient {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://pacific-caverns-24056.herokuapp.com/";

    public static Retrofit getClient() {

        // Logging interceptor to log the responses being returned
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
