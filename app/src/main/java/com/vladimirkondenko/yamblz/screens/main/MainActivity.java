package com.vladimirkondenko.yamblz.screens.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.vladimirkondenko.yamblz.App;
import com.vladimirkondenko.yamblz.Const;
import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.dagger.modules.MainModule;
import com.vladimirkondenko.yamblz.databinding.ActivityMainBinding;
import com.vladimirkondenko.yamblz.databinding.LayoutTranslationToolbarBinding;
import com.vladimirkondenko.yamblz.model.entities.Languages;
import com.vladimirkondenko.yamblz.screens.ScreenCodes;
import com.vladimirkondenko.yamblz.screens.history.HistoryFragment;
import com.vladimirkondenko.yamblz.screens.translation.TranslationFragment;
import com.vladimirkondenko.yamblz.utils.Utils;
import com.vladimirkondenko.yamblz.utils.adapters.LanguageSpinnerAdapter;
import com.vladimirkondenko.yamblz.utils.events.Bus;
import com.vladimirkondenko.yamblz.utils.events.InputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.OutputLanguageSelectionEvent;
import com.vladimirkondenko.yamblz.utils.events.SelectLanguageEvent;
import com.vladimirkondenko.yamblz.utils.events.SwapLanguageEvent;
import com.vladimirkondenko.yamblz.utils.ui.AnimUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainActivity extends AppCompatActivity implements MainView {

    static {
        // VectorDrawable support for pre-Lollipop devices
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    public MainPresenter presenter;

    @Inject
    public TranslationFragment translationFragment;

    @Inject
    public HistoryFragment historyFragment;

    private int currentFragment = ScreenCodes.Translation.SCREEN_ID;

    private ActivityMainBinding binding;

    private Spinner spinnerInputLangs;

    private LanguageSpinnerAdapter adapterInputLangs;

    private Spinner spinnerOutputLangs;

    private LanguageSpinnerAdapter adapterOutputLangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        App.get().plusMainSubcomponent(new MainModule(this)).inject(this);
        Bus.subscribe(this);
        presenter.attachView(this);
        binding.bottomnavMain.setOnNavigationItemSelectedListener(item -> {
            presenter.selectScreen(ScreenCodes.menuItemToScreenId(item.getItemId()));
            return false;
        });
        RxView.clicks(binding.textviewAllBannerYandex).subscribe(o -> onBannerClicked());
        setSupportActionBar(binding.toolbarMain);
        // Set initial screen
        if (savedInstanceState != null) currentFragment = savedInstanceState.getInt(Const.BUNDLE_SELECTED_FRAGMENT);
        presenter.selectScreen(currentFragment);
        presenter.getLanguagesList();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        Bus.unsubscribe(this);
        App.get().clearMainPresenterComponent();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Const.BUNDLE_SELECTED_FRAGMENT, currentFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSelectTranslationScreen() {
        currentFragment = ScreenCodes.Translation.SCREEN_ID;
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        setFragment(translationFragment);
    }

    @Override
    public void onSelectHistoryScreen() {
        currentFragment = ScreenCodes.History.SCREEN_ID;
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        setFragment(historyFragment);
    }

    @Override
    public void onLoadLanguages(Languages langs) {
            setupCustomToolbar(langs);
            presenter.getSelectedLanguages(langs);
    }

    @Override
    public void onSelectInputLang(String lang) {
        spinnerInputLangs.setSelection(adapterInputLangs.getItemPosition(lang));
    }

    @Override
    public void onSelectOutputLang(String lang) {
        spinnerOutputLangs.setSelection(adapterOutputLangs.getItemPosition(lang));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectDetectedLanguage(SelectLanguageEvent event) {
        spinnerInputLangs.setSelection(adapterInputLangs.getItemPosition(event.getSelectedLang()), true);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.framelayout_main_container, fragment);
        transaction.commit();
    }

    private void onBannerClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(Const.BANNER_URL));
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    private void setupCustomToolbar(Languages langs) {
        // Toolbar layout
        LayoutTranslationToolbarBinding toolbarBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.layout_translation_toolbar,
                binding.relativelayoutMainRoot,
                false
        );
        getSupportActionBar().setCustomView(toolbarBinding.relativelayoutTranslationToolbarRoot, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        // Views
        ImageButton buttonSwapLanguage = toolbarBinding.buttonTranslationSwitchLanguage;
        buttonSwapLanguage.setImageDrawable(Utils.getTintedIcon(this, R.drawable.ic_switch_language_black_24dp));
        spinnerInputLangs = toolbarBinding.spinnerTranslationLangInput;
        spinnerOutputLangs = toolbarBinding.spinnerTranslationLangTranslation;
        // Adapters
        adapterInputLangs = new LanguageSpinnerAdapter(this);
        adapterOutputLangs = new LanguageSpinnerAdapter(this);
        spinnerInputLangs.setAdapter(adapterInputLangs);
        spinnerOutputLangs.setAdapter(adapterOutputLangs);
        adapterInputLangs.setLangs(langs, true);
        adapterOutputLangs.setLangs(langs);
        // Reactive event listeners
        RxAdapterView.itemSelections(toolbarBinding.spinnerTranslationLangInput)
                .filter(i -> i >= 0)
                .map(adapterInputLangs::getItem)
                .filter(lang -> lang != null)
                .subscribe(language -> {
                    // Disable the swap button if the language is not specified
                    toolbarBinding.buttonTranslationSwitchLanguage.setEnabled(!language.equals(Const.LANG_CODE_AUTO));
                    Bus.post(new InputLanguageSelectionEvent(language));
                    presenter.setInputLang(language);
                });
        RxAdapterView.itemSelections(toolbarBinding.spinnerTranslationLangTranslation)
                .filter(i -> i >= 0)
                .map(adapterOutputLangs::getItem)
                .filter(lang -> lang != null)
                .subscribe(language -> {
                    Bus.post(new OutputLanguageSelectionEvent(language));
                    presenter.setOutputLang(language);
                });
        // Language swapping button
        RxView.clicks(buttonSwapLanguage)
                .subscribe(event -> {
                    Bus.post(new SwapLanguageEvent());
                    int animDistance = 4;
                    int animDuration = Const.ANIM_DURATION_LANG_SWITCH_SPINNER;
                    // +/- 1's are used because of the "Detect language" item
                    // We have to shift the position to get the actual selected language
                    int currentInputPosition = spinnerInputLangs.getSelectedItemPosition();
                    int currentOutputPosition = spinnerOutputLangs.getSelectedItemPosition();
                    toolbarBinding.buttonTranslationSwitchLanguage.animate()
                            .rotationBy(180)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .setDuration(Const.ANIM_DURATION_DEFAULT);
                    AnimUtils.slideInAndOut(
                            spinnerInputLangs,
                            true,
                            animDistance,
                            animDuration,
                            () -> buttonSwapLanguage.setClickable(false), // Disable button clicks to prevent multiple animations from being executed
                            () -> {
                                spinnerInputLangs.setSelection(currentOutputPosition + 1);
                                buttonSwapLanguage.setClickable(true); // Enable button clicks when the animation ends
                            }
                    );
                    AnimUtils.slideInAndOut(
                            spinnerOutputLangs,
                            false,
                            animDistance,
                            animDuration,
                            () -> {},
                            () -> spinnerOutputLangs.setSelection(currentInputPosition - 1)
                    );
                });
    }

    @Override
    public void onError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
    }

}
