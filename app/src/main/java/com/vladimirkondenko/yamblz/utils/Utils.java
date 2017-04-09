package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public abstract class Utils {

    private static final String DEFAULT_JSON_ENCODING = "UTF-8";

    public static <K , V extends Comparable> LinkedHashMap<K, V> sortByValues(LinkedHashMap<K, V> map) {
        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();
        LinkedList<Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (entryA, entryB) -> entryA.getValue().compareTo(entryB.getValue()));
        for (Iterator<Map.Entry<K, V>> it = entries.iterator(); it.hasNext(); ) {
            Map.Entry<K, V> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static String getJsonFromRawResources(Context context, @RawRes int rawResourceName) {
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
