package com.vladimirkondenko.yamblz.dagger.modules;

import android.content.Context;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.screens.main.MainInteractor;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;

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
    public MainInteractor provideMainInteractor(Context context, LanguagesService service) {
        return new MainInteractor(context, service);
    }

    @Provides
    @PerView
    public MainPresenter provideMainPresenter(MainInteractor interactor) {
        return new MainPresenter(view, interactor);
    }

}
