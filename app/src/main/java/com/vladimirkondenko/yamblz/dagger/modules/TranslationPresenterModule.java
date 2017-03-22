package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerFragment;
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
    @PerFragment
    public TranslationPresenter provideTranslationPresenter(AvailableLanguagesService service) {
        return new TranslationPresenter(service, view);
    }

    @Provides
    @PerFragment
    public AvailableLanguagesService provideAvailableLanguagesService(Retrofit retrofit) {
        return retrofit.create(AvailableLanguagesService.class);
    }

}
