package com.vladimirkondenko.yamblz;

import android.app.Application;

import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.components.DaggerAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.HistorySubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.MainSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TranslationSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.DatabaseModule;
import com.vladimirkondenko.yamblz.dagger.modules.FragmentModule;
import com.vladimirkondenko.yamblz.dagger.modules.HistoryModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationModule;
import com.vladimirkondenko.yamblz.model.database.Database;

public class App extends Application {
    
    protected static App instance;

    protected AppComponent appComponent = null;

    protected MainSubcomponent mainPresenterSubcomponent;

    protected TranslationSubcomponent translationPresenterComponent;

    protected HistorySubcomponent historySubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = getAppComponent();
        Database.init(this);
    }

    public static App get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .fragmentModule(new FragmentModule())
                    .netModule(new NetModule())
                    .databaseModule(new DatabaseModule())
                    .build();
        }
        return appComponent;
    }

    public MainSubcomponent plusMainSubcomponent(MainModule module) {
        if (mainPresenterSubcomponent == null) {
            mainPresenterSubcomponent = appComponent.plus(module);
        }
        return mainPresenterSubcomponent;
    }

    public TranslationSubcomponent plusTranslationSubcomponent(TranslationModule module) {
        if (translationPresenterComponent == null) {
            translationPresenterComponent = appComponent.plus(module);
        }
        return translationPresenterComponent;
    }

    public HistorySubcomponent plusHistorySubcomponent(HistoryModule module) {
        if (historySubcomponent == null) {
            historySubcomponent = appComponent.plus(module);
        }
        return historySubcomponent;
    }

    public void clearMainPresenterComponent() {
        mainPresenterSubcomponent.getDatabase().close();
        mainPresenterSubcomponent = null;
    }

    public void clearTranslationPresenterComponent() {
        translationPresenterComponent = null;
    }

    public void clearHistorySubcomponent() {
        historySubcomponent = null;
    }

}
