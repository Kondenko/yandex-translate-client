package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.RxRule;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainPresenterModule;
import com.vladimirkondenko.yamblz.model.entities.LanguagesHolder;
import com.vladimirkondenko.yamblz.model.services.LanguagesService;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;

import static com.vladimirkondenko.yamblz.Const.LOCALE_EN;
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

    @Captor
    public ArgumentCaptor<LanguagesHolder> captor = ArgumentCaptor.forClass(LanguagesHolder.class);

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
        assertEquals(presenter.getInitialTranslationLang(languagesHolder), LOCALE_EN);
    }

    @Test
    public void checkEnSetup() {
        LanguagesHolder languagesHolder = getLanguages(LOCALE_EN);
        languagesHolder.setUserLanguage(Const.LOCALE_EN);
        assertNotEquals(presenter.getInitialTranslationLang(languagesHolder), LOCALE_EN);
    }

    @Test
    public void checkError() {
        String locale = LOCALE_EN;
        LanguagesHolder languagesHolder = getLanguages(locale);
        languagesHolder.setUserLanguage("");
        assertNotEquals(presenter.getInitialTranslationLang(languagesHolder), locale);
    }

    @Test
    public void shouldFetchLanguages() {
        String locale = LOCALE_EN;
        when(service.getAvailableLanguages(anyString())).thenReturn(Single.just(getLanguages(locale)));
        presenter.getLanguages(RuntimeEnvironment.application.getBaseContext());
        verify(view).onLoadLanguages(any(LanguagesHolder.class));
    }

    @Test
    public void shouldContainLanguageDetection() {
        String locale = LOCALE_EN;
        when(service.getAvailableLanguages(anyString())).thenReturn(Single.just(getLanguages(locale)));
        presenter.getLanguages(RuntimeEnvironment.application.getBaseContext());
        verify(view).onLoadLanguages(captor.capture());
        assertTrue(captor.getValue().getLanguages().containsKey(Const.LOCALE_DETECT));
    }

    private LanguagesHolder getLanguages(String locale) {
        LanguagesHolder langs = LanguageUtils.getLangsFromRawRes(
                RuntimeEnvironment.application.getApplicationContext(),
                Const.LOCALE_RU.equals(locale) ? R.raw.input_languages_ru : R.raw.input_languages_en
        );
        langs.setUserLanguage(locale);
        return langs;
    }


}