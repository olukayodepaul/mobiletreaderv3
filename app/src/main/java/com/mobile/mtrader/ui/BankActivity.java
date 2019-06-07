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
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.BankAdapter;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.SumSales;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.BankViewModel;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BankActivity extends BaseActivity {

    BankViewModel bankViewModel;

    ApplicationComponent component;

    @BindView(R.id.user_baskets)
    RecyclerView user_baskets;

    @BindView(R.id.back_page)
    ImageView back_page;

    BankAdapter bankAdapter;

    @BindView(R.id.payments_btn)
    Button payments_btn;

    @BindView(R.id.basket_t)
    TextView basket_t;

    @BindView(R.id.qty_t)
    TextView qty_t;

    @BindView(R.id.order_t)
    TextView order_t;

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
        bankViewModel = ViewModelProviders.of(this, viewModelFactory).get(BankViewModel.class);
        showProgressBar(true);
        DecimalFormat formatter = new DecimalFormat("#,###.0");

        back_page.setOnClickListener(v -> onBackPressed());

        user_baskets.setLayoutManager(new LinearLayoutManager(this));
        user_baskets.setHasFixedSize(true);
        bankAdapter = new BankAdapter(this, bankViewModel);
        user_baskets.setAdapter(bankAdapter);

        bankViewModel.setBasket().observe(this, sales -> {

            bankAdapter.setModulesAdapter(sales);
            bankAdapter.notifyDataSetChanged();
            showProgressBar(false);

        });

        bankViewModel.sumAllSalesCommission()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SumSales>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(SumSales sumSales) {
                        basket_t.setText(formatter.format(sumSales.mPrice));
                        qty_t.setText(formatter.format(sumSales.mOrder));
                        order_t.setText(formatter.format(sumSales.mSales));
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                });

        payments_btn.setOnClickListener(v -> {
            showProgressBar(true);
            if (!AppUtil.checkConnection(this)) {
                showProgressBar(false);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                bankViewModel.dailyRoster();
            }
        });


        bankViewModel.getObserveResponse().observe(this, s -> {
            
            showProgressBar(false);
            String[] res = s.split("\\~");

            if(Integer.parseInt(res[0])==200){
                Intent intent = new Intent(this,SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else if(Integer.parseInt(res[0])==401) {
                Toast.makeText(this, res[1], Toast.LENGTH_SHORT).show();
            }
        });

    }
}
