package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.utils.interceptors.ApiKeyInterceptor;
import com.vladimirkondenko.yamblz.utils.interceptors.LoggingInterceptor;

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
        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor(Const.API_KEY))
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
