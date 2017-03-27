package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;

import javax.inject.Inject;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView> {

    @Inject
    public TranslationPresenter(TranslationView view) {
//        this.service = service;
        attachView(view);
    }

    @Override
    public void attachView(TranslationView view) {
        super.attachView(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    @Override
    public void detachView() {
        super.detachView();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }


}
