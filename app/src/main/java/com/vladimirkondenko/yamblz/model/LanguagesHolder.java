package com.vladimirkondenko.yamblz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedHashTreeMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Holds all available for translation languages for a single language.
 */
public class LanguagesHolder {

    @SerializedName("dirs")
    @Expose
    @Deprecated
    public List<String> dirs = null;

    @SerializedName("langs")
    @Expose
    public LinkedHashMap<String, String> languages = null;

    @Override
    public String toString() {
        return dirs.toString() + '\n' + languages.toString();
    }
}
