package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.TestUtils;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.model.entities.Translation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(CustomRobolectricTestRunner.class)
public class TranslationServiceTest {

    @Inject
    public NetTranslationService service;

    @Inject
    public MockWebServer server;

    @Before
    public void setUp() throws Exception {
        TestApp.get().plusTestServiceSubcomponent(new TestServiceModule()).inject(this);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
        TestApp.get().clearTestServiceSubcomponent();
    }

    @Test
    public void getTranslation() throws Exception {
        String json = TestUtils.getJsonFromTestAssets("translation_en_ru_success.json");
        MockResponse response = new MockResponse().setBody(json);
        server.enqueue(response);
        TestObserver<Translation> observer = TestObserver.create();
        service.getTranslation("Hello, world!", "en-ru")
                .subscribe(observer);
        observer.assertComplete();
        observer.assertNoErrors();
    }

}