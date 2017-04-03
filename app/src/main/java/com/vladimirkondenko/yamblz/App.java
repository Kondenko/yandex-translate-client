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

    protected static AppComponent appComponent = null;
    protected static TranslationPresenterSubcomponent translationPresenterComponent;
    protected static MainPresenterSubcomponent mainPresenterSubcomponent;

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

    public static TranslationPresenterSubcomponent plusTranslationSubcomponent(TranslationPresenterModule module) {
        if (translationPresenterComponent == null) {
            translationPresenterComponent = appComponent.plus(module);
        }
        return translationPresenterComponent;
    }

    public static MainPresenterSubcomponent plusMainSubcomponent(MainPresenterModule module) {
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
