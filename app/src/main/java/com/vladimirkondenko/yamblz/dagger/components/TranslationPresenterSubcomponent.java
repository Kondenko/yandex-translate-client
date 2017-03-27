package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenter;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {TranslationPresenterModule.class})
public interface TranslationPresenterSubcomponent extends BasePresenterSubcomponent<TranslationFragment, TranslationPresenter> {
    @Override
    public void inject(TranslationFragment view);
}
