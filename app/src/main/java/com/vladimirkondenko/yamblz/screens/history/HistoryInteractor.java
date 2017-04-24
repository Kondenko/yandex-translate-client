package com.vladimirkondenko.yamblz.screens.history;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.DbSavedTranslationsService;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.base.BaseInteractor;

import javax.inject.Inject;

import io.realm.RealmResults;

public class HistoryInteractor extends BaseInteractor {

    private DbSavedTranslationsService databaseService;

    @Inject
    public HistoryInteractor(DbSavedTranslationsService databaseService) {
        this.databaseService = databaseService;
    }

    public RealmResults<Translation> getHistory() {
        return databaseService.getHistory();
    }

    public RealmResults<Translation> getBookmarks() {
        return databaseService.getBookmarks();
    }

    public void removeFromHistory(Translation translation) {
        databaseService.setSavedToHistory(translation, false, 0);
    }

    public void setBookmarked(Translation translation, boolean bookmarked) {
        long timestamp = Utils.getCurrentTimeSec();
        databaseService.setBookmarked(translation, bookmarked, timestamp);
    }

}
