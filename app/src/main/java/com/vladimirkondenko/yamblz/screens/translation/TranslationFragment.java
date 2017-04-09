package com.vladimirkondenko.yamblz.screens.translation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationPresenterModule;
import com.vladimirkondenko.yamblz.databinding.FragmentTranslationBinding;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


    private Disposable edittextTranslationInputSubscription;
    private Disposable buttonTranslationClearInputSubscription;

    public TranslationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.get().plusTranslationSubcomponent(new TranslationPresenterModule(this)).inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);

        EditText edittextTranslationInput = binding.edittextTranslationInput;
        TextView textviewTranslationResult = binding.textviewTranslationResult;

        buttonTranslationClearInputSubscription = RxView.clicks(binding.buttonTranslationClearInput)
                .subscribe(o -> {
                    edittextTranslationInput.getText().clear();
                    textviewTranslationResult.setText("");
                });

        edittextTranslationInputSubscription = RxTextView.editorActionEvents(edittextTranslationInput)
                .subscribe(event -> {
                    if (event.actionId() == KeyEvent.KEYCODE_ENTER) {
                        if (edittextTranslationInput.getText().length() == 0) {
                            textviewTranslationResult.setText("");
                        } else {
                            presenter.translate(languageInput, languageTranslation, edittextTranslationInput.getText().toString());
                        }
                    }
                });

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
        Utils.disposeAll(buttonTranslationClearInputSubscription, edittextTranslationInputSubscription);
        presenter.detachView();
        App.get().clearTranslationPresenterComponent();
    }

    public void setInputLanguage(String lang) {
        languageInput = lang;
    }

    public void setTranslationLanguage(String lang) {
        languageTranslation = lang;
    }

    @Override
    public void onTranslationSuccess(String result) {
        Log.i(TAG, "onTranslationSuccess: " + result);
        binding.textviewTranslationResult.setText(result);
    }

    @Override
    public void onError(Throwable t, int errorCode) {
        if (t != null) {
            t.printStackTrace();
        }
        displayErrorMessage(errorCode);
    }

    private void displayErrorMessage(int errorCode) {
        int errorMessageResId;
        switch (errorCode) {
            case ErrorCodes.TEXT_TOO_LONG: {
                errorMessageResId = R.string.translation_error_text_too_long;
            }
            default: {
                errorMessageResId = R.string.all_error_generic;
            }
        }
        Toast.makeText(getContext(), errorMessageResId, Toast.LENGTH_SHORT).show();
    }

}
