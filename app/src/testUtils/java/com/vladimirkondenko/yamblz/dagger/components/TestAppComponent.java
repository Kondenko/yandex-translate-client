package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.TestAppModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestTranslationPresenterModule;
import com.vladimirkondenko.yamblz.utils.ApiKeyInterceptorTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class, TestNetModule.class})
public interface TestAppComponent {
    TestServiceSubcomponent plus(TestServiceModule module);
    TestMainPresenterSubcomponent plus(TestMainPresenterModule module);
    TestTranslationPresenterSubcomponent plus(TestTranslationPresenterModule module);

    void inject(ApiKeyInterceptorTest test);
}
