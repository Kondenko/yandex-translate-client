package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.utils.BaseView;

public interface MainView extends BaseView {
    void onLoadLanguages(Languages languages);
    void onError(Throwable error);
}
