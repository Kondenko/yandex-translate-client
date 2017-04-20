package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.HistoryModule;
import com.vladimirkondenko.yamblz.screens.history.HistoryFragment;
import com.vladimirkondenko.yamblz.screens.history.HistoryPresenter;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = HistoryModule.class)
public interface HistorySubcomponent extends BaseSubcomponent<HistoryFragment, HistoryPresenter> {
    @Override
    void inject(HistoryFragment view);
}
