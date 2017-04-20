package com.vladimirkondenko.yamblz.screens.history;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.DbTranslationService;
import com.vladimirkondenko.yamblz.utils.base.BaseInteractor;

import javax.inject.Inject;

import io.realm.RealmResults;

public class HistoryInteractor extends BaseInteractor {

    private DbTranslationService databaseService;

    @Inject
    public HistoryInteractor(DbTranslationService databaseService) {
        this.databaseService = databaseService;
    }

    public RealmResults<Translation> getHistory() {
        return databaseService.getHistory();
    }

    public void setBookmarked(int id, boolean value) {
        databaseService.setBookmarked(id, value);
    }

    public void cleanup() {
        databaseService.cleanup();
    }

}
