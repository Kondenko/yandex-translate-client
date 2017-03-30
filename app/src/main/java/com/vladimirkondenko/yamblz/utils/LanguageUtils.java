package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;

import java.util.Locale;

public class LanguageUtils {

    public static LanguagesHolder getInputLanguages(Context context) {
        String locale = getDeviceLocale();
        int jsonResId;
        String forLanguage;
        if (locale.equalsIgnoreCase(Const.LOCALE_RU)) {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_RU;
            forLanguage = Const.LOCALE_RU;
        } else {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_EN;
            forLanguage = Const.LOCALE_EN;
        }
        String json = Utils.getJsonFromAsset(context, jsonResId);
        LanguagesHolder languagesHolder = new GsonBuilder().create().fromJson(json, LanguagesHolder.class);
        languagesHolder.forLanguage = forLanguage;
        return languagesHolder;
    }

    public static String getPreferredLocale() {
        String locale = Locale.getDefault().getLanguage();
        if (locale.equalsIgnoreCase(Const.LOCALE_RU)) {
            return Const.LOCALE_RU;
        }
        return Const.LOCALE_EN;
    }

    public static String getDeviceLocale() {
        return Locale.getDefault().getLanguage();
    }
}
