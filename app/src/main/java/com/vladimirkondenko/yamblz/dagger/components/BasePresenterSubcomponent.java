package com.vladimirkondenko.yamblz.dagger.components;

import android.support.v4.app.Fragment;

import com.vladimirkondenko.yamblz.BasePresenter;
import com.vladimirkondenko.yamblz.BaseView;

public interface BasePresenterSubcomponent<F extends BaseView, P extends BasePresenter> {
    P presenter();
    void inject(F fragment);
}
