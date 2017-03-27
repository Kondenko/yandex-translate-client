package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;

public abstract class Utils {

    private static final String DEFAULT_JSON_ENCODING = "UTF-8";

    public static String getJsonFromAsset(Context context, @RawRes int rawResourceName) {
        String json = "";
        try {
            InputStream is = context.getResources().openRawResource(rawResourceName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, DEFAULT_JSON_ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public static void disposeAll(Disposable... disposables) {
        for (Disposable disposable : disposables) {
            dispose(disposable);
        }
    }

    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
