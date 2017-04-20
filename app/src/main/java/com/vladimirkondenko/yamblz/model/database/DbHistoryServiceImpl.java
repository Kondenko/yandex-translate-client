package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.DbTranslationService;
import com.vladimirkondenko.yamblz.utils.base.DatabaseUserClass;

import io.realm.RealmResults;

public class DbHistoryServiceImpl extends DatabaseUserClass implements DbTranslationService {

    private RealmResults<Translation> historyQueryResults = null;

    public DbHistoryServiceImpl(Database database) {
        super(database);
    }

    @Override
    public void saveToHistory(Translation translation) {
        performTransaction(() -> realm.copyToRealmOrUpdate(translation));
    }

    public void setBookmarked(int translationId, boolean value) {
        performTransaction(() ->
                realm.where(Translation.class)
                .equalTo(Translation.FIELD_NAME_PRIMARY_KEY, translationId)
                .findFirst()
                .setBookmarked(value)
        );
    }

    @Override
    public RealmResults<Translation> getHistory() {
        historyQueryResults = realm
                .where(Translation.class)
                .equalTo(Translation.FIELD_NAME_SAVED_TO_HISTORY, true)
                .findAllSortedAsync(Translation.FIELD_NAME_TIMESTAMP);
        return historyQueryResults;
    }

    public void cleanup() {
        if (historyQueryResults != null) historyQueryResults.removeAllChangeListeners();
    }

}
