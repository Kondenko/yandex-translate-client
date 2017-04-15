package com.vladimirkondenko.yamblz.utils.rxlifecycle;


import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public final class RxLifecyclePresenter {

    private RxLifecyclePresenter() {
        throw new AssertionError("No instances");
    }


    @NonNull
    public static <T> LifecycleTransformer<T> bindPresenter(@NonNull final Observable<Integer> lifecycle) {
        return RxLifecycle.bind(lifecycle, PRESENTER_LIFECYCLE);
    }

    private static final Function<Integer, Integer> PRESENTER_LIFECYCLE = lastEvent -> {
        switch (lastEvent) {
            case PresenterEvent.ATTACH:
                return PresenterEvent.DETACH;
            case PresenterEvent.DETACH:
                throw new OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.");
            default:
                throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
        }
    };

}