package com.vladimirkondenko.yamblz.dagger.components;


import android.content.Context;

import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.fragments.translation.TranslationView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    TranslationPresenterSubcomponent plus(TranslationPresenterModule translationPresenterModule);

    void inject(TranslationView view);
}
