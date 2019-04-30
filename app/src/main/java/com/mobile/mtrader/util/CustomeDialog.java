package com.mobile.mtrader.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.mobile.mtrader.mobiletreaderv3.R;

public class CustomeDialog extends Dialog implements View.OnClickListener {

    Context context;

    String message;

    public CustomeDialog(@NonNull Context context, String message) {

        super(context);
        this.context = context;
        this.message = message;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customes_dialog);
    }

    @Override
    public void onClick(View view) {

    }
}
