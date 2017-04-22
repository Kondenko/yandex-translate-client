package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(CustomRobolectricTestRunner.class)
public class LanguagesServiceTest {

    @Inject
    public NetLanguagesService service;

    @Inject
    public MockWebServer server;

    @Before
    public void setUp() {
        TestApp.get().plusTestServiceSubcomponent(new TestServiceModule()).inject(this);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
        TestApp.get().clearTestServiceSubcomponent();
    }

    @Test
    public void shouldReturnLanguages() {
        String json = Utils.getJsonFromRawResources(RuntimeEnvironment.application.getBaseContext(), R.raw.input_languages_ru);
        server.enqueue(new MockResponse().setBody(json));
        TestObserver<Languages> testObserver = TestObserver.create();
        service.getAvailableLanguages("ru").subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

}