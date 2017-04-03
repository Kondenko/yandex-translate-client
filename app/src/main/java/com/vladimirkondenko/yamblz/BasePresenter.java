package com.vladimirkondenko.yamblz;


public abstract class BasePresenter<T extends BaseView> {

    protected T view;

    protected BasePresenter() {
    }

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public final boolean isViewAttached() {
        return view != null;
    }

    public final T getView() {
        return view;
    }

}
