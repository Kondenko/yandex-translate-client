package com.vladimirkondenko.yamblz.screens.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.databinding.ActivityMainBinding;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.utils.LanguageSpinnerAdapter;
import com.vladimirkondenko.yamblz.utils.Utils;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    @Inject
    public MainPresenter presenter;

    private Spinner spinnerInputLangs;
    private Spinner spinnerTranslationLangs;

    private Disposable inputSpinnerSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Dependency Injection
        App.plus(new MainPresenterModule(this)).inject(this);

        // UI
        TranslationFragment translationFragment = new TranslationFragment();
        binding.bottomnavMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_bottomnav_translation: {
                    setFragment(translationFragment);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Wrong item id");
                }
            }
            return false;
        });

        // Setup initial screen
        setFragment(translationFragment);

        // Show language selection spinners in the toolbar
        initToolbarView();

        presenter.getInputLanguages(this);
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
        resetRxBindings();
        App.clearMainPresenterComponent();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout_main_container, fragment);
        transaction.commit();
    }

    private void initToolbarView() {
        Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_translation_toolbar, null);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowCustomEnabled(true);
            spinnerInputLangs = (Spinner) findViewById(R.id.spinner_translation_lang_from);
            spinnerTranslationLangs = (Spinner) findViewById(R.id.spinner_translation_lang_to);
        }
    }

    private void resetRxBindings() {
        Utils.dispose(inputSpinnerSubscription);
    }

    @Override
    public void onLoadInputLanguages(LanguagesHolder languages) {
        if (spinnerInputLangs != null) {
            LanguageSpinnerAdapter spinnerAdapterInputLangs = new LanguageSpinnerAdapter(this, languages);
            spinnerInputLangs.setAdapter(spinnerAdapterInputLangs);
            inputSpinnerSubscription = RxAdapterView
                    .itemSelections(spinnerInputLangs)
                    .subscribe(position -> {
                        String language = spinnerAdapterInputLangs.getItem(position);
                        presenter.getTranslationLanguages(language);
                    });
        }
    }

    @Override
    public void onLoadTranslationLanguages(LanguagesHolder languages) {
        if (spinnerTranslationLangs != null) {
            LanguageSpinnerAdapter spinnerAdapterTranslationLangs = new LanguageSpinnerAdapter(this, languages);
            spinnerTranslationLangs.setAdapter(spinnerAdapterTranslationLangs);
        }
    }

    @Override
    public void onError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
    }
}
