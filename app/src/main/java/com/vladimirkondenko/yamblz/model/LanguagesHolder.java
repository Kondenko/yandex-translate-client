package com.vladimirkondenko.yamblz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedHashTreeMap;
import com.vladimirkondenko.yamblz.Const;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all available for translation languages for a single language.
 */
public class LanguagesHolder {

    public String forLanguage = Const.LOCALE_EN;

    @SerializedName("dirs")
    @Expose
    @Deprecated
    public final List<String> dirs = Collections.emptyList();

    @SerializedName("langs")
    @Expose
    public final LinkedHashMap<String, String> languages = new LinkedHashMap<>();

    @Override
    public String toString() {
        return dirs.toString() + '\n' + languages.toString();
    }

}
