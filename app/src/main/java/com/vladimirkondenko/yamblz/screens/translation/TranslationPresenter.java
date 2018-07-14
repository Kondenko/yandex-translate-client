package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.base.BaseLifecyclePresenter;

import javax.inject.Inject;

import io.reactivex.Single;


public class TranslationPresenter extends BaseLifecyclePresenter<TranslationView, TranslationInteractor> {

    private static final String TAG = "TranslationPresenter";

    private String inputLanguage;

    private String outputLanguage;

    private Single<Translation> translationSingle = null;

    private Translation lastTranslation;

    @Inject
    public TranslationPresenter(TranslationView view, TranslationInteractor interactor) {
        super(view, interactor);
    }

    public void onCreateView() {
        view.onBookmarkingEnabled(false);
    }

    public void onInputTextChange(String text, boolean isConnected) {
        if (text.length() == 0) {
            clearText();
        } else {
            enqueueTranslation(text);
            if (isConnected) executePendingTranslation();
        }
    }

    public void clickClearButton() {
        view.onClearButtonClicked();
        view.onBookmarkingEnabled(false);
        saveLastTranslation();
    }

    public void clearText() {
        view.onTextCleared();
        view.onBookmarkingEnabled(false);
    }

    public void pressEnter() {
        view.onEnterKeyPressed();
        saveLastTranslation();
    }

    public void bookmarkTranslation(boolean bookmarked) {
        if (lastTranslation != null) {
            interactor.setBookmarked(lastTranslation, bookmarked);
        }
    }

    public void saveLastTranslation() {
        if (lastTranslation != null) {
            interactor.saveToHistory(lastTranslation);
        }
    }

    public void enqueueTranslation(String originalText) {
        if (isValid(inputLanguage) && isValid(outputLanguage)) {
            translationSingle = interactor.translate(inputLanguage, outputLanguage, originalText)
                    .compose(bindToLifecycle());
        } else {
            view.onError(new IllegalArgumentException(String.format("Can\'t use this direction: \"%s\" \"%s\"", inputLanguage, outputLanguage)), ErrorCodes.WRONG_LANGUAGE);
        }
    }

    public void executePendingTranslation() {
        if (translationSingle != null && isViewAttached()) {
            translationSingle.subscribe(
                    translation -> {
                        view.onTranslationSuccess(translation);
                        view.onBookmarkingEnabled(true);
                        lastTranslation = translation;
                    },
                    throwable -> {
                        if (isViewAttached()) view.onError(throwable, 0);
                    }
            );
        }
    }

    public void selectInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public void selectOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    private boolean isValid(String lang) {
        return lang != null && !lang.isEmpty();
    }

}
