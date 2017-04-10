package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.TranslationService;
import com.vladimirkondenko.yamblz.utils.BaseInteractor;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.events.Bus;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TranslationInteractor extends BaseInteractor {

    private TranslationService service;

    @Inject
    public TranslationInteractor(TranslationService service) {
        this.service = service;
    }

    public Single<Translation> translate(String fromLang, String toLang, String text) {
        boolean shouldDetectLanguage = fromLang.equals(Const.LANG_CODE_AUTO);
        String lang = shouldDetectLanguage ? toLang : LanguageUtils.langsToDirection(fromLang, toLang);
        return service.getTranslation(lang, text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(translation -> {
                    if (shouldDetectLanguage) Bus.post(new LanguageDetectionEvent(translation.getLang()));
                });
    }

}
