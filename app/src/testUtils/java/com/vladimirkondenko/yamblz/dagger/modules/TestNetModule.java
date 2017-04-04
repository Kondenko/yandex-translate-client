package com.vladimirkondenko.yamblz.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TestNetModule {

    @Singleton
    @Provides
    public MockWebServer provideMockWebServer() {
        return new MockWebServer();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(MockWebServer server) {
        return new Retrofit.Builder()
                .baseUrl(server.url(""))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
