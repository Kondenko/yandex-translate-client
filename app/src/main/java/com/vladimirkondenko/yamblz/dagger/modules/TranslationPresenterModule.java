package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TranslationPresenterModule extends BasePresenterModule<TranslationView> {

    public TranslationPresenterModule(TranslationView view) {
        super(view);
    }


    @Provides
    @PerView
    public TranslationPresenter provideTranslationPresenter() {
        return new TranslationPresenter(view);
    }

}
