package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.entities.SelectedLangs;
import com.vladimirkondenko.yamblz.model.services.LanguagesDbService;

import javax.inject.Inject;

import io.realm.Realm;

public class LanguagesServiceImpl implements LanguagesDbService {

    private Realm realm;

    @Inject
    public LanguagesServiceImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void saveLangs(String inputLang, String outputLang) {
        realm.executeTransaction(transaction -> {
            SelectedLangs langs = realm.createObject(SelectedLangs.class);
            langs.setInputLang(inputLang);
            langs.setOutputLang(outputLang);
        });
    }

    public SelectedLangs getSelectedLangs() {
        return realm.where(SelectedLangs.class).findFirst();
    }

    public boolean areLangsSaved() {
        return getSelectedLangs() != null;
    }

}
