package com.vladimirkondenko.yamblz.screens.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.databinding.ActivityMainBinding;
import com.vladimirkondenko.yamblz.databinding.LayoutTranslationToolbarBinding;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.utils.LanguageSpinnerAdapter;
import com.vladimirkondenko.yamblz.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    @Inject
    public MainPresenter presenter;

    private ActivityMainBinding binding;

    private Spinner spinnerInputLang;
    private Spinner spinnerTranslationLang;

    private LanguageSpinnerAdapter spinnerAdapterInputLangs;
    private LanguageSpinnerAdapter spinnerAdapterTranslationLangs;

    private Disposable inputSpinnerSubscription;
    private Disposable translationSpinnerSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Dependency Injection
        App.plusMainSubcomponent(new MainPresenterModule(this)).inject(this);

        // UI
        TranslationFragment translationFragment = new TranslationFragment();
        binding.bottomnavMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_bottomnav_translation: {
                    setTranslationFragment(translationFragment);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Wrong item id");
                }
            }
            return false;
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // Setup initial screen
        setTranslationFragment(translationFragment);

        presenter.onCreate(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.disposeAll(inputSpinnerSubscription, translationSpinnerSubscription);
        App.clearMainPresenterComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
    }

    private void setTranslationFragment(TranslationFragment fragment) {
        setFragment(fragment);
        // Toolbar layout
        ActionBar supportActionBar = getSupportActionBar();
        LayoutTranslationToolbarBinding toolbarBinding = DataBindingUtil
                .inflate(getLayoutInflater(), R.layout.layout_translation_toolbar, binding.relativelayoutMainRoot, false);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setCustomView(toolbarBinding.relativelayoutTranslationToolbarRoot, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        // Spinners
        spinnerInputLang = toolbarBinding.spinnerTranslationLangInput;
        spinnerTranslationLang = toolbarBinding.spinnerTranslationLangTranslation;
        // Adapters
        spinnerAdapterInputLangs = new LanguageSpinnerAdapter(this);
        spinnerAdapterTranslationLangs = new LanguageSpinnerAdapter(this);
        spinnerInputLang.setAdapter(spinnerAdapterInputLangs);
        spinnerTranslationLang.setAdapter(spinnerAdapterTranslationLangs);
        // Reactive event listeners
        inputSpinnerSubscription = RxAdapterView
                .itemSelections(toolbarBinding.spinnerTranslationLangInput)
                .subscribe(position -> {
                    String language = spinnerAdapterInputLangs.getItem(position);
                    fragment.setInputLanguage(language);
                });
        translationSpinnerSubscription = RxAdapterView
                .itemSelections(toolbarBinding.spinnerTranslationLangTranslation)
                .subscribe(position -> {
                    String language = spinnerAdapterTranslationLangs.getItem(position);
                    fragment.setTranslationLanguage(language);
                });
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout_main_container, fragment);
        transaction.commit();
    }

    @Override
    public void onLoadLanguages(LanguagesHolder langs) {
        if (spinnerAdapterInputLangs != null && spinnerAdapterTranslationLangs != null) {
            spinnerAdapterInputLangs.setLangs(langs);
            spinnerInputLang.setSelection(spinnerAdapterInputLangs.getItemPosition(langs.forLanguage));
            spinnerAdapterTranslationLangs.setLangs(langs);
            presenter.getInitialTranslationLang(langs);
        }
    }

    @Override
    public void onSelectTranslationLanguage(String lang) {
        if (spinnerTranslationLang != null) {
            spinnerTranslationLang.setSelection(spinnerAdapterTranslationLangs.getItemPosition(lang));
        }
    }

    @Override
    public void onError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
    }
}
