package com.vladimirkondenko.yamblz.utils.ui;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkNotNull;

/**
 * Reactive binding for AppCompatCheckableImageButton
 *
 * @see RxView
 * @see AppCompatCheckableImageButton
 */

public class RxCheckableImageButton {

    @CheckResult
    @NonNull
    public static Observable<Boolean> checks(@NonNull AppCompatCheckableImageButton view) {
        checkNotNull(view, "view == null");
        return new ViewCheckObservable(view);
    }

}
