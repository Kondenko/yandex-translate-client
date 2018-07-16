package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.screens.ScreenCodes;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import javax.inject.Inject;

public class MainPresenter extends BaseLifecyclePresenter<MainView, MainInteractor> {

    private String inputLang;

    private String outputLang;

    @Inject
    public MainPresenter(MainView view, MainInteractor interactor) {
        super(view, interactor);
    }

    public void onPause() {
        saveLanguages();
    }

    public void selectScreen(int actionId) {
        switch (actionId) {
            case ScreenCodes.Translation.SCREEN_ID: {
                view.onSelectTranslationScreen();
                break;
            }
            case ScreenCodes.History.SCREEN_ID:
                view.onSelectHistoryScreen();
                break;
            default: {
                // Go to the translation screen by default to avoid errors
                view.onSelectTranslationScreen();
                break;
            }
        }
    }

    public void getLanguagesList() {
        interactor.getLanguages()
                .compose(bindToLifecycle())
                .subscribe(view::onLoadLanguages, view::onError);
    }

    public void getSelectedLanguages(Languages languages) {
        view.onSelectInputLang(interactor.getInputLang());
        view.onSelectOutputLang(interactor.getOutputLang(languages));
    }

    public void saveLanguages() {
        if (inputLang != null && outputLang != null) interactor.saveLangs(inputLang, outputLang);
    }

    public void setInputLang(String inputLang) {
        this.inputLang = inputLang;
    }

    public void setOutputLang(String outputLang) {
        this.outputLang = outputLang;
    }



}
