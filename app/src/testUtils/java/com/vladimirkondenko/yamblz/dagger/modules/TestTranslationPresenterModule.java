package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.services.TranslationService;
import com.vladimirkondenko.yamblz.screens.translation.TranslationInteractor;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestTranslationPresenterModule extends BasePresenterModule<TranslationView> {

    public TestTranslationPresenterModule(TranslationView view) {
        super(view);
    }

    @Provides
    @PerView
    public TranslationService provideTranslationService() {
        return Mockito.mock(TranslationService.class);
    }

    @Provides
    @PerView
    public TranslationInteractor provideTranslationInteractor(TranslationService service) {
        return new TranslationInteractor(service);
    }


    @Provides
    @PerView
    public TranslationPresenter provideTranslationPresenter(TranslationInteractor interactor) {
        return new TranslationPresenter(view, interactor);
    }


}
