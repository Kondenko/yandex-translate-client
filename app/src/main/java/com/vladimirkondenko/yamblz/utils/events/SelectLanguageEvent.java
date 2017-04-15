package com.vladimirkondenko.yamblz.utils.events;

/**
 * Created by Kondenko on 10.04.2017.
 */

public class SelectLanguageEvent {

    private final String lang;

    public SelectLanguageEvent(String lang) {
        this.lang = lang;
    }

    public String getSelectedLang() {
        return lang;
    }

}
