package com.vladimirkondenko.yamblz.dagger.components;


import com.vladimirkondenko.yamblz.dagger.PerTest;
import com.vladimirkondenko.yamblz.dagger.modules.TestServiceModule;
import com.vladimirkondenko.yamblz.service.LanguagesServiceTest;

import dagger.Subcomponent;

@PerTest
@Subcomponent(modules = {TestServiceModule.class})
    public interface TestServiceSubcomponent {
    void inject(LanguagesServiceTest test);
}
