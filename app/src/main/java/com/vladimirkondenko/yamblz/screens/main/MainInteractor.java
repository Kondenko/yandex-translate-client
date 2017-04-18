package com.vladimirkondenko.yamblz.screens.main;


import android.content.Context;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.model.services.LanguagesDatabaseService;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.base.BaseInteractor;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainInteractor extends BaseInteractor {

    private Context context;
    private LanguagesService service;
    private LanguagesDatabaseService dbService;

    @Inject
    public MainInteractor(Context context, LanguagesService service, LanguagesDatabaseService dbService) {
        this.context = context;
        this.service = service;
        this.dbService = dbService;
    }

    public String getInputLang() {
        return (dbService.areLangsSaved()) ? dbService.getSelectedLangs().getInputLang() : Const.LANG_CODE_AUTO;
    }

    public String getOutputLang(Languages languages) {
        if (dbService.areLangsSaved()) {
            return dbService.getSelectedLangs().getOutputLang();
        } else {
            switch (languages.getUserLanguageCode()) {
                case Const.LANG_CODE_EN: {
                    return languages.getLanguages().keySet().iterator().next();
                }
                default:
                    return Const.LANG_CODE_EN;
            }
        }
    }

    public void saveLangs(String inputLang, String outputLang) {
        dbService.saveLangs(inputLang, outputLang);
    }

    public Single<Languages> getLanguages() {
        Single<Languages> languagesSingle;
        Languages preferredLangs = LanguageUtils.getInputLanguages(context);
        String deviceLocale = LanguageUtils.getDeviceLocale();
        if (preferredLangs.getLanguages().keySet().contains(deviceLocale)) {
            languagesSingle = service.getAvailableLanguages(deviceLocale);
        } else {
            languagesSingle = Single.just(preferredLangs);
        }
        return languagesSingle
                .subscribeOn(Schedulers.newThread())
                .doOnSuccess(languages -> {
                    LinkedHashMap<String, String> sortedLanguages = Utils.sortByValues(languages.getLanguages());
                    languages.setLanguages(sortedLanguages);
                    languages.setUserLanguageCode(deviceLocale);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

}
