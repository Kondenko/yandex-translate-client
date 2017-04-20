package com.vladimirkondenko.yamblz.screens.history;

import android.util.Log;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class HistoryPresenter extends BaseLifecyclePresenter<HistoryView, HistoryInteractor> {

    public HistoryPresenter(HistoryView view, HistoryInteractor interactor) {
        super(view, interactor);
    }

    public void onCreateView() {
        getHistory();
    }

    public void onDestroyView() {
        interactor.cleanup();
    }

    public void bookmarkTranslation(int id, boolean bookmarked) {
        interactor.setBookmarked(id, bookmarked);
    }

    public void getHistory() {
        view.displayHistory(interactor.getHistory());
    }

}
