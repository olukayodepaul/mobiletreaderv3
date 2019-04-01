package com.mobile.mtrader.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mtrader.adapter.ClockInAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.ClockInViewModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DepotClokingActivity extends AppCompatActivity {

    ClockInAdapter clockInAdapter;

    @BindView(R.id.user_baskets)
    RecyclerView user_baskets;

    @BindView(R.id.totalitems)
    TextView totalitems;

    @BindView(R.id.totalqty)
    TextView totalqty;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.resume)
    Button resume;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ClockInViewModel clockInViewModel;

    DecimalFormat myFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_cloking);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        clockInViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClockInViewModel.class);
        progressbar.setVisibility(View.GONE);

        String cDates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String cTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String cmsg = "Resumption already taken";

        myFormatter = new DecimalFormat("#,###.00");

        back_page.setOnClickListener(v->onBackPressed());

        //get the lat and lng here.

        resume.setOnClickListener(v->{

            if(!AppUtil.checkConnection(this)) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressDialog();
                clockInViewModel.setEmployeeDetails().observe(this, employees -> clockInViewModel.setUserDailyAttendant(employees.user_id,
                        1, cDates, cTime,"0.000","0.666",cmsg
                ));
            }
        });
        observeAll();
    }

    public void observeAll() {

        clockInViewModel.sumAllMyProduct("1").observe(this, products ->
                totalqty.setText(myFormatter.format(products)));

        user_baskets.setLayoutManager(new LinearLayoutManager(this));
        user_baskets.setHasFixedSize(true);
        clockInAdapter = new ClockInAdapter(this);
        user_baskets.setAdapter(clockInAdapter);

        clockInViewModel.setBasket("1").observe(this, products ->
                clockInAdapter.setModulesAdapter(products));

        clockInViewModel.getObserveResponse().observe(this, s -> {
            hideProgressDialog();
            String[] res = s.split("\\~");

            if(Integer.parseInt(res[0])==200){
                Toast.makeText(this, res[1], Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(res[0])==401){
                Toast.makeText(this, res[1], Toast.LENGTH_SHORT).show();
            }
        });

        clockInViewModel.getThrowable().observe(this, throwable -> {
            hideProgressDialog();
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void showProgressDialog() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        progressbar.setVisibility(View.GONE);
    }

}
