package com.vladimirkondenko.yamblz.screens.translation;

import android.support.annotation.Nullable;

import com.vladimirkondenko.yamblz.utils.base.BaseView;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;

public interface TranslationView extends BaseView {
    void onDetectLanguageEvent(LanguageDetectionEvent event);
    void onTranslationSuccess(String result);
    void onError(@Nullable Throwable t, int errorCode);
}
