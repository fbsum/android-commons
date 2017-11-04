package com.fbsum.android.experiment.rx;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;

/**
 * Created by xin on 10/26/17.
 */

public final class DisposableUtil {

    public static void dispose(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
