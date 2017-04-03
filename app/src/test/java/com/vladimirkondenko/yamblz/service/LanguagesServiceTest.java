package com.vladimirkondenko.yamblz.service;

import com.vladimirkondenko.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
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
    public MockWebServer server;

    @Inject
    public LanguagesService service;

    @Before
    public void setUp() {
        TestApp.plusTestServiceSubcomponent(new TestServiceModule()).inject(this);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
        TestApp.clearTestServiceSubcomponent();
    }

    @Test
    public void shouldReturnLanguages() {
        String json = Utils.getJsonFromAsset(RuntimeEnvironment.application.getBaseContext(), R.raw.input_languages_ru);
        server.enqueue(new MockResponse().setBody(json));
        TestObserver<LanguagesHolder> testObserver = TestObserver.create();
        service.getAvailableLanguages("ru").subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

}