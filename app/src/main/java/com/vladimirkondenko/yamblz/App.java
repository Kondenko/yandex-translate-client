package com.vladimirkondenko.yamblz;

import android.app.Application;

import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.components.DaggerAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.MainPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TranslationPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;

public class App extends Application {
    
    protected static App instance;

    protected AppComponent appComponent = null;
    protected TranslationPresenterSubcomponent translationPresenterComponent;
    protected MainPresenterSubcomponent mainPresenterSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = getAppComponent();
    }

    public static App get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .netModule(new NetModule())
                    .build();
        }
        return appComponent;
    }

    public TranslationPresenterSubcomponent plusTranslationSubcomponent(TranslationPresenterModule module) {
        if (translationPresenterComponent == null) {
            translationPresenterComponent = appComponent.plus(module);
        }
        return translationPresenterComponent;
    }

    public MainPresenterSubcomponent plusMainSubcomponent(MainPresenterModule module) {
        if (mainPresenterSubcomponent == null) {
            mainPresenterSubcomponent = appComponent.plus(module);
        }
        return mainPresenterSubcomponent;
    }


    public void clearTranslationPresenterComponent() {
        translationPresenterComponent = null;
    }

    public void clearMainPresenterComponent() {
        mainPresenterSubcomponent = null;
    }

}
