package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;

import dagger.Module;
import dagger.Provides;

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
