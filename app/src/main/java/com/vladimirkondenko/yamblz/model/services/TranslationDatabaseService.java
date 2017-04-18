package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Translation;

public interface TranslationDatabaseService {

    void saveToHistory(Translation translation);

}
