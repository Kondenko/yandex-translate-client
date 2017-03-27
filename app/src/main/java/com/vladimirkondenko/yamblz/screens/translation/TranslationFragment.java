package com.vladimirkondenko.yamblz.screens.translation;


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

    public TranslationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Dependency Injection
        App.plus(new TranslationPresenterModule(this)).inject(this);
        // Data Binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);
        binding.buttonTranslationClearInput.setOnClickListener(view -> binding.edittextTranslationInput.getText().clear());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Release scoped presenter
        presenter.detachView();
        App.clearTranslationPresenterComponent();
    }

    @Override
    public void showError(Throwable t) {
        if (t != null) {
            t.printStackTrace();
        }
    }
}
