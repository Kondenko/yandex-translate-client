package com.vladimirkondenko.yamblz.utils.events;

public class LanguageDetectionEvent {

    private final String lang;

    public LanguageDetectionEvent(String lang) {
        this.lang = lang;
    }

    public String getDetectedLang() {
        return lang;
    }

}
