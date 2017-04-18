package com.vladimirkondenko.yamblz;


import android.app.Application;

import com.vladimirkondenko.yamblz.dagger.components.DaggerTestAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.TestAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.TestMainPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TestServiceSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TestTranslationPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.TestAppModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestTranslationModule;

public class TestApp extends Application {

    protected static TestApp instance;

    private TestAppComponent testAppComponent;
    private TestServiceSubcomponent testServiceSubcomponent;
    private TestTranslationPresenterSubcomponent testTranslationPresenterSubcomponent;
    private TestMainPresenterSubcomponent testMainPresenterSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        testAppComponent = getAppComponent();
    }

    public static TestApp get() {
        return instance;
    }

    public TestAppComponent getTestAppComponent() {
        return testAppComponent;
    }

    public TestAppComponent getAppComponent() {
        if (testAppComponent == null) {
            testAppComponent = DaggerTestAppComponent.builder()
                    .testAppModule(new TestAppModule())
                    .testNetModule(new TestNetModule())
                    .build();
        }
        return testAppComponent;
    }

    public TestServiceSubcomponent plusTestServiceSubcomponent(TestServiceModule module) {
        if (testServiceSubcomponent == null) {
            testServiceSubcomponent = testAppComponent.plus(module);
        }
        return testServiceSubcomponent;
    }

    public TestMainPresenterSubcomponent plusTestMainPresenterSubcomponent(TestMainModule module) {
        if (testMainPresenterSubcomponent == null) {
            testMainPresenterSubcomponent = testAppComponent.plus(module);
        }
        return testMainPresenterSubcomponent;
    }

    public TestTranslationPresenterSubcomponent plusTestTranslationPresenterSubcomponent(TestTranslationModule module) {
        if (testServiceSubcomponent == null) {
            testTranslationPresenterSubcomponent = testAppComponent.plus(module);
        }
        return testTranslationPresenterSubcomponent;
    }

    public void clearTestServiceSubcomponent() {
        testServiceSubcomponent = null;
    }

    public void clearTestMainPresenterSubcomponent() {
        testMainPresenterSubcomponent = null;
    }

    public void clearTestTranslationPresenterSubcomponent() {
        testTranslationPresenterSubcomponent = null;
    }

}
