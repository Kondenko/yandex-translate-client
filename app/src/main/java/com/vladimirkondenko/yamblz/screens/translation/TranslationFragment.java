package com.vladimirkondenko.yamblz.screens.translation;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.TranslationModule;
import com.vladimirkondenko.yamblz.databinding.FragmentTranslationBinding;
import com.vladimirkondenko.yamblz.model.entities.Translation;
import com.vladimirkondenko.yamblz.utils.ErrorCodes;
import com.vladimirkondenko.yamblz.utils.LanguageUtils;
import com.vladimirkondenko.yamblz.utils.RxNetworkBroadcastReceiver;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.events.Bus;
import com.vladimirkondenko.yamblz.utils.events.InputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.LanguageDetectionEvent;
import com.vladimirkondenko.yamblz.utils.events.OutputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.SelectLanguageEvent;
import com.vladimirkondenko.yamblz.utils.events.SwapLanguageEvent;
import com.vladimirkondenko.yamblz.utils.ui.AnimUtils;
import com.vladimirkondenko.yamblz.utils.ui.RxCheckableImageButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * The main fragment which provides primary translator features.
 */
public class TranslationFragment extends Fragment implements TranslationView {

    private static final String ARG_LANG_IN = "lang_in";

    private static final String ARG_LANG_OUT = "lang_out";

    @Inject
    public RxNetworkBroadcastReceiver networkBroadcastReceiver;

    @Inject
    public TranslationPresenter presenter;

    private FragmentTranslationBinding binding;

    private CompositeDisposable disposables = new CompositeDisposable();

    private String inputLanguage;

    private String outputLanguage;

    public TranslationFragment() {
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translation, container, false);
        if (savedInstanceState != null) {
            inputLanguage = savedInstanceState.getString(ARG_LANG_IN);
            outputLanguage = savedInstanceState.getString(ARG_LANG_OUT);
        }
        App.get().plusTranslationSubcomponent(new TranslationModule(this)).inject(this);
        Bus.subscribe(this);
        presenter.attachView(this);
        Drawable bookmarkDrawable = Utils.getTintedIcon(getContext(), R.drawable.selector_all_bookmark);
        binding.includeTranslationBookmarkButton.buttonTransationBookmark.setImageDrawable(bookmarkDrawable);
        networkBroadcastReceiver.register().subscribe(isOnline -> {
            binding.includeTranslationOfflineBanner.linearlayoutTranslationOfflineBannerRoot.setVisibility(isOnline ? View.GONE : View.VISIBLE);
            if (isOnline) presenter.executePendingTranslation();
        });
        disposables.addAll(
                RxCheckableImageButton.checks(binding.includeTranslationBookmarkButton.buttonTransationBookmark)
                        .subscribe(presenter::bookmarkTranslation),
                RxView.clicks(binding.buttonTranslationClearInput)
                        .subscribe(o -> presenter.clickClearButton()),
                RxTextView.textChanges(binding.edittextTranslationInput)
                        .skipInitialValue()
                        .doOnSubscribe(d -> Log.i(this.getClass().getSimpleName(), "Subscribed to text changes"))
                        .doOnDispose(() -> Log.i(this.getClass().getSimpleName(), "Unsubscribed from text changes"))
                        .debounce(225, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .filter(text -> text.length() < Const.MAX_TEXT_LENGTH)
                        .map(String::valueOf)
                        .map(String::trim)
                        .subscribe(text -> presenter.onInputTextChange(inputLanguage, outputLanguage, text, networkBroadcastReceiver.isOnline())),
                RxTextView.editorActions(binding.edittextTranslationInput)
                        .subscribe(event -> presenter.pressEnter())
        );
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ARG_LANG_IN, inputLanguage);
        outState.putString(ARG_LANG_OUT, outputLanguage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        presenter.detachView();
        networkBroadcastReceiver.unregister();
        Bus.unsubscribe(this);
        App.get().clearTranslationPresenterComponent();
        super.onDestroyView();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwapLanguages(SwapLanguageEvent event) {
        if (!Utils.areFieldsEmpty(binding.edittextTranslationInput, binding.textviewTranslationResult)) {
            int duration = Const.ANIM_DURATION_LANG_SWITCH_SPINNER;
            int distance = 4;
            AnimUtils.slideInAndOut(
                    binding.edittextTranslationInput,
                    true,
                    distance,
                    duration,
                    () -> {
                    },
                    () -> binding.edittextTranslationInput.setText(binding.textviewTranslationResult.getText())
            );
            AnimUtils.slideInAndOut(
                    binding.textviewTranslationResult,
                    false,
                    distance,
                    duration,
                    () -> {
                    },
                    () -> binding.textviewTranslationResult.setText("")
            );
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetectLanguage(LanguageDetectionEvent event) {
        String langCode = LanguageUtils.parseDirection(event.getDetectedLang())[0];
        Locale locale = new Locale(langCode);
        String language = locale.getDisplayLanguage();
        binding.textviewDetectedLang.setText(language);
        showDetectedLangLayout(true);
        disposables.add(RxView.clicks(binding.framelayoutDetectedLang)
                .subscribe(o -> {
                    showDetectedLangLayout(false);
                    Bus.post(new SelectLanguageEvent(langCode));
                })
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInputLangChange(InputLanguageSelectionEvent event) {
        inputLanguage = event.getInputLang();
        showDetectedLangLayout(false);
        presenter.saveLastTranslation();
        translate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutputLangChange(OutputLanguageSelectionEvent event) {
        outputLanguage = event.getOutputLang();
        presenter.saveLastTranslation();
        translate();
    }

    @Override
    public void onTranslationSuccess(Translation translation) {
        binding.textviewTranslationResult.setText(translation.getFormattedTranslatedText());
        binding.includeTranslationBookmarkButton.buttonTransationBookmark.setChecked(translation.isBookmarked());
    }

    @Override
    public void onClearButtonClicked() {
        binding.edittextTranslationInput.getText().clear();
        binding.textviewTranslationResult.setText("");
        showDetectedLangLayout(false);
    }

    @Override
    public void onTextCleared() {
        showDetectedLangLayout(false);
        binding.textviewTranslationResult.setText("");
    }

    @Override
    public void onEnterKeyPressed() {
        hideKeyboard();
    }

    @Override
    public void onBookmarkingEnabled(boolean enabled) {
        binding.includeTranslationBookmarkButton.buttonTransationBookmark.setEnabled(enabled);
        if (!enabled)
            binding.includeTranslationBookmarkButton.buttonTransationBookmark.setChecked(false);
    }

    private void translate() {
        if (!Utils.isEmpty(binding.edittextTranslationInput) && presenter != null) {
            String text = String.valueOf(binding.edittextTranslationInput.getText());
            presenter.enqueueTranslation(inputLanguage, outputLanguage, text);
            if (networkBroadcastReceiver.isOnline()) presenter.executePendingTranslation();
        }
    }

    private void showDetectedLangLayout(boolean show) {
        binding.framelayoutDetectedLang.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(Throwable t, int errorCode) {
        if (t != null) {
            t.printStackTrace();
        }
        showToast(errorCode);
    }

    private void showToast(int errorCode) {
        int errorMessageResId;
        switch (errorCode) {
            case ErrorCodes.TEXT_TOO_LONG: {
                errorMessageResId = R.string.translation_error_text_too_long;
                break;
            }
            case ErrorCodes.WRONG_LANGUAGE: {
                errorMessageResId = R.string.translation_error_wrong_lang;
                break;
            }
            default: {
                errorMessageResId = R.string.all_error_generic;
                break;
            }
        }
        Toast.makeText(getContext(), errorMessageResId, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edittextTranslationInput.getWindowToken(), 0);
    }

}
