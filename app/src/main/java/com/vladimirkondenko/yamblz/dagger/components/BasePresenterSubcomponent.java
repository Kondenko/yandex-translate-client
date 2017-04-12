package com.vladimirkondenko.yamblz.dagger.components;

import com.vladimirkondenko.yamblz.utils.base.BasePresenter;
import com.vladimirkondenko.yamblz.utils.base.BaseView;

public interface BasePresenterSubcomponent<V extends BaseView, P extends BasePresenter> {
    P presenter();
    void inject(V view);
}
