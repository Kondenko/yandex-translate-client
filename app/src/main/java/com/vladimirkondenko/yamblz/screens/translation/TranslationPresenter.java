package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import javax.inject.Inject;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView, TranslationInteractor> {

    private String inputLanguage;
    private String outputLanguage;

    @Inject
    public TranslationPresenter(TranslationView view, TranslationInteractor interactor) {
        super(view, interactor);
    }

    public void translate(String text) {
        if (text.length() != 0 && inputLanguage != null && outputLanguage != null) {
            if (text.length() == Const.MAX_TEXT_LENGTH) {
                view.onError(null, ErrorCodes.TEXT_TOO_LONG);
            } else {
                interactor.translate(inputLanguage, outputLanguage, text)
                        .compose(bindToLifecycle())
                        .subscribe(
                                translation -> view.onTranslationSuccess(translation.getText().get(0)),
                                throwable -> view.onError(throwable, 0)
                        );
            }
        }
    }

    public void setInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public void setOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

}
