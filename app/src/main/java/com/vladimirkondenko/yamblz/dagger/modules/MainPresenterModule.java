package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;
import com.vladimirkondenko.yamblz.services.LanguagesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainPresenterModule extends BasePresenterModule<MainView> {

    public MainPresenterModule(MainView view) {
        super(view);
    }

    @Provides
    @PerView
    public LanguagesService provideAvailableLanguagesService(Retrofit retrofit) {
        return retrofit.create(LanguagesService.class);
    }

    @Provides
    @PerView
    public MainPresenter provideMainPresenter(LanguagesService service) {
        return new MainPresenter(service, view);
    }

}
