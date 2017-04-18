package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.entities.SelectedLangs;
import com.vladimirkondenko.yamblz.model.services.LanguagesDatabaseService;
import com.vladimirkondenko.yamblz.utils.base.DatabaseUserClass;

import javax.inject.Inject;

public class LanguagesServiceImpl extends DatabaseUserClass implements LanguagesDatabaseService {


    @Inject
    public LanguagesServiceImpl(Database database) {
        super(database);
    }

    @Override
    public void saveLangs(String inputLang, String outputLang) {
        performTransaction(() -> {
                    SelectedLangs langs = new SelectedLangs();
                    langs.setInputLang(inputLang);
                    langs.setOutputLang(outputLang);
                    realm.copyToRealmOrUpdate(langs);
                }
        );

    }

    public SelectedLangs getSelectedLangs() {
        return realm.where(SelectedLangs.class).findFirst();
    }

    public boolean areLangsSaved() {
        return getSelectedLangs() != null;
    }

}
