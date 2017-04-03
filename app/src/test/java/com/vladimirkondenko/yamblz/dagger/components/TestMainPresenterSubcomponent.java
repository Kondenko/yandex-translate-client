package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.screens.main.MainPresenterTest;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {TestMainPresenterModule.class})
public interface TestMainPresenterSubcomponent {
    void inject(MainPresenterTest test);
}
