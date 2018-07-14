package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.widget.TextView;

import com.vladimirkondenko.yamblz.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public final class Utils {

    private static final String DEFAULT_JSON_ENCODING = "UTF-8";

    public static boolean areFieldsEmpty(TextView... textViews) {
        for (TextView textView : textViews) {
            if (isEmpty(textView)) return true;
        }
        return false;
    }

    public static boolean isEmpty(TextView textView) {
        return textView.getText() == null || textView.getText().toString().trim().length() == 0;
    }

    public static Drawable getTintedIcon(Context context, @DrawableRes int drawableId) {
        return getTintedDrawable(context, drawableId, R.color.all_icon_statelist);
    }

    public static Drawable getTintedDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorSelector) {
        Drawable drawable = DrawableCompat.wrap(Utils.getDrawable(context, drawableId));
        DrawableCompat.setTintList(drawable.mutate(), AppCompatResources.getColorStateList(context, colorSelector));
        return drawable;
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(drawableId);
        } else {
            return context.getResources().getDrawable(drawableId, context.getTheme());
        }
    }

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

    public static long getCurrentTimeSec() {
        return System.currentTimeMillis() / 1000;
    }

    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static int hashCode(Object... objects) {
        int prime = 31;
        int result = 1;
        for (Object o : objects) result = result * prime + o.hashCode();
        return result;
    }

}
