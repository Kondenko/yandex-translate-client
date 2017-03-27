package com.vladimirkondenko.yamblz.screens.main;

import android.content.Context;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BaseLifecyclePresenter<MainView> {

    private MainView view;
    private AvailableLanguagesService languagesService;

    @Inject
    public MainPresenter(AvailableLanguagesService service, MainView view) {
        this.languagesService = service;
        this.view = view;
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    @Override
    public void detachView() {
        super.detachView();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }

    public void getInputLanguages(Context context) {
        LanguagesHolder inputLanguages = LanguageUtils.getInputLanguages(context);
        view.onLoadInputLanguages(inputLanguages);
    }

    public void getTranslationLanguages(String languageCode) {
        languagesService.getAvailableLanguages(languageCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(
                        languages -> view.onLoadTranslationLanguages(languages),
                        error -> view.onError(error)
                );
    }


}
