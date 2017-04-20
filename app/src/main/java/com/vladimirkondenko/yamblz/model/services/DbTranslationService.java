package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseDatabaseService;

import io.realm.RealmResults;

public interface DbTranslationService extends BaseDatabaseService {

    RealmResults<Translation> getHistory();

    void setBookmarked(Translation translation, boolean bookmarked);

    void setSavedToHistory(Translation translation, boolean saveToHistory, long timestamp);

}