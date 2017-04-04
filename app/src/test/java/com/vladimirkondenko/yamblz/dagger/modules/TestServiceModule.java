package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerTest;
import com.vladimirkondenko.yamblz.services.LanguagesService;

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

}
