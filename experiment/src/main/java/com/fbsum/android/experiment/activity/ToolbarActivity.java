package com.fbsum.android.experiment.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.fbsum.android.experiment.R;

/**
 * Created by xin on 1/30/18.
 */

public abstract class ToolbarActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_toolbar_activity);
        getWindow().setBackgroundDrawable(null);

        toolbar = findViewById(R.id.toolbar);
        int titleRes = getToolbarTitleRes();
        if (titleRes != 0) {
            toolbar.setTitle(titleRes);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout container = findViewById(R.id.toolbar_activity_container);
        int contentViewRes = getContentViewRes();
        if (contentViewRes != 0) {
            getLayoutInflater().inflate(contentViewRes, container, true);
        }

        onCreate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @StringRes
    protected abstract int getToolbarTitleRes();

    @LayoutRes
    protected abstract int getContentViewRes();

    protected abstract void onCreate();
}
