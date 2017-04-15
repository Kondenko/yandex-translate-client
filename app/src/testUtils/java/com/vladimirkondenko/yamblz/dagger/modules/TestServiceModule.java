package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerTest;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.model.services.TranslationService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TestServiceModule {

    @Provides
    @PerTest
    public LanguagesService provideAvailableLanguagesService(Retrofit retrofit) {
        return retrofit.create(LanguagesService.class);
    }

    @Provides
    @PerTest
    public TranslationService provideTranslationService(Retrofit retrofit) {
        return retrofit.create(TranslationService.class);
    }

}
