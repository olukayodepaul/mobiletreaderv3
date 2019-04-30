package com.mobile.mtrader.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.ConfirmSalesAdapter;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.RepSalesConfirmViewModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ConfirmSales extends BaseActivity {

    @BindView(R.id.back_page)
    ImageView back_page;

    Bundle bundle;

    String dbToken, customerno;

    @BindView(R.id.saleValues)
    RecyclerView saleValues;

    @BindView(R.id.order_t)
    TextView order_t;

    @BindView(R.id.app_price_t)
    TextView app_price_t;

    @BindView(R.id.invent_t)
    TextView invent_t;

    @BindView(R.id.tv_price_t)
    TextView tv_price_t;

    @BindView(R.id.confirms)
    EditText confirms;

    @BindView(R.id.btns)
    Button btns;

    Intent intent;

    ApplicationComponent component;

    RepSalesConfirmViewModel repSalesConfirmViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    CompositeDisposable mDis = new CompositeDisposable();

    ConfirmSalesAdapter confirmSalesAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sales);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        repSalesConfirmViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepSalesConfirmViewModel.class);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        bundle = getIntent().getExtras();
        showProgressBar(true);

        if (bundle != null) {
            dbToken = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customerno = bundle.getString("CUSTOMER_NO");
        }

        repSalesConfirmViewModel.salesEntries("1", customerno);
        saleValues.setLayoutManager(new LinearLayoutManager(this));
        saleValues.setHasFixedSize(true);
        confirmSalesAdapter = new ConfirmSalesAdapter(this);
        saleValues.setAdapter(confirmSalesAdapter);


        repSalesConfirmViewModel.SalesEntriesdata().observe(this, salesEntries -> {

            int sumAllPP = 0;
            int sumAllRP = 0;
            double sumInventory = 0.0;
            int sumPricing = 0;
            double sumOrder = 0.0;

            for (int i = 0; i < salesEntries.size(); i++) {

                String[] divider = Double.toString(Double.parseDouble(salesEntries.get(i).orders)).split("\\.");
                sumAllPP += Integer.parseInt(divider[0]) * Double.parseDouble(salesEntries.get(i).rollprice);
                sumAllRP += Integer.parseInt(divider[1]) * Double.parseDouble(salesEntries.get(i).packprice);
                sumInventory += Double.parseDouble(salesEntries.get(i).inventory);
                sumPricing += Double.parseDouble(salesEntries.get(i).pricing);
                sumOrder += Double.parseDouble(salesEntries.get(i).orders);

            }

            invent_t.setText(formatter.format(sumOrder));
            app_price_t.setText(formatter.format(sumPricing));
            order_t.setText(formatter.format(sumInventory));
            tv_price_t.setText(formatter.format(sumAllPP + sumAllRP));
            confirmSalesAdapter.setModulesAdapter(salesEntries);
            showProgressBar(false);

        });

        btns.setOnClickListener(v-> {
            showProgressBar(true);
            if(!confirms.getText().toString().equals(dbToken)) {
                showProgressBar(false);
                AppUtil.showAlertDialog(this, "Verification","Enter valid verification code","Close");
            }else {
                if(!AppUtil.checkConnection(this)) {
                    showProgressBar(false);
                    AppUtil.showAlertDialog(this, "Internet Error","You are not connected to the internet","Close");
                }else{
                   repSalesConfirmViewModel.pustSalesToServer();
                }
            }
        });

        repSalesConfirmViewModel.observeResponse().observe(this, s -> {

            if(s.equals("200")) {
                Intent intent = new Intent(this,SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else if(s.equals("500")) {
                showProgressBar(false);
                Toast.makeText(this, "Server cant be access", Toast.LENGTH_SHORT).show();
            }
        });

        back_page.setOnClickListener(v-> onBackPressed());
    }

}





