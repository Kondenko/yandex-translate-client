package com.vladimirkondenko.yamblz;


import android.app.Application;

import com.vladimirkondenko.yamblz.dagger.components.DaggerTestAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.TestAppComponent;
import com.vladimirkondenko.yamblz.dagger.components.TestMainPresenterSubcomponent;
import com.vladimirkondenko.yamblz.dagger.components.TestServiceSubcomponent;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestNetModule;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;

public class TestApp extends Application {

    private static TestAppComponent testAppComponent;
    private static TestMainPresenterSubcomponent testMainPresenterSubcomponent;
    private static TestServiceSubcomponent testServiceSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        testAppComponent = DaggerTestAppComponent.builder()
                .testNetModule(new TestNetModule())
                .build();
    }

    public static TestAppComponent getTestAppComponent() {
        return testAppComponent;
    }

    public static TestServiceSubcomponent plusTestServiceSubcomponent(TestServiceModule module) {
        if (testServiceSubcomponent == null) {
            testServiceSubcomponent = testAppComponent.plus(module);
        }
        return testServiceSubcomponent;
    }


    public static TestMainPresenterSubcomponent plusTestMainPresenterSubcomponent(TestMainPresenterModule module) {
        if (testMainPresenterSubcomponent == null) {
            testMainPresenterSubcomponent = testAppComponent.plus(module);
        }
        return testMainPresenterSubcomponent;
    }

    public static void clearTestMainPresenterSubcomponent() {
        testMainPresenterSubcomponent = null;
    }

    public static void clearTestServiceSubcomponent() {
        testServiceSubcomponent = null;
    }

}
