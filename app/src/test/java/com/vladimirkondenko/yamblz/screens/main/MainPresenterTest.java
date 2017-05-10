package com.vladimirkondenko.yamblz.screens.main;

import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.RxRule;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestMainModule;
import com.vladimirkondenko.yamblz.model.entities.Languages;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CustomRobolectricTestRunner.class)
public class MainPresenterTest {

    @Rule
    public RxRule rxRule = new RxRule();

    @Captor
    public ArgumentCaptor<Languages> captor = ArgumentCaptor.forClass(Languages.class);

    @Mock
    private MainView view;

    @Inject
    public MainPresenter presenter;

    @Inject
    public MainInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TestApp.get().plusTestMainPresenterSubcomponent(new TestMainModule(view)).inject(this);
    }

    @After
    public void tearDown() throws IOException {
        TestApp.get().clearTestMainPresenterSubcomponent();
    }

    @Test
    public void checkViewIsNotNull() {
        assertTrue(presenter.isViewAttached());
    }

    @Test
    public void checkRuEnSetup() {
        Languages languages = getLanguages(Const.LANG_CODE_RU);
//        assertEquals(presenter.getInitialOutputLang(languages), Const.LANG_CODE_EN);
    }

    @Test
    public void checkEnSetup() {
        Languages languages = getLanguages(Const.LANG_CODE_EN);
        languages.setUserLanguageCode(Const.LANG_CODE_EN);
//        assertNotEquals(presenter.getInitialOutputLang(languages), Const.LANG_CODE_EN);
    }

    @Test
    public void checkError() {
        String locale = Const.LANG_CODE_EN;
        Languages languages = getLanguages(locale);
        languages.setUserLanguageCode("");
//        assertEquals(presenter.getInitialOutputLang(languages), locale);
    }

    @Test
    public void shouldFetchLanguages() {
        String locale = Const.LANG_CODE_EN;
        when(interactor.getLanguages()).thenReturn(Single.just(getLanguages(locale)));
        presenter.getLanguagesList();
        verify(view).onLoadLanguages(any(Languages.class));
    }

    private Languages getLanguages(String locale) {
        Languages langs = LanguageUtils.getLangsFromRawRes(
                RuntimeEnvironment.application.getApplicationContext(),
                Const.LANG_CODE_RU.equals(locale) ? R.raw.input_languages_ru : R.raw.input_languages_en
        );
        langs.setUserLanguageCode(locale);
        return langs;
    }


}