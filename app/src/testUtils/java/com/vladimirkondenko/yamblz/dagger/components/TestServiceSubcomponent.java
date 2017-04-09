package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.PerTest;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.model.services.LanguagesServiceTest;
import com.vladimirkondenko.yamblz.model.services.TranslationServiceTest;

import dagger.Subcomponent;

@PerTest
@Subcomponent(modules = {TestServiceModule.class})
public interface TestServiceSubcomponent {
    void inject(LanguagesServiceTest test);
    void inject(TranslationServiceTest test);
}
