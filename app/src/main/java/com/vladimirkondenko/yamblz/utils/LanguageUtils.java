package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.gson.GsonBuilder;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Languages;

import java.util.Locale;

public class LanguageUtils {

    public static String langsToDirection(String langFrom, String langTo) {
        return String.format("%s-%s", langFrom, langTo);
    }

    public static Languages getInputLanguages(Context context) {
        String locale = getDeviceLocale();
        int jsonResId;
        String forLanguage;
        if (locale.equalsIgnoreCase(Const.LANG_CODE_RU)) {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_RU;
            forLanguage = Const.LANG_CODE_RU;
        } else {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_EN;
            forLanguage = Const.LANG_CODE_EN;
        }
        Languages languages = getLangsFromRawRes(context, jsonResId);
        languages.setUserLanguageCode(forLanguage);
        return languages;
    }

    public static Languages getLangsFromRawRes(Context context, @RawRes int jsonResId) {
        String json = Utils.getJsonFromRawResources(context, jsonResId);
        return new GsonBuilder().create().fromJson(json, Languages.class);
    }

    public static String getDeviceLocale() {
        return Locale.getDefault().getLanguage();
    }
}
