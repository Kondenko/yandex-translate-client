package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.utils.BasePresenter;
import com.vladimirkondenko.yamblz.utils.BaseView;

public interface BasePresenterSubcomponent<V extends BaseView, P extends BasePresenter> {
    P presenter();
    void inject(V view);
}
