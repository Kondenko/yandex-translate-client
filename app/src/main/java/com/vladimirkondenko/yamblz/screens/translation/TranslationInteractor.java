package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.model.services.DbTranslationService;
import com.vladimirkondenko.yamblz.model.services.NetTranslationService;
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

    private NetTranslationService netService;
    private DbTranslationService dbService;

    @Inject
    public TranslationInteractor(NetTranslationService netService, DbTranslationService dbService) {
        this.netService = netService;
        this.dbService = dbService;
    }

    public Single<Translation> translate(String fromLang, String toLang, String inputText) {
        boolean shouldDetectLanguage = fromLang == null || fromLang.equals(Const.LANG_CODE_AUTO);
        String lang = shouldDetectLanguage ? toLang : LanguageUtils.langsToDirection(fromLang, toLang);
        return Single.defer(() -> netService.getTranslation(lang, inputText.trim())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(translation -> {
                    translation.setInputText(inputText);
                    if (shouldDetectLanguage) Bus.post(new LanguageDetectionEvent(translation.getDirection()));
                }));
    }

    public void saveToHistory(Translation translation) {
        long timestamp = Utils.getCurrentTimeSec();
        dbService.setSavedToHistory(translation, true, timestamp);
    }

}
