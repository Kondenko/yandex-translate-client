package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.SelectedLangs;

public interface LanguagesDbService {

    void saveLangs(String inputLang, String outputLang);

    SelectedLangs getSelectedLangs();

    boolean areLangsSaved();

}
