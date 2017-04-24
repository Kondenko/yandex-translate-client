package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseDatabaseService;

import io.reactivex.Single;
import io.realm.RealmResults;

public interface DbSavedTranslationsService extends BaseDatabaseService {

    RealmResults<Translation> getHistory();

    RealmResults<Translation> getBookmarks();

    void setBookmarked(Translation translation, boolean bookmarked, long timestamp);

    void setSavedToHistory(Translation translation, boolean saveToHistory, long timestamp);

    Single<Translation> getIfSaved(String inputText, String direction);

    void setInputText(Translation translation, String inputText);

}