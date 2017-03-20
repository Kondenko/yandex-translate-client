package com.vladimirkondenko.yamblz;


public abstract class BasePresenter {

    protected BaseView view;

    protected BasePresenter() {}

    protected BasePresenter(BaseView view) {
        this.view = view;
    }

}
