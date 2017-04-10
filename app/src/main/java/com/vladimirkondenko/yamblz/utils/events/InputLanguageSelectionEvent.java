package com.vladimirkondenko.yamblz.utils.events;

public class InputLanguageSelectionEvent {

    private final String lang;

    public InputLanguageSelectionEvent(String lang) {
        this.lang = lang;
    }

    public String getInputLang() {
        return lang;
    }


}
