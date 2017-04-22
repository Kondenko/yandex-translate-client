package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.DbTranslationService;

import io.realm.RealmResults;

public class DbHistoryServiceImpl extends DatabaseUserClass implements DbTranslationService {

    private RealmResults<Translation> historyQueryResults = null;

    public DbHistoryServiceImpl(Database database) {
        super(database);
    }

    public void setSavedToHistory(Translation translation, boolean saveToHistory, long timestamp) {
        performTransaction(() -> {
            if (!translation.isBookmarked() && !saveToHistory) {
                delete(translation);
            } else {
                translation.setTimestamp(timestamp);
                translation.setId(translation.calculateId());
                translation.setSavedToHistory(saveToHistory);
                realm.copyToRealmOrUpdate(translation);
            }
        });
    }

    public void setBookmarked(Translation translation, boolean bookmarked) {
        performTransaction(() -> {
            if (!translation.isSavedToHistory() && !bookmarked) {
                delete(translation);
            } else {
                translation.setBookmarked(bookmarked);
                realm.copyToRealmOrUpdate(translation);
            }
        });
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
