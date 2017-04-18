package com.vladimirkondenko.yamblz.dagger.modules;

import android.content.Context;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.database.LanguagesServiceImpl;
import com.vladimirkondenko.yamblz.model.services.LanguagesDatabaseService;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.screens.main.MainInteractor;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

@Module
public class MainPresenterModule extends BasePresenterModule<MainView> {

    public MainPresenterModule(MainView view) {
        super(view);
    }

    @Provides
    @PerView
    public LanguagesService provideLanguagesService(Retrofit retrofit) {
        return retrofit.create(LanguagesService.class);
    }

    @Provides
    @PerView
    public LanguagesDatabaseService provideLanguagesDbService(Realm realm) {
        return new LanguagesServiceImpl(realm);
    }

    @Provides
    @PerView
    public MainInteractor provideMainInteractor(Context context, LanguagesService service, LanguagesDatabaseService dbService) {
        return new MainInteractor(context, service, dbService);
    }

    @Provides
    @PerView
    public MainPresenter provideMainPresenter(MainInteractor interactor) {
        return new MainPresenter(view, interactor);
    }

}
