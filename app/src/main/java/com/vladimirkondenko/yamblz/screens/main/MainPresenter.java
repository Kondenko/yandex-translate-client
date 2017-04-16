package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import javax.inject.Inject;

public class MainPresenter extends BaseLifecyclePresenter<MainView, MainInteractor> {

    private String inputLang;
    private String outputLang;

    @Inject
    public MainPresenter(MainView view, MainInteractor interactor) {
        super(view, interactor);
    }

    public void onResume() {
        getLanguagesList();
    }

    public void onPause() {
        saveLanguages();
    }

    public void setupSelection(Languages languages) {
        view.onSelectInputLang(interactor.getInputLang());
        view.onSelectOutputLang(interactor.getOutputLang(languages));
    }

    public void saveLanguages() {
        if (inputLang != null && outputLang != null) interactor.saveLangs(inputLang, outputLang);
    }

    public void getLanguagesList() {
        interactor.getLanguages()
                .compose(bindToLifecycle())
                .subscribe(view::onLoadLanguages, view::onError);
    }

    public void setInputLang(String inputLang) {
        this.inputLang = inputLang;
    }

    public void setOutputLang(String outputLang) {
        this.outputLang = outputLang;
    }



}
