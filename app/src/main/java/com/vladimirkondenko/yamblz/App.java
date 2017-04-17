package com.vladimirkondenko.yamblz;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.components.DaggerAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.MainPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TranslationPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.DatabaseModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.model.database.Database;

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
        Stetho.initializeWithDefaults(this);
        Database.init(this);
    }

    public static App get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .netModule(new NetModule())
                    .databaseModule(new DatabaseModule())
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
