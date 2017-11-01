package com.fbsum.android.commons.detector;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 底部导航栏显示和隐藏监测器
 * <p>
 * Created by xin on 9/24/17.
 */

public class BottomNavigationBarDetector implements LifecycleObserver {

    private int lastRootHeight = 0;
    private View rootView;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private OnStateChangedListener onStateChangedListener;

    public void attach(AppCompatActivity activity) {
        activity.getLifecycle().addObserver(this);
        rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onStateChangedListener == null) {
                    return;
                }
                int height = rootView.getHeight();
                if (lastRootHeight == 0) {
                    lastRootHeight = height;
                    return;
                }
                if (lastRootHeight > height) {
                    lastRootHeight = height;
                    onStateChangedListener.onOpen();
                } else if (lastRootHeight < height) {
                    lastRootHeight = height;
                    onStateChangedListener.onClose();
                }
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (onGlobalLayoutListener != null) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    /**
     * 回调接口
     */
    public interface OnStateChangedListener {
        void onOpen();

        void onClose();
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        this.onStateChangedListener = listener;
    }
}
