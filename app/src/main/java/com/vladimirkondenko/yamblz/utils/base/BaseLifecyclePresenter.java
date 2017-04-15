package com.vladimirkondenko.yamblz.utils.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.RxLifecyclePresenter;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public abstract class BaseLifecyclePresenter<V extends BaseView, I extends BaseInteractor> extends BasePresenter<V, I> implements LifecycleProvider<Integer> {

    private static final String TAG = "BaseLifecyclePresenter";

    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    protected BaseLifecyclePresenter(V view, I interactor) {
        this.attachView(view);
        this.interactor = interactor;
    }

    @Override
    @NonNull
    public final Observable<Integer> lifecycle() {
        return lifecycleSubject;
    }

    @Override
    @NonNull
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull Integer event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecyclePresenter.bindPresenter(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void attachView(V view) {
        super.attachView(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void detachView() {
        super.detachView();
        lifecycleSubject.onNext(PresenterEvent.DETACH);
    }

}
