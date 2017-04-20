package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.SelectedLangs;
import com.vladimirkondenko.yamblz.utils.base.BaseDatabaseService;

public interface DbLanguagesService extends BaseDatabaseService {

    void saveLangs(String inputLang, String outputLang);

    SelectedLangs getSelectedLangs();

    boolean areLangsSaved();

}
