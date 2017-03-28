package com.vladimirkondenko.yamblz.screens.translation;

import android.content.Context;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.screens.main.MainView;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView> {

    @Inject
    public TranslationPresenter(TranslationView view) {
        this.view = view;
    }

}
