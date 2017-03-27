package com.vladimirkondenko.yamblz.screens.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.Spinner;

import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.MainPresenterModule;
import com.vladimirkondenko.yamblz.databinding.ActivityMainBinding;
import com.vladimirkondenko.yamblz.model.LanguagesHolder;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.utils.LanguageSpinnerAdapter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    @Inject
    public MainPresenter presenter;

    private ActivityMainBinding binding;

    private Toolbar toolbar;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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

        // Setup initial state
        setFragment(translationFragment);
        // Show language selection spinners in the toolbar
        initToolbarView();

        presenter.getLanguages();
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
        App.clearMainPresenterComponent();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout_main_container, fragment);
        transaction.commit();
    }

    private void initToolbarView() {
        toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout_translation_toolbar, null);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowCustomEnabled(true);
            spinnerFrom = (Spinner) findViewById(R.id.spinner_translation_lang_from);
            spinnerTo = (Spinner) findViewById(R.id.spinner_translation_lang_to);
        }
    }

    @Override
    public void loadLanguages(LanguagesHolder inputLangs, LanguagesHolder translationLangs) {
        if (spinnerFrom != null) {
            LanguageSpinnerAdapter adapterInput = new LanguageSpinnerAdapter(this, inputLangs);
            spinnerFrom.setAdapter(adapterInput);
        }
        if (spinnerTo != null) {
            LanguageSpinnerAdapter adapterTranslation = new LanguageSpinnerAdapter(this, translationLangs);
            spinnerTo.setAdapter(adapterTranslation);
        }
    }

    @Override
    public void onError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
    }
}
