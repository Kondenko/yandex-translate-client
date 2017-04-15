package com.vladimirkondenko.yamblz.dagger.modules;

import android.content.Context;

import org.robolectric.RuntimeEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAppModule {

    @Provides
    @Singleton
    public Context provideContext() {
        return RuntimeEnvironment.application.getBaseContext();
    }

}
