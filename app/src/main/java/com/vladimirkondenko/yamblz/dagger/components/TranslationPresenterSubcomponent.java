package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.dagger.PerFragment;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.screens.fragments.translation.TranslationPresenter;
import com.vladimirkondenko.yamblz.screens.fragments.translation.TranslationView;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = {TranslationPresenterModule.class})
public interface TranslationPresenterSubcomponent {

    void inject(TranslationView view);

/*
    @Subcomponent.Builder
    interface Builder {
        Builder presenterModule(TranslationPresenterModule module);
        TranslationPresenterSubcomponent build();
    }
*/

}
