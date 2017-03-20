package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerFragment;
import com.vladimirkondenko.yamblz.screens.fragments.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.fragments.translation.TranslationView;

import dagger.Module;
import dagger.Provides;

@Module
public class TranslationPresenterModule extends BasePresenterModule {

    public TranslationPresenterModule(TranslationView view) {
        this.view = view;
    }

    @Provides
    @PerFragment
    public TranslationPresenter provideTranslationPresenter() {
        return new TranslationPresenter((TranslationView) view);
    }

}
