package com.vladimirkondenko.yamblz.screens.history;

import android.util.Log;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.screens.ScreenCodes;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static android.R.attr.id;

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

    public void removeFromHistory(Translation translation) {
        interactor.removeFromHistory(translation);
    }

    public void getHistory() {
        view.displayHistory(interactor.getHistory());
    }

    public AdapterPresenter getAdapterPresenter() {
        return new AdapterPresenter();
    }

    public class AdapterPresenter {

        public void setBookmarked(Translation translation, boolean bookmarked) {
                interactor.setBookmarked(translation, bookmarked);
        }
    }

}
