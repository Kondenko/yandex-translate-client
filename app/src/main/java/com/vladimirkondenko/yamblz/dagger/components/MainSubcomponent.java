package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.MainModule;
import com.vladimirkondenko.yamblz.model.database.Database;
import com.vladimirkondenko.yamblz.screens.main.MainActivity;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {MainModule.class})
public interface MainSubcomponent extends BaseSubcomponent<MainActivity, MainPresenter> {
    @Override
    void inject(MainActivity view);
    Database getDatabase();
}
