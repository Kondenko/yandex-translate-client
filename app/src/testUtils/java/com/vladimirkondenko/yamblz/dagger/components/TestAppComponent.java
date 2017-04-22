package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.modules.TestAppModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestTranslationModule;
import com.vladimirkondenko.yamblz.utils.ApiKeyInterceptorTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestAppModule.class, TestNetModule.class})
public interface TestAppComponent {
    TestServiceSubcomponent plus(TestServiceModule module);
    TestMainPresenterSubcomponent plus(TestMainModule module);
    TestTranslationPresenterSubcomponent plus(TestTranslationModule module);

    void inject(ApiKeyInterceptorTest test);
}
