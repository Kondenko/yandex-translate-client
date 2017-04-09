package com.vladimirkondenko.yamblz.screens.translation;

import android.support.annotation.Nullable;

import com.vladimirkondenko.yamblz.utils.BaseView;

public interface TranslationView extends BaseView {
    void onTranslationSuccess(String result);
    void onError(@Nullable Throwable t, int errorCode);
}
