package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.AppModule;
import com.vladimirkondenko.yamblz.dagger.modules.DatabaseModule;
import com.vladimirkondenko.yamblz.dagger.modules.FragmentModule;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.NetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FragmentModule.class, NetModule.class, DatabaseModule.class})
public interface AppComponent {
    MainPresenterSubcomponent plus(MainPresenterModule translationPresenterModule);
    TranslationPresenterSubcomponent plus(TranslationPresenterModule translationPresenterModule);
}
