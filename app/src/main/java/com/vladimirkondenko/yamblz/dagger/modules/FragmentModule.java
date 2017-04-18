package com.vladimirkondenko.yamblz.dagger.modules;

import android.support.v4.app.FragmentTransaction;

import com.vladimirkondenko.yamblz.screens.history.HistoryFragment;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    @Provides
    public TranslationFragment provideTranslationFragment() {
        return new TranslationFragment();
    }

    @Provides
    public HistoryFragment provideHistoryFragment() {
        return new HistoryFragment();
    }

}
