package com.vladimirkondenko.yamblz.screens.translation;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.databinding.FragmentTranslationBinding;

import javax.inject.Inject;

/**
 * The main fragment which provides primary translator features.
 */
public class TranslationFragment extends Fragment implements TranslationView {

    private final String TAG = "TranslationFragment";

    @Inject
    public TranslationPresenter presenter;

    private FragmentTranslationBinding binding;

    private String languageInput;
    private String languageTranslation;

    public TranslationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.plusTranslationSubcomponent(new TranslationPresenterModule(this)).inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);
        binding.buttonTranslationClearInput.setOnClickListener(view -> binding.edittextTranslationInput.getText().clear());
        return binding.getRoot();
    }

     @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
        App.clearTranslationPresenterComponent();
    }

    public void setInputLanguage(String lang) {
        languageInput = lang;
    }

    public void setTranslationLanguage(String lang) {
        languageTranslation = lang;
    }

    @Override
    public void onError(Throwable t) {
        if (t != null) {
            t.printStackTrace();
        }
    }
}
