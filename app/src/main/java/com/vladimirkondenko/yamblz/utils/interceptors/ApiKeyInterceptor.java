package com.vladimirkondenko.yamblz.utils.interceptors;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.vladimirkondenko.yamblz.Const.REQUEST_KEY_API_KEY;

/**
 * Adds the API key to every request sent.
 */
public class ApiKeyInterceptor implements Interceptor {

    private String apiKey;

    @Inject
    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(REQUEST_KEY_API_KEY, apiKey).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
