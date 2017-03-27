package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.BaseView;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;

public interface MainView extends BaseView {
//    void loadLanguages(LanguagesHolder inputLangs, LanguagesHolder translationLangs);
    void onLoadInputLanguages(LanguagesHolder languages);
    void onLoadTranslationLanguages(LanguagesHolder languages);
    void onError(Throwable error);
}
