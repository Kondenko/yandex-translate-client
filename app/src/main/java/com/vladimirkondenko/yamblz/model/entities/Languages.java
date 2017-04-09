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
public final class Languages {

    // The language in which all other languages are stored in the languages variable
    private String userLanguageCode = Const.LANG_CODE_EN;

    @SerializedName("dirs")
    @Expose
    @Deprecated
    private List<String> dirs = Collections.emptyList();

    @SerializedName("langs")
    @Expose
    private LinkedHashMap<String, String> languages = new LinkedHashMap<>();

    public String getUserLanguageCode() {
        return userLanguageCode;

    }

    public void setUserLanguageCode(String userLanguageCode) {
        this.userLanguageCode = userLanguageCode;
    }

    public LinkedHashMap<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(LinkedHashMap<String, String> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return dirs.toString() + '\n' + languages.toString();
    }

}
