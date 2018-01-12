package com.fbsum.android.experiment.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by xin on 1/12/18.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置透明背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // 添加 MATCH_PARENT 的父容器
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 添加内容布局
        View contentView = inflater.inflate(getContentView(), null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        frameLayout.addView(contentView, lp);
        contentView.setClickable(true);
        initViews(contentView);

        return frameLayout;
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initViews(View contentView);

    @Override
    public void onStart() {
        super.onStart();

        // 让 Dialog 的宽高都 MATCH_PARENT
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height);
            }
        }
    }
}
