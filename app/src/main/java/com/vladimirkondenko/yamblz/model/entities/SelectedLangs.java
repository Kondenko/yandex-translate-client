package com.vladimirkondenko.yamblz.model.entities;

import io.realm.RealmObject;

/**
 * The pair of languages user selects to translate text.
 * E.g. English - Italian
 */
public class SelectedLangs extends RealmObject {

    private String inputLang;

    private String outputLang;

    public String getOutputLang() {
        return outputLang;
    }

    public void setOutputLang(String outputLang) {
        this.outputLang = outputLang;
    }

    public String getInputLang() {
        return inputLang;
    }

    public void setInputLang(String inputLang) {
        this.inputLang = inputLang;
    }

}
