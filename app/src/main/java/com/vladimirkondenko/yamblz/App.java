package com.vladimirkondenko.yamblz;

import android.app.Application;

import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.components.DaggerAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.MainPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TranslationPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;

public class App extends Application {

    private static AppComponent appComponent = null;
    private static TranslationPresenterSubcomponent translationPresenterComponent;
    private static MainPresenterSubcomponent mainPresenterSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = getAppComponent();
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .netModule(new NetModule())
                    .build();
        }
        return appComponent;
    }

    public static TranslationPresenterSubcomponent plus(TranslationPresenterModule module) {
        if (translationPresenterComponent == null) {
            translationPresenterComponent = appComponent.plus(module);
        }
        return translationPresenterComponent;
    }

    public static MainPresenterSubcomponent plus(MainPresenterModule module) {
        if (mainPresenterSubcomponent == null) {
            mainPresenterSubcomponent = appComponent.plus(module);
        }
        return mainPresenterSubcomponent;
    }


    public static void clearTranslationPresenterComponent() {
        translationPresenterComponent = null;
    }

    public static void clearMainPresenterComponent() {
        mainPresenterSubcomponent = null;
    }

}
