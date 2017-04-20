package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.model.database.Database;
import com.vladimirkondenko.yamblz.model.database.DbHistoryServiceImpl;
import com.vladimirkondenko.yamblz.model.services.DbTranslationService;
import com.vladimirkondenko.yamblz.screens.history.HistoryInteractor;
import com.vladimirkondenko.yamblz.screens.history.HistoryPresenter;
import com.vladimirkondenko.yamblz.screens.history.HistoryView;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryModule extends BaseModule<HistoryView> {

    public HistoryModule(HistoryView view) {
        super(view);
    }

    @Provides
    @PerView
    public DbTranslationService provideDatabaseService(Database database) {
        return new DbHistoryServiceImpl(database);
    }

    @Provides
    @PerView
    public HistoryInteractor provideHistoryInteractor(DbTranslationService service) {
        return new HistoryInteractor(service);
    }

    @Provides
    @PerView
    public HistoryPresenter provideHistoryPresenter(HistoryInteractor interactor) {
        return new HistoryPresenter(view, interactor);
    }

}
