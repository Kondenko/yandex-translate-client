package com.vladimirkondenko.yamblz.utils.events;

public class OutputLanguageSelectionEvent {

    private final String lang;

    public OutputLanguageSelectionEvent(String lang) {
        this.lang = lang;
    }

    public String getOutputLang() {
        return lang;
    }

}
