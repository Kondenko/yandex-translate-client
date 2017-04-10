package com.vladimirkondenko.yamblz.screens.translation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.vladimirkondenko.yamblz.utils.Bus;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.events.InputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;
import com.vladimirkondenko.yamblz.utils.events.OutputLanguageSelectionEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * The main fragment which provides primary translator features.
 */
public class TranslationFragment extends Fragment implements TranslationView {

    private final String TAG = "TranslationFragment";

    private static final int TYPING_DELAY_SEC = 1;

    @Inject
    public TranslationPresenter presenter;

    private FragmentTranslationBinding binding;

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

        edittextTranslationInputSubscription = RxTextView.textChanges(edittextTranslationInput)
                .subscribe(charSequence -> {
                        if (charSequence.length() == 0) {
                            textviewTranslationResult.setText("");
                        } else {
                            presenter.translate(charSequence.toString());
                        }
                });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        Bus.subscribe(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Bus.unsubscribe(this);
        Utils.disposeAll(buttonTranslationClearInputSubscription, edittextTranslationInputSubscription);
        presenter.detachView();
        App.get().clearTranslationPresenterComponent();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetectLanguageEvent(LanguageDetectionEvent event) {
        String lang = LanguageUtils.parseDirection(event.getDetectedLang())[0];
        Snackbar.make(binding.framelayoutTranslationResult, "Language detected: " + lang, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void inputLanguageChanged(InputLanguageSelectionEvent event) {
        presenter.setInputLanguage(event.getInputLang());
        presenter.translate(getTextToTranslate());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void translationLanguageChanged(OutputLanguageSelectionEvent event) {
        presenter.setOutputLanguage(event.getOutputLang());
        presenter.translate(getTextToTranslate());
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

    private String getTextToTranslate() {
        return binding.edittextTranslationInput.getText().toString();
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
