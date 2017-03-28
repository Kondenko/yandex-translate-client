package com.vladimirkondenko.yamblz.screens.main;

import android.content.Context;

import com.vladimirkondenko.yamblz.BaseLifecyclePresenter;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;

import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BaseLifecyclePresenter<MainView> {

    private MainView view;
    private AvailableLanguagesService languagesService;

    @Inject
    public MainPresenter(AvailableLanguagesService languagesService, MainView view) {
        this.view = view;
        this.languagesService = languagesService;
    }

    public void onCreate(Context context) {
        getLanguages(context);
    }

    public String getInitialTranslationLang(LanguagesHolder languagesHolder) {
        if (Const.LOCALE_RU.equalsIgnoreCase(languagesHolder.forLanguage)) return Const.LOCALE_EN;
        Set<String> languages = languagesHolder.languages.keySet();
        for (String lang : languages) {
            if (!languagesHolder.forLanguage.equals(lang)) return lang;
        }
        return Const.LOCALE_EN;
    }

    public void getLanguages(Context context) {
        LanguagesHolder preferredLangs = LanguageUtils.getInputLanguages(context);
        String deviceLocale = LanguageUtils.getDeviceLocale();
        if (preferredLangs.languages.keySet().contains(deviceLocale)) {
            getTranslationLanguages(deviceLocale);
        } else {
            view.onLoadLanguages(preferredLangs);
        }
    }

    public void getTranslationLanguages(String languageCode) {
        languagesService.getAvailableLanguages(languageCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(
                        languages -> {
                            languages.forLanguage = languageCode;
                            view.onLoadLanguages(languages);
                        },
                        error -> view.onError(error)
                );
    }

}
