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

    public void removeFromHistory(Translation translation) {
        databaseService.setSavedToHistory(translation, false, 0);
    }

    public void setBookmarked(Translation translation, boolean bookmarked) {
        databaseService.setBookmarked(translation, bookmarked);
    }

    public void cleanup() {
        databaseService.cleanup();
    }

}
