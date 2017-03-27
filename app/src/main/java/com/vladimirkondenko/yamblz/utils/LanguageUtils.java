package com.vladimirkondenko.yamblz.utils;

import com.vladimirkondenko.yamblz.Const;

import java.util.Locale;

public class LanguageUtils {

    /**
     * Returns user's default locale if available, default locale otherwise.
     */
    public static String getDeviceLanguage() {
        String locale = Locale.getDefault().getLanguage();
        return locale != null ? locale : Const.DEFAULT_LOCALE_TO;
    }

    /**
     * Returns a locale which will be used to display all languages
     * from which text can be translated.
     */
    public static String getDefaultLocale() {
        return Const.DEFAULT_LOCALE_FROM;
    }

}
