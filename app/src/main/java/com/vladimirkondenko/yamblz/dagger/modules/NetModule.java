package com.vladimirkondenko.yamblz.dagger.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.vladimirkondenko.yamblz.BuildConfig;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.utils.RealmGson;
import com.vladimirkondenko.yamblz.utils.interceptors.ApiKeyInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new ApiKeyInterceptor(Const.API_KEY));
        if (BuildConfig.DEBUG) clientBuilder.addNetworkInterceptor(new StethoInterceptor()); // Must be the last interceptor
        return clientBuilder.build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return RealmGson.create();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
