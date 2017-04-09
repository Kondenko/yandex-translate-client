package com.vladimirkondenko.yamblz.utils;


import android.util.Log;

public abstract class BasePresenter<V extends BaseView, I extends BaseInteractor> {

    private static final String TAG = "BasePresenter";

    protected V view;
    protected I interactor;

    public void attachView(V view) {
        Log.i(TAG, "attachView");
        this.view = view;
    }

    public void detachView() {
        Log.i(TAG, "detachView");
        view = null;
    }

    public final boolean isViewAttached() {
        return view != null;
    }

    public final V getView() {
        return view;
    }

}
