package com.mobile.mtrader.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.mobile.mtrader.adapter.ConfirmSalesAdapter;
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
import java.util.UUID;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ConfirmSales extends AppCompatActivity {

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

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

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
        progressbar.setVisibility(View.GONE);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            dbToken = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customerno = bundle.getString("CUSTOMER_NO");
        }

        saleValues.setLayoutManager(new LinearLayoutManager(this));
        saleValues.setHasFixedSize(true);
        confirmSalesAdapter = new ConfirmSalesAdapter(this);
        saleValues.setAdapter(confirmSalesAdapter);

        mDis.add(repSalesConfirmViewModel.salesEnteryRecord("1", customerno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            int sumAllPP = 0;
                            int sumAllRP = 0;
                            double sumInventory = 0.0;
                            int sumPricing = 0;
                            double sumOrder = 0.0;

                            for (int i = 0; i < data.size(); i++) {

                                String[] divider = Double.toString(Double.parseDouble(data.get(i).orders)).split("\\.");
                                sumAllPP += Integer.parseInt(divider[0]) * Double.parseDouble(data.get(i).packprice);
                                sumAllRP += Integer.parseInt(divider[1]) * Double.parseDouble(data.get(i).rollprice);
                                sumInventory += Double.parseDouble(data.get(i).inventory);
                                sumPricing += data.get(i).pricing;
                                sumOrder += Double.parseDouble(data.get(i).orders);
                            }
                            invent_t.setText(Double.toString(sumOrder));
                            app_price_t.setText(Integer.toString(sumPricing));
                            order_t.setText(Double.toString(sumInventory));
                            tv_price_t.setText(Double.toString(sumAllPP + sumAllRP));
                            confirmSalesAdapter.setModulesAdapter(data);
                        },
                        throwable -> Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.getMessage())
                )
        );

        btns.setOnClickListener(v-> {
            if(!confirms.getText().toString().equals(dbToken)) {
                AppUtil.showAlertDialog(this, "Verification","Enter valid verification code","Close");
            }else{
                if(!AppUtil.checkConnection(this)) {
                    AppUtil.showAlertDialog(this, "Internet Error","You are not connected to the internet","Close");
                }else{
                    repSalesConfirmViewModel.pustSalesToServer("1",customerno,1168,
                            new SimpleDateFormat("HH:mm:ss").format(new Date()), "open", "00.00", "00.00",
                            UUID.randomUUID().toString());
                }
            }
        });

        repSalesConfirmViewModel.observeResponse().observe(this, s -> {

            if(s.equals("501")){
                progressbar.setVisibility(View.VISIBLE);
            }else if(s.equals("200")){
                Intent intent = new Intent(this,SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else if(s.equals("500")){
                Toast.makeText(this, "Server cant be access", Toast.LENGTH_SHORT).show();
            }
        });

        back_page.setOnClickListener(v-> onBackPressed());
    }

}





