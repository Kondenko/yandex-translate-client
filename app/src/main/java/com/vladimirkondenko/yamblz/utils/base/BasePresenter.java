package com.vladimirkondenko.yamblz.utils.base;


public abstract class BasePresenter<V extends BaseView, I extends BaseInteractor> {

    private static final String TAG = "BasePresenter";

    protected V view;
    protected I interactor;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public final boolean isViewAttached() {
        return view != null;
    }

    public final V getView() {
        return view;
    }

}
