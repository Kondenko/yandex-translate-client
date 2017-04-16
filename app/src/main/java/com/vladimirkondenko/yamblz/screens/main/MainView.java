package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.utils.base.BaseView;

public interface MainView extends BaseView {
    void onSelectInputLang(String lang);
    void onSelectOutputLang(String lang);
    void onLoadLanguages(Languages languages);
    void onError(Throwable error);
}
