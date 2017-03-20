package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.BaseView;

abstract class BasePresenterModule<T extends BaseView> {

    protected T view;

    BasePresenterModule() {}

    BasePresenterModule(T view) {
        this.view = view;
    }

}
