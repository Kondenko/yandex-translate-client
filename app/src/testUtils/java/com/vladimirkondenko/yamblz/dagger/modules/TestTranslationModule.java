package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.database.Database;
import com.vladimirkondenko.yamblz.model.database.DbSavedTranslationsServiceImpl;
import com.vladimirkondenko.yamblz.model.services.DbSavedTranslationsService;
import com.vladimirkondenko.yamblz.model.services.NetTranslationService;
import com.vladimirkondenko.yamblz.screens.translation.TranslationInteractor;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.translation.TranslationView;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestTranslationModule extends BaseModule<TranslationView> {

    public TestTranslationModule(TranslationView view) {
        super(view);
    }

    @Provides
    @PerView
    public NetTranslationService provideTranslationService() {
        return Mockito.mock(NetTranslationService.class);
    }

    @Provides
    @PerView
    public DbSavedTranslationsService provideSavedTranslationsService(Database database) {
        return new DbSavedTranslationsServiceImpl(database);
    }

    @Provides
    @PerView
    public TranslationInteractor provideTranslationInteractor(NetTranslationService netService, DbSavedTranslationsService dbService) {
        return new TranslationInteractor(netService, dbService);
    }


    @Provides
    @PerView
    public TranslationPresenter provideTranslationPresenter(TranslationInteractor interactor) {
        return new TranslationPresenter(view, interactor);
    }


}
