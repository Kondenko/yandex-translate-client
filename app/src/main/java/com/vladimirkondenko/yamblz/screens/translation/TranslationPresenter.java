package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.utils.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;

import javax.inject.Inject;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView, TranslationInteractor> {

    @Inject
    public TranslationPresenter(TranslationView view, TranslationInteractor interactor) {
        super(view, interactor);
    }

    public void translate(String langFrom, String langTo, String text) {
        if (text.length() == Const.MAX_TEXT_LENGTH) {
            view.onError(null, ErrorCodes.TEXT_TOO_LONG);
        } else {
            interactor.translate(langFrom, langTo, text)
                    .compose(bindToLifecycle())
                    .subscribe(
                            translation -> view.onTranslationSuccess(translation.getText().get(0)),
                            throwable -> view.onError(throwable, 0)
                    );
        }
    }

}
