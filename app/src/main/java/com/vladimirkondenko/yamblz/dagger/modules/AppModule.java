package com.vladimirkondenko.yamblz.dagger.modules;

import android.app.Application;
import android.content.Context;

import com.vladimirkondenko.yamblz.utils.RxNetworkBroadcastReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public RxNetworkBroadcastReceiver provideNetworkBroadcastReceiver() {
        return new RxNetworkBroadcastReceiver(app);
    }

}
