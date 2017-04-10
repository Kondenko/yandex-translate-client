package com.vladimirkondenko.yamblz.screens.translation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.events.Bus;
import com.vladimirkondenko.yamblz.utils.events.InputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;
import com.vladimirkondenko.yamblz.utils.events.OutputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.SelectLanguageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

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

    private Disposable subscriptionClearButton;
    private Disposable subscriptionSelectDetectedLang;
    private Disposable subscriptionInputText;

    public TranslationFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        App.get().plusTranslationSubcomponent(new TranslationPresenterModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);

        EditText edittextTranslationInput = binding.edittextTranslationInput;
        TextView textviewTranslationResult = binding.textviewTranslationResult;

        subscriptionClearButton = RxView.clicks(binding.buttonTranslationClearInput)
                .subscribe(o -> {
                    showDetectedLangLayout(false);
                    edittextTranslationInput.getText().clear();
                    textviewTranslationResult.setText("");
                });

        subscriptionInputText = RxTextView.textChanges(edittextTranslationInput)
                .subscribe(charSequence -> {
                    if (charSequence.length() == 0) {
                        showDetectedLangLayout(false);
                        textviewTranslationResult.setText("");
                    } else {
                        presenter.translate(charSequence.toString());
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        Bus.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Bus.unsubscribe(this);
        Utils.disposeAll(subscriptionClearButton, subscriptionInputText, subscriptionSelectDetectedLang);
        presenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        App.get().clearTranslationPresenterComponent();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetectLanguageEvent(LanguageDetectionEvent event) {
        String langCode = LanguageUtils.parseDirection(event.getDetectedLang())[0];
        Locale locale = new Locale(langCode);
        String language = locale.getDisplayLanguage();
        binding.textviewDetectedLang.setText(language);
        showDetectedLangLayout(true);
        subscriptionSelectDetectedLang = RxView.clicks(binding.framelayoutDetectedLang)
                .subscribe(o -> {
                    showDetectedLangLayout(false);
                    Bus.post(new SelectLanguageEvent(langCode));
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void inputLanguageChanged(InputLanguageSelectionEvent event) {
        showDetectedLangLayout(false);
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

    private void showDetectedLangLayout(boolean show) {
        binding.framelayoutDetectedLang.setVisibility(show ? View.VISIBLE : View.GONE);
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
