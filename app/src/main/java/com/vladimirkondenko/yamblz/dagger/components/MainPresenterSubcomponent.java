package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.screens.main.MainActivity;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {MainPresenterModule.class})
@PerView
public interface MainPresenterSubcomponent extends BasePresenterSubcomponent<MainActivity, MainPresenter> {
    @Override
    public void inject(MainActivity fragment);
}
