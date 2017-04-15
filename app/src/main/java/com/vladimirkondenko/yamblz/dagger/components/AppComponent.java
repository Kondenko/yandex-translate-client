package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    TranslationPresenterSubcomponent plus(TranslationPresenterModule translationPresenterModule);
    MainPresenterSubcomponent plus(MainPresenterModule translationPresenterModule);
}
