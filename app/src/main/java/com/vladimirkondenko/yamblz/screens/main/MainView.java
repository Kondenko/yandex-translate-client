package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.BaseView;
import com.vladimirkondenko.yamblz.model.entities.LanguagesHolder;

public interface MainView extends BaseView {
    void onLoadLanguages(LanguagesHolder languages);
    void onError(Throwable error);
}
