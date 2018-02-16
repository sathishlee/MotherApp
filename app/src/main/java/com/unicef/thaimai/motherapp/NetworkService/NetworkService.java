package com.unicef.thaimai.motherapp.NetworkService;


import android.util.Base64;
import android.util.LruCache;

import com.unicef.thaimai.motherapp.constant.Apiconstants;
import com.unicef.thaimai.motherapp.Networkapi.NetworkAPI;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sathish on 2/15/2018.
 */

public class NetworkService {

    private NetworkAPI networkAPI;
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservable;


    public NetworkService() {
        this(Apiconstants.BASE_URL);
    }

    public NetworkService(String baseUrl) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(loggingInterceptor);

//baseUrl(baseUrl)
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Apiconstants.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        Retrofit retrofit = new Retrofit.Builder().
//                baseUrl(Apiconstants.BASE_URL)
//
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
        networkAPI = retrofit.create(NetworkAPI.class);
    }


    public  NetworkAPI getApi(){
        return networkAPI;
    }

    public OkHttpClient buildclient(){

        String credentials ="admin"+":"+"1234";
        final String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(),Base64.DEFAULT);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());

                return response;
            }
        });


        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request  request = chain.request().newBuilder()
                        .addHeader("Content-Type","application/x-www-from-urlencoded; charset=utf-8")
                        .addHeader("Authorization","Basic "+base64EncodedCredentials)
                        .build();
                return chain.proceed(request);
            }
        });

        return clientBuilder.build();

    }

}
