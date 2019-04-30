package com.mobile.mtrader.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.ClockOutAdaper;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.ClockOutViewModel;

import java.text.DecimalFormat;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DepotClockoutActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ClockOutViewModel clockOutViewModel;

    ApplicationComponent component;

    @BindView(R.id.user_baskets)
    RecyclerView user_baskets;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.payments_btn)
    Button payments_btn;

    @BindView(R.id.basket_t)
    TextView basket_t;

    @BindView(R.id.qty_t)
    TextView qty_t;

    @BindView(R.id.order_t)
    TextView order_t;

    ClockOutAdaper clockOutAdaper;

    DecimalFormat myFormatter;

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
        clockOutViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClockOutViewModel.class);
        showProgressBar(true);
        myFormatter = new DecimalFormat("#,###.0");

        payments_btn.setOnClickListener(v -> {
            showProgressBar(true);
            if (!AppUtil.checkConnection(this)) {
                showProgressBar(false);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                clockOutViewModel.dailyRoster();
            }
        });

        back_page.setOnClickListener(v -> onBackPressed());

        user_baskets.setLayoutManager(new LinearLayoutManager(this));
        user_baskets.setHasFixedSize(true);
        clockOutAdaper = new ClockOutAdaper(this, clockOutViewModel);
        user_baskets.setAdapter(clockOutAdaper);
        observeAll();
        clockOutViewModel.sunAllTotalSoldProduct();
    }

    public void observeAll() {

        clockOutViewModel.setBasket().observe(this, products -> {

            double total = 0.0;

            showProgressBar(false);
            for(int i = 0 ; i < products.size() ; i++) {
                total += Double.parseDouble(products.get(i).qty);
            }

            double finalTotal = total;

            clockOutViewModel.getAllSalesEntrySum().observe(this, sumData-> {
                qty_t.setText(myFormatter.format(sumData));
                order_t.setText(myFormatter.format(finalTotal -sumData));
            });

            basket_t.setText(myFormatter.format(total));
            clockOutAdaper.setModulesAdapter(products);
            clockOutAdaper.notifyDataSetChanged();

        });

        clockOutViewModel.getObserveResponse().observe(this, s -> {

            showProgressBar(false);
            String[] res = s.split("\\~");

            if(Integer.parseInt(res[0])==200) {
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
