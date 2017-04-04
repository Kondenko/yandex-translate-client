package com.vladimirkondenko.yamblz.utils;

import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.utils.interceptors.ApiKeyInterceptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertTrue;

@RunWith(CustomRobolectricTestRunner.class)
public class ApiKeyInterceptorTest {

    @Inject
    public OkHttpClient client;

    @Inject
    public MockWebServer server;

    @Before
    public void setUp() throws Exception {
        TestApp.getTestAppComponent().inject(this);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void checkApiKeyGetsAttachedToRequest() throws Exception {
        String apiKey = "API-KEY-EXAMPLE";
        ApiKeyInterceptor interceptor = new ApiKeyInterceptor(apiKey);

        client = client.newBuilder().addInterceptor(interceptor).build();

        server.enqueue(new MockResponse());
        client.newCall(new Request.Builder()
                .url(server.url("/"))
                .build()
        ).execute();

        RecordedRequest request = server.takeRequest();
        String path = request.getPath();

        assertTrue(path.contains(apiKey));
    }

}