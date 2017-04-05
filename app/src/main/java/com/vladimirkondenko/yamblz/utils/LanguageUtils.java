package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.gson.GsonBuilder;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.LanguagesHolder;

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
        LanguagesHolder languagesHolder = getLangsFromRawRes(context, jsonResId);
        languagesHolder.setUserLanguage(forLanguage);
        return languagesHolder;
    }

    public static LanguagesHolder getLangsFromRawRes(Context context, @RawRes int jsonResId) {
        String json = Utils.getJsonFromAsset(context, jsonResId);
        return new GsonBuilder().create().fromJson(json, LanguagesHolder.class);
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
