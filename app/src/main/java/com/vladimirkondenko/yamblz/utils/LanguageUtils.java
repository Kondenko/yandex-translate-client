package com.vladimirkondenko.yamblz.utils;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.gson.GsonBuilder;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Languages;

import java.util.Locale;

public class LanguageUtils {

    private static final String DIRECTION_SEPARATOR = "-";

    /**
     * Creates a dash-separated pair of language which will be used
     * as a URL parameter when sending a translation request.
     */
    public static String langsToDirection(String langInput, String langOutput) {
        return langInput + DIRECTION_SEPARATOR + langOutput;
    }

    /**
     * Parses a pair of languages to an array of 2 languages
     * the first of which is the input language
     * and the second is the output language.
     */
    public static String[] parseDirection(String direction) {
        return direction.split(DIRECTION_SEPARATOR);
    }

    /**
     * Returns a Languages object for Russian or English language,
     * which are considered the most commonly used.
     * This is needed to prevent unnecessary network calls in
     * most cases.
     *
     * @return a list of languages in Russian, if the user device's locale
     * is Russian, English otherwise.
     */
    public static Languages getInputLanguages(Context context) {
        String locale = getDeviceLocale();
        int jsonResId;
        String defaultInputLanguage;
        if (locale.equalsIgnoreCase(Const.LANG_CODE_RU)) {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_RU;
            defaultInputLanguage = Const.LANG_CODE_RU;
        } else {
            jsonResId = Const.INPUT_LANGUAGES_JSON_RES_EN;
            defaultInputLanguage = Const.LANG_CODE_EN;
        }
        Languages languages = getLangsFromRawRes(context, jsonResId);
        languages.setUserLanguageCode(defaultInputLanguage);
        return languages;
    }

    /**
     * Retrieves a raw json file from application resources.
     */
    public static Languages getLangsFromRawRes(Context context, @RawRes int jsonResId) {
        String json = Utils.getJsonFromRawResources(context, jsonResId);
        return new GsonBuilder().create().fromJson(json, Languages.class);
    }

    public static String getDeviceLocale() {
        return Locale.getDefault().getLanguage();
    }

}
