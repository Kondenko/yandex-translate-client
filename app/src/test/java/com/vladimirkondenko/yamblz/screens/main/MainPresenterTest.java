package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.CustomRobolectricTestRunner;
import com.vladimirkondenko.RxRule;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.services.LanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CustomRobolectricTestRunner.class)
public class MainPresenterTest {

    @Rule
    public RxRule rxRule = new RxRule();

    @Mock
    private MainView view;

    @Inject
    public MainPresenter presenter;

    @Inject
    public LanguagesService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestApp.plusTestMainPresenterSubcomponent(new TestMainPresenterModule(view)).inject(this);
    }

    @After
    public void tearDown() throws IOException {
        TestApp.clearTestMainPresenterSubcomponent();
    }

    @Test
    public void checkViewIsNotNull() {
        assertTrue(presenter.isViewAttached());
    }

    @Test
    public void checkRuEnSetup() {
        LanguagesHolder languagesHolder = getLanguages(Const.LOCALE_RU);
        assertEquals(presenter.getInitialTranslationLang(languagesHolder), Const.LOCALE_EN);
    }

    @Test
    public void checkEnSetup() {
        LanguagesHolder languagesHolder = getLanguages(Const.LOCALE_EN);
        languagesHolder.forLanguage = Const.LOCALE_EN;
        assertNotEquals(presenter.getInitialTranslationLang(languagesHolder), Const.LOCALE_EN);
    }

    @Test
    public void checkError() {
        String locale = Const.LOCALE_EN;
        LanguagesHolder languagesHolder = getLanguages(locale);
        languagesHolder.forLanguage = "";
        assertNotEquals(presenter.getInitialTranslationLang(languagesHolder), locale);
    }

    @Test
    public void shouldFetchLanguages() {
        String locale = Const.LOCALE_EN;
        when(service.getAvailableLanguages(anyString())).thenReturn(Single.just(getLanguages(locale)));
        presenter.getLanguageForLocale(locale);
        verify(view).onLoadLanguages(any(LanguagesHolder.class));
    }

    private LanguagesHolder getLanguages(String locale) {
        LanguagesHolder langs = LanguageUtils.getLangsFromRawRes(
                RuntimeEnvironment.application.getApplicationContext(),
                Const.LOCALE_RU.equals(locale) ? R.raw.input_languages_ru : R.raw.input_languages_en
        );
        langs.forLanguage = locale;
        return langs;
    }


}