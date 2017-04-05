package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vladimirkondenko.yamblz.Const;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Holds all available for translation languages for a single language.
 */
public final class LanguagesHolder {

    // The language in which all other languages are stored in the languages variable
    private String userLanguage = Const.LOCALE_EN;

    @SerializedName("dirs")
    @Expose
    @Deprecated
    private List<String> dirs = Collections.emptyList();

    @SerializedName("langs")
    @Expose
    private LinkedHashMap<String, String> languages = new LinkedHashMap<>();

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserLanguage() {
        return userLanguage;

    }
    public LinkedHashMap<String, String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return dirs.toString() + '\n' + languages.toString();
    }

}
