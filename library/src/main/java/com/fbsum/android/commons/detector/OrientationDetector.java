package com.fbsum.android.commons.detector;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.view.OrientationEventListener;

import com.fbsum.android.commons.util.LifecycleUtils;


/**
 * 方向检测器
 */
public class OrientationDetector extends OrientationEventListener implements LifecycleObserver {

    private int crrOrientation = 270;
    private AppCompatActivity activity;
    private OrientationChangedListener orientationChangedListener;

    public OrientationDetector(AppCompatActivity activity) {
        super(activity, SensorManager.SENSOR_DELAY_UI);
        this.activity = activity;
        this.activity.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        enable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        disable();
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (LifecycleUtils.isNotResume(activity)) {
            return;
        }
        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            return;  //手机平放时，检测不到有效的角度
        }
        //只检测是否有四个角度的改变
        if (orientation > 350 || orientation < 10) { //0度
            orientation = 0;
        } else if (orientation > 80 && orientation < 100) { //90度
            orientation = 90;
        } else if (orientation > 170 && orientation < 190) { //180度
            orientation = 180;
        } else if (orientation > 200 && orientation < 300) { //270度
            orientation = 270;
        } else {
            return;
        }
        if (orientation == crrOrientation) {
            return;
        }
        crrOrientation = orientation;
        if (orientationChangedListener != null) {
            orientationChangedListener.onOrientationChanged(orientation);
        }
    }

    /**
     * 回调接口
     */
    public interface OrientationChangedListener {
        void onOrientationChanged(int orientation);
    }

    public void setOrientationChangedListener(OrientationChangedListener listener) {
        this.orientationChangedListener = listener;
    }
}