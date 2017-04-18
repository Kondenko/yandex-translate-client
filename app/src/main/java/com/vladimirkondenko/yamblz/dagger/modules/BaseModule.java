package com.vladimirkondenko.yamblz.dagger.modules;


import com.vladimirkondenko.yamblz.utils.base.BaseView;

public abstract class BaseModule<T extends BaseView> {

    protected T view;

    protected BaseModule(T view) {
        this.view = view;
    }

}
