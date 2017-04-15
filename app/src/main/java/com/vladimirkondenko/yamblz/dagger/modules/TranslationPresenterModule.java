package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.services.TranslationService;
import com.vladimirkondenko.yamblz.screens.translation.TranslationInteractor;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;

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
    public TranslationService provideTranslationService(Retrofit retrofit) {
        return retrofit.create(TranslationService.class);
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