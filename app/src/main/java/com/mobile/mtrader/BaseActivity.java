package com.mobile.mtrader;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.mobile.mtrader.mobiletreaderv3.R;

public abstract  class BaseActivity  extends AppCompatActivity {

    public ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.actitvity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_contents);
        mProgressBar = constraintLayout.findViewById(R.id.base_progress_bar);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);

    }

    public void showProgressBar(boolean visible) {
        mProgressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}
