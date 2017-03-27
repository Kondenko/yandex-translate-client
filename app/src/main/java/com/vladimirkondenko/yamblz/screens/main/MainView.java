package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.BaseView;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;

public interface MainView extends BaseView {
    void loadLanguages(LanguagesHolder inputLangs, LanguagesHolder translationLangs);
//    void loadInputLanguages(LanguagesHolder languages);
//    void loadTranslationLanguages(LanguagesHolder languages);
    void onError(Throwable error);
}
