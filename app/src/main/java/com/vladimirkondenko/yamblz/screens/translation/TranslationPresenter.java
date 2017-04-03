package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;

import javax.inject.Inject;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView> {

    @Inject
    public TranslationPresenter(TranslationView view) {
        this.view = view;
    }

}
