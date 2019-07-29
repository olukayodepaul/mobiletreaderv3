package com.mobile.mtrader.ui;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobile.mtrader.mobiletreaderv3.R;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class Camera2Fragment extends Fragment {

    public static Camera2Fragment newInstance(){
        return new Camera2Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera2, container, false);
        return view;
    }

}
