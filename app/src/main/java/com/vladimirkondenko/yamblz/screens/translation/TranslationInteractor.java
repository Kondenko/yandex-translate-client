package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.TranslationDatabaseService;
import com.vladimirkondenko.yamblz.model.services.TranslationService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.base.BaseInteractor;
import com.vladimirkondenko.yamblz.utils.events.Bus;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TranslationInteractor extends BaseInteractor {

    private TranslationService netService;
    private TranslationDatabaseService dbService;

    @Inject
    public TranslationInteractor(TranslationService netService, TranslationDatabaseService dbService) {
        this.netService = netService;
        this.dbService = dbService;
    }

    public Single<Translation> translate(String fromLang, String toLang, String text) {
        boolean shouldDetectLanguage = fromLang == null || fromLang.equals(Const.LANG_CODE_AUTO);
        String lang = shouldDetectLanguage ? toLang : LanguageUtils.langsToDirection(fromLang, toLang);
        return Single.defer(() -> netService.getTranslation(lang, text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(translation -> {
                    if (shouldDetectLanguage) Bus.post(new LanguageDetectionEvent(translation.getLang()));
                }));
    }

    public void saveToHistory(Translation translation) {
        translation.setTimestamp(Utils.getCurrenttimeSec());
        dbService.saveToHistory(translation);
    }

}
