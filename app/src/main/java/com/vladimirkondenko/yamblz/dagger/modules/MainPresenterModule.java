package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;
import com.vladimirkondenko.yamblz.service.AvailableLanguagesService;

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
    public MainPresenter provideMainPresenter(AvailableLanguagesService service) {
        return new MainPresenter(service, view);
    }

    @Provides
    @PerView
    public AvailableLanguagesService provideAvailableLanguagesService(Retrofit retrofit) {
        return retrofit.create(AvailableLanguagesService.class);
    }

}
