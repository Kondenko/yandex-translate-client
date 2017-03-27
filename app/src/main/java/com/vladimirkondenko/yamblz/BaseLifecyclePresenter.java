package com.vladimirkondenko.yamblz;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.PresenterEvent;
import com.vladimirkondenko.yamblz.utils.rxlifecycle.RxLifecyclePresenter;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class BaseLifecyclePresenter<V extends BaseView> extends BasePresenter<V> implements LifecycleProvider<Integer> {

    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

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
