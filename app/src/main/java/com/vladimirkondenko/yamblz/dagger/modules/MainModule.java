package com.vladimirkondenko.yamblz.dagger.modules;

import android.content.Context;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.database.Database;
import com.vladimirkondenko.yamblz.model.database.LanguagesServiceImpl;
import com.vladimirkondenko.yamblz.model.services.LanguagesDatabaseService;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.screens.main.MainInteractor;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule extends BaseModule<MainView> {

    public MainModule(MainView view) {
        super(view);
    }

    @Provides
    @PerView
    public LanguagesService provideLanguagesService(Retrofit retrofit) {
        return retrofit.create(LanguagesService.class);
    }

    @Provides
    @PerView
    public LanguagesDatabaseService provideLanguagesDbService(Database database) {
        return new LanguagesServiceImpl(database);
    }

    @Provides
    @PerView
    public MainInteractor provideMainInteractor(Context context, LanguagesService netService, LanguagesDatabaseService dbService) {
        return new MainInteractor(context, netService, dbService);
    }

    @Provides
    @PerView
    public MainPresenter provideMainPresenter(MainInteractor interactor) {
        return new MainPresenter(view, interactor);
    }

}
