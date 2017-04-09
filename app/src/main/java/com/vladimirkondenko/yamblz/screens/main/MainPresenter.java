package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.utils.BaseLifecyclePresenter;

import javax.inject.Inject;

public class MainPresenter extends BaseLifecyclePresenter<MainView, MainInteractor> {

    @Inject
    public MainPresenter(MainView view, MainInteractor interactor) {
        super(view, interactor);
    }

    public void onCreate() {
        getLanguages();
    }

    public String getInitialTranslationLang(Languages languages) {
        switch (languages.getUserLanguageCode()) {
            case Const.LANG_CODE_EN: {
                return languages.getLanguages().keySet().iterator().next();
            }
            default: return Const.LANG_CODE_EN;
        }
    }

    public void getLanguages() {
        interactor.getLanguages()
                .compose(bindToLifecycle())
                .subscribe(view::onLoadLanguages, view::onError);
    }

}
