package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseDatabaseService;

import io.realm.RealmResults;

public interface DbTranslationService extends BaseDatabaseService {

    void saveToHistory(Translation translation);

    RealmResults<Translation> getHistory();

    void setBookmarked(int translationId, boolean value);

}
