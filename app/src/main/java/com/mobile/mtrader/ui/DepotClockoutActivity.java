package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.mtrader.adapter.ClockOutAdaper;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.BankViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DepotClockoutActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    BankViewModel bankViewModel;

    ApplicationComponent component;

    ClockOutAdaper clockOutAdaper;

    @BindView(R.id.clock_out_depot)
    RecyclerView clock_out_depot;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.payments_btn)
    Button payments_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_clockout);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        bankViewModel = ViewModelProviders.of(this,viewModelFactory).get(BankViewModel.class);
        bankViewModel.salesEntriesToday();

        String cDates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String cTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String cmsg = "Close already taken";

        back_page.setOnClickListener(v-> onBackPressed());

        clock_out_depot.setLayoutManager(new LinearLayoutManager(this));
        clock_out_depot.setHasFixedSize(true);
        clockOutAdaper = new ClockOutAdaper(this);
        clock_out_depot.setAdapter(clockOutAdaper);

        bankViewModel.emitSalesEntries().observe(this, sales ->{
            progressbar.setVisibility(View.GONE);
            clockOutAdaper.setModulesAdapter(sales);
        });

        payments_btn.setOnClickListener(v->{
            if(!AppUtil.checkConnection(this)) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressDialog();
                bankViewModel.setEmployeeDetails().observe(this,
                        employees -> bankViewModel.setUserDailyAttendantForClockout(employees.user_id,
                                6, cDates, cTime,"0.000","0.666",cmsg
                        ));
            }
        });


        bankViewModel.getObserveResponse().observe(this, s -> {
            hideProgressDialog();
            String[] res = s.split("\\~");

            if(Integer.parseInt(res[0])==200){
                Intent intent = new Intent(this,SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else if(Integer.parseInt(res[0])==401){
                Toast.makeText(this, res[1], Toast.LENGTH_SHORT).show();
            }
        });

        bankViewModel.getThrowable().observe(this, throwable -> {
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
