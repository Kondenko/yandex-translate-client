package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.BaseView;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;

public interface TranslationView extends BaseView {
    void onError(Throwable t);
}
