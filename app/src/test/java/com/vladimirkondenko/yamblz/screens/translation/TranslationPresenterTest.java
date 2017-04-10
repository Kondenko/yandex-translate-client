package com.vladimirkondenko.yamblz.screens.translation;

import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;
import com.vladimirkondenko.yamblz.TestApp;
import com.vladimirkondenko.yamblz.dagger.modules.TestTranslationPresenterModule;
import com.vladimirkondenko.yamblz.model.entities.Translation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CustomRobolectricTestRunner.class)
public class TranslationPresenterTest {

    @Mock
    public TranslationView view;

    @Inject
    public TranslationPresenter presenter;

    @Inject
    public TranslationInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        TestApp.get().plusTestTranslationPresenterSubcomponent(new TestTranslationPresenterModule(view)).inject(this);
    }

    @After
    public void tearDown() throws Exception {
        TestApp.get().clearTestTranslationPresenterSubcomponent();
    }

    @Test
    public void shouldSendResultToView() {
        String translatedString = "Привет, мир!";
        String direction = "en-ru";
        ArrayList<String> translatedText = new ArrayList<>();
        translatedText.add(translatedString);
        Translation result = new Translation(200, direction, translatedText);
        when(interactor.translate(anyString(), anyString(), anyString())).thenReturn(Single.just(result));
        verify(view).onTranslationSuccess(translatedString);
        verify(view, never()).onError(any(Throwable.class), anyInt());
    }

}