package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerView;
import com.vladimirkondenko.yamblz.dagger.modules.TestTranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.translation.TranslationPresenterTest;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = {TestTranslationPresenterModule.class})
public interface TestTranslationPresenterSubcomponent {
    void inject(TranslationPresenterTest view);
}
