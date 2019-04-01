package com.mobile.mtrader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.mtrader.mobiletreaderv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bnview)
    BottomNavigationView bnView;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.back_page)
    ImageView back_page;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        back_page.setOnClickListener(v->onBackPressed());


        bottomNavigationView = bnView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.sales);
    }

    RouteMapFragment routeMapFragment = new RouteMapFragment();
    CustomerFragment salesFragment = new CustomerFragment();
    SalesHistoryFragment salesHistoryFragment = new SalesHistoryFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.sales:
                title.setText("Customers");
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, salesFragment).commit();
                return true;

            case R.id.shtry:
                title.setText("Sales History");
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, salesHistoryFragment).commit();
                return true;

            case R.id.routepam:
                title.setText("Route Map");
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out) .replace(R.id.container, routeMapFragment).commit();
                return true;

        }

        return false;
    }
}
