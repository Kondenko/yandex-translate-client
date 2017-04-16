package com.vladimirkondenko.yamblz.screens.translation;

import android.support.annotation.Nullable;

import com.vladimirkondenko.yamblz.utils.base.BaseView;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;
import com.vladimirkondenko.yamblz.utils.events.SwapLanguageEvent;

public interface TranslationView extends BaseView {
    void onSwapLanguages(SwapLanguageEvent event);
    void onDetectLanguage(LanguageDetectionEvent event);
    void onTranslationSuccess(String result);
    void onError(@Nullable Throwable t, int errorCode);
}
