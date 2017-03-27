package com.vladimirkondenko.yamblz.screens.main;

import android.support.v4.util.Pair;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.openjdk.tools.javac.jvm.ByteCodes.error;

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

    public void getLanguages() {
        Single<LanguagesHolder> from = getLanguagesForLocale(LanguageUtils.getDeviceLanguage());
        from.subscribe(
          languagesForDefaultLanguage -> {
              getLanguagesForLocale(languagesForDefaultLanguage.languages.keySet().iterator().next())
              .subscribe(
                      translationLanguages -> view.loadLanguages(languagesForDefaultLanguage, translationLanguages),
                      error -> view.onError(error)
              );
          },
          error -> view.onError(error)
        );
    }

    private Single<LanguagesHolder> getLanguagesForLocale(String languageCode) {
        return languagesService.getAvailableLanguages(languageCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle());
    }

}
