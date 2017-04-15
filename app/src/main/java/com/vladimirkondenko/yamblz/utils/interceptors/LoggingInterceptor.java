package com.vladimirkondenko.yamblz.utils.interceptors;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "HTTP-Log";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response responce = chain.proceed(request);
        Log.i(TAG, request.toString());
        Log.i(TAG, responce.toString());
        return responce;
    }

}
