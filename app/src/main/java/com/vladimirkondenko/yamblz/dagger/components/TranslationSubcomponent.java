package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationModule;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {TranslationModule.class})
public interface TranslationSubcomponent extends BaseSubcomponent<TranslationFragment, TranslationPresenter> {
    @Override
    void inject(TranslationFragment view);
}
