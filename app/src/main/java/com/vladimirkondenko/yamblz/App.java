package com.vladimirkondenko.yamblz;

import android.app.Application;
import android.content.Context;

import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.components.DaggerAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.TranslationPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;

public class App extends Application {

    private static App instance = null;

    private static AppComponent appComponent = null;
    private static  TranslationPresenterSubcomponent translationPresenterComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = getAppComponent();
    }

    public static App get() {
//        if (instance == null) instance = (App) context;
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    public TranslationPresenterSubcomponent plus(TranslationPresenterModule module) {
        if (translationPresenterComponent == null) {
            translationPresenterComponent = appComponent.plus(module);
        }
        return translationPresenterComponent;
    }

    public void clearTranslationPresenterComponent() {
        translationPresenterComponent = null;
    }

}
