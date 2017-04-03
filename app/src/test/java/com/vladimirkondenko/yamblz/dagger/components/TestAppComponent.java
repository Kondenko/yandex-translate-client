package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestNetModule.class})
public interface TestAppComponent {
    TestMainPresenterSubcomponent plus(TestMainPresenterModule module);
    TestServiceSubcomponent plus(TestServiceModule module);
}
