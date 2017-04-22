package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainModule;
import com.vladimirkondenko.yamblz.screens.main.MainPresenterTest;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {TestMainModule.class})
public interface TestMainPresenterSubcomponent {
    void inject(MainPresenterTest test);
}
