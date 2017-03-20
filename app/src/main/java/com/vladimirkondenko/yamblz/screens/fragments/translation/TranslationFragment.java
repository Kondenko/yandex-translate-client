package com.vladimirkondenko.yamblz.screens.fragments.translation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.components.AppComponent;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.databinding.FragmentTranslationBinding;

import javax.inject.Inject;

/**
 * The main fragment which provides primary translator features.
 */
public class TranslationFragment extends Fragment implements TranslationView {

    @Inject
    public TranslationPresenter presenter;

    private FragmentTranslationBinding binding;

    public TranslationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);
        // Dependency Injection
        App app = App.get();
        AppComponent appComponent = app.getAppComponent();
        app.plus(new TranslationPresenterModule(this));
        appComponent.inject(this);
        //Views
        binding.buttonTranslationClearInput.setOnClickListener(view -> binding.edittextTranslationInput.getText().clear());
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Release scoped presenter
        App.get().clearTranslationPresenterComponent();
    }
}
