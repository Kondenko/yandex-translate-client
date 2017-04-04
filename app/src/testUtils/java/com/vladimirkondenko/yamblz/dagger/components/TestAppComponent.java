package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.utils.ApiKeyInterceptorTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestNetModule.class})
public interface TestAppComponent {
    com.vladimirkondenko.yamblz.dagger.components.TestMainPresenterSubcomponent plus(TestMainPresenterModule module);
    com.vladimirkondenko.yamblz.dagger.components.TestServiceSubcomponent plus(TestServiceModule module);

    void inject(ApiKeyInterceptorTest test);
}
