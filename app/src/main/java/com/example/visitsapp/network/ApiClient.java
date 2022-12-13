package com.example.visitsapp.network;

import android.content.Context;

import com.example.visitsapp.utils.SharedPrefrences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;


    private static final String PARAM_HEADER_ACCESS_TOKEN_KEY = "X-Authorization";
    private static final String PARAM_HEADER_ANDROID_VERSION_KEY = "version-no";
    private static final String PARAM_HEADER_ANDROID_VERSION_KEY_VALUE
            = "15";
    public static final String BASE_URL = "http://13.95.146.210/visit-app/public/api/";
    public static String paramHeaderAccessTokenValue = "";
    private static String paramHeaderRefreshTokenValue = "";


    private ApiClient() {

    }

    public static Retrofit getApiClient() {



        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = getHttpClient(SharedPrefrences.getInstance().getAccessToken());
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    private static OkHttpClient getHttpClient(final String bearerToken) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization","Bearer "+ bearerToken)
                        .header(PARAM_HEADER_ANDROID_VERSION_KEY,
                                PARAM_HEADER_ANDROID_VERSION_KEY_VALUE)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);



                return response;
            }
        });

        return httpClient.build();
    }


}
