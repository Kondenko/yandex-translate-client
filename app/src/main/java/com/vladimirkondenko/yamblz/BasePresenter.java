package com.vladimirkondenko.yamblz;


public abstract class BasePresenter<T extends BaseView> {

    protected T view;

    protected BasePresenter() {
    }

    public BasePresenter(T view) {
        this.view = view;
    }

    protected void attachView(T view) {
        this.view = view;
    }

    protected void detachView() {
        view = null;
    }

    protected final boolean isViewAttached() {
        return view != null;
    }

    protected final T getView() {
        return view;
    }

}
