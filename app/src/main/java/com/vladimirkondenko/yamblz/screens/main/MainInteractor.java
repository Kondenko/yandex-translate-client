package com.vladimirkondenko.yamblz.screens.main;


import android.content.Context;

import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.utils.BaseInteractor;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.Utils;

import java.util.LinkedHashMap;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainInteractor extends BaseInteractor {

    private Context context;

    private LanguagesService service;

    public MainInteractor(Context context, LanguagesService service) {
        this.context = context;
        this.service = service;
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
