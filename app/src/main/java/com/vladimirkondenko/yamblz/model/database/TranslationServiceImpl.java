package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.TranslationDatabaseService;
import com.vladimirkondenko.yamblz.utils.base.DatabaseUserClass;

public class TranslationServiceImpl extends DatabaseUserClass implements TranslationDatabaseService {

    public TranslationServiceImpl(Database database) {
        super(database);
    }

    @Override
    public void saveToHistory(Translation translation) {
        performTransaction(() -> {
            realm.copyToRealmOrUpdate(translation);
        });
    }

}
