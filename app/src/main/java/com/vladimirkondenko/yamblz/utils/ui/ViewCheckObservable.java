package com.vladimirkondenko.yamblz.utils.ui;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 * Observable that emits checked changes of an AppCompatCheckableImageButton.
 *
 * @see RxCheckableImageButton
 * @see AppCompatCheckableImageButton
 */
public final class ViewCheckObservable extends Observable<Boolean> {
    private final AppCompatCheckableImageButton view;

    public ViewCheckObservable(AppCompatCheckableImageButton view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Boolean> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnCheckedChangeListener(listener);
    }

    public static final class Listener extends MainThreadDisposable implements AppCompatCheckableImageButton.CheckedChangeListener {
        private final AppCompatCheckableImageButton view;
        private final Observer<? super Boolean> observer;

        public Listener(AppCompatCheckableImageButton view, Observer<? super Boolean> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void onCheckedChanged(boolean checked) {
            if (!isDisposed()) {
                observer.onNext(checked);
            }
        }

        @Override
        protected void onDispose() {
            view.setOnClickListener(null);
        }
    }
}
