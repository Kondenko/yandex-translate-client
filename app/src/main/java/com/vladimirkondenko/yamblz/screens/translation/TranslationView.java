package com.vladimirkondenko.yamblz.screens.translation;

import android.support.annotation.Nullable;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.base.BaseView;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;
import com.vladimirkondenko.yamblz.utils.events.SwapLanguageEvent;

public interface TranslationView extends BaseView {
    void onSwapLanguages(SwapLanguageEvent event);
    void onDetectLanguage(LanguageDetectionEvent event);
    void onClearButtonClicked();
    void onTextCleared();
    void onEnterKeyPressed();
    void onBookmarkingEnabled(boolean enabled);
    void onTranslationSuccess(Translation translation);
    void onError(@Nullable Throwable t, int errorCode);
}
