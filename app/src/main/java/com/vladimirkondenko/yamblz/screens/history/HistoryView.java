package com.vladimirkondenko.yamblz.screens.history;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseView;

import io.realm.OrderedRealmCollection;

public interface HistoryView extends BaseView {
    void displayHistory(OrderedRealmCollection<Translation> translations);
}
