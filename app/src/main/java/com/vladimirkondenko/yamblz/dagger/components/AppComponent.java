package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.DatabaseModule;
import com.vladimirkondenko.yamblz.dagger.modules.FragmentModule;
import com.vladimirkondenko.yamblz.dagger.modules.HistoryModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FragmentModule.class, NetModule.class, DatabaseModule.class})
public interface AppComponent {
    MainSubcomponent plus(MainModule mainModule);
    TranslationSubcomponent plus(TranslationModule translationPresenterModule);
    HistorySubcomponent plus(HistoryModule historyModule);
}
