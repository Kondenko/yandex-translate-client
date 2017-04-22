package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerTest;
import com.vladimirkondenko.yamblz.model.services.NetLanguagesService;
import com.vladimirkondenko.yamblz.model.services.NetTranslationService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TestServiceModule {

    @Provides
    @PerTest
    public NetLanguagesService provideAvailableLanguagesService(Retrofit retrofit) {
        return retrofit.create(NetLanguagesService.class);
    }

    @Provides
    @PerTest
    public NetTranslationService provideTranslationService(Retrofit retrofit) {
        return retrofit.create(NetTranslationService.class);
    }

}
