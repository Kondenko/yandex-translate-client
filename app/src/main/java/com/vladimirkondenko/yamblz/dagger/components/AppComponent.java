package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class})
public interface AppComponent {
    TranslationPresenterSubcomponent plus(TranslationPresenterModule translationPresenterModule);
    MainPresenterSubcomponent plus(MainPresenterModule translationPresenterModule);

    void inject(TranslationView view);
}
