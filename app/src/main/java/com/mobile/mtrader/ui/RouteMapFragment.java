package com.mobile.mtrader.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobile.mtrader.mobiletreaderv3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteMapFragment extends Fragment {


    public RouteMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_map2, container, false);
    }

}
