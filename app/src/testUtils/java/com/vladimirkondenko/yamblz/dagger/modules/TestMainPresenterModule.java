package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.screens.main.MainPresenter;
import com.vladimirkondenko.yamblz.screens.main.MainView;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestMainPresenterModule extends BasePresenterModule<MainView> {

    public TestMainPresenterModule(MainView view) {
        super(view);
    }

    @Provides
    @PerView
    public LanguagesService provideAvailableLanguagesService() {
        return Mockito.mock(LanguagesService.class);
    }

    @Provides
    @PerView
    public MainPresenter provideMainPresenter(LanguagesService service) {
        return new MainPresenter(service, view);
    }

}
