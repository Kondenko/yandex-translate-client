package com.vladimirkondenko.yamblz.utils;

import com.vladimirkondenko.yamblz.utils.interceptors.ApiKeyInterceptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertTrue;

public class ApiKeyInterceptorTest {

    private String apiKey = "API-KEY-EXAMPLE";

    private ApiKeyInterceptor interceptor = new ApiKeyInterceptor(apiKey);
    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    private MockWebServer server =  new MockWebServer();

    @Before
    public void setUp() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void checkApiKeyGetsAttachedToRequest() throws Exception {
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