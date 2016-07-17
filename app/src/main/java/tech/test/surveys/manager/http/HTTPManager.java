package tech.test.surveys.manager.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.test.surveys.util.Contextor;

public class HTTPManager {
    private static final String BASE_URL = "https://www-staging.usay.co/app/";
    private static final int CONNECT_TIMEOUT_SECOND = 60;
    private static final int READ_TIMEOUT_SECOND = 60;
    private static final int WRITE_TIMEOUT_SECOND = 60;
    private static HTTPManager instance;

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .build();

    public static HTTPManager getInstance() {
        if (instance == null)
            instance = new HTTPManager();
        return instance;
    }

    private APIService service;

    private HTTPManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
    }

    public APIService getService() {
        return service;

    }


}