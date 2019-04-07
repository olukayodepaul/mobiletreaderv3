package com.mobile.mtrader.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.mtrader.adapter.BankAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.BankViewModel;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity {

    BankViewModel bankViewModel;

    ApplicationComponent component;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.banks)
    RecyclerView banks;

    @BindView(R.id.back_page)
    ImageView back_page;

    BankAdapter bankAdapter;

    @BindView(R.id.payments_btn)
    Button payments_btn;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
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
        String cmsg = "Bank already taken";

        back_page.setOnClickListener(v-> onBackPressed());

        banks.setLayoutManager(new LinearLayoutManager(this));
        banks.setHasFixedSize(true);
        bankAdapter = new BankAdapter(this);
        banks.setAdapter(bankAdapter);
        bankViewModel.emitSalesEntries().observe(this, sales -> {
            progressbar.setVisibility(View.GONE);
            bankAdapter.setModulesAdapter(sales);
        });

        payments_btn.setOnClickListener(v->{
            if(!AppUtil.checkConnection(this)) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressDialog();
                bankViewModel.setEmployeeDetails().observe(this,
                        employees -> bankViewModel.setUserDailyAttendant(employees.user_id,
                                5, cDates, cTime,"0.000","0.666",cmsg
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
