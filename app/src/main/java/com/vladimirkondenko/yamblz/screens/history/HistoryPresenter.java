package com.vladimirkondenko.yamblz.screens.history;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

public class HistoryPresenter extends BaseLifecyclePresenter<HistoryView, HistoryInteractor> {

    private int selectedTab = 0;

    public HistoryPresenter(HistoryView view, HistoryInteractor interactor) {
        super(view, interactor);
    }

    public void remove(Translation translation) {
        if (selectedTab == TabCodes.Bookmarks.SCREEN_ID) {
            interactor.setBookmarked(translation, false);
        } else {
            interactor.removeFromHistory(translation);
        }
    }

    void selectTab(int tab) {
        switch (tab) {
            case TabCodes.History.SCREEN_ID: {
                getHistory();
                selectedTab = TabCodes.History.SCREEN_ID;
                view.onHistorySelected();
                break;
            }
            case TabCodes.Bookmarks.SCREEN_ID: {
                getBookmarks();
                selectedTab = TabCodes.Bookmarks.SCREEN_ID;
                view.onBookmarksSelected();
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong tab id");
            }
        }
    }

    public void getHistory() {
        view.displayList(interactor.getHistory());
    }

    public void getBookmarks() {
        view.displayList(interactor.getBookmarks());
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
