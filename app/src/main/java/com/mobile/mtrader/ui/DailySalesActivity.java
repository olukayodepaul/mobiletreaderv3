package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mtrader.adapter.SkuAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.DailySalesViewModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DailySalesActivity extends AppCompatActivity {


    ApplicationComponent component;

    SkuAdapter skuAdapter;

    @BindView(R.id.save)
    TextView save;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.recyler_data)
    RecyclerView recyler_data;

    Bundle bundle;
    String customer_key, customer_no;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DailySalesViewModule dailySalesViewModule;

    CompositeDisposable mDis = new CompositeDisposable();

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sales);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        dailySalesViewModule = ViewModelProviders.of(this, viewModelFactory).get(DailySalesViewModule.class);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            customer_key = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customer_no = bundle.getString("CUSTOMER_ID");
        }

        recyler_data.setLayoutManager(new LinearLayoutManager(this));
        recyler_data.setHasFixedSize(true);
        skuAdapter = new SkuAdapter(this, customer_no);
        recyler_data.setAdapter(skuAdapter);


        mDis.add(dailySalesViewModule.getLiveCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            skuAdapter.setOnItemClickListener(position -> {

                                SkuAdapter.ViewHolder holder = (SkuAdapter.ViewHolder) recyler_data
                                        .findViewHolderForLayoutPosition(position);

                                EditText getInventory = holder.itemView.findViewById(R.id.editTextq);
                                EditText getPricing = holder.itemView.findViewById(R.id.editText2q);
                                EditText getOrder = holder.itemView.findViewById(R.id.editText3q);

                                if (getInventory.getText().toString().equals(".")) {
                                    getInventory.setText("");
                                } else if (getOrder.getText().toString().equals(".")) {
                                    getOrder.setText("");
                                } else {

                                    double salesQty = 0.0;
                                    if (!getOrder.getText().toString().equals("")) {
                                        salesQty = Double.parseDouble(getOrder.getText().toString());
                                    }

                                    if (Double.parseDouble(data.get(position).qty) < salesQty) {
                                        getOrder.setText("");
                                    } else {
                                        if (TextUtils.isEmpty(getInventory.getText().toString()) ||
                                                TextUtils.isEmpty(getPricing.getText().toString()) ||
                                                TextUtils.isEmpty(getOrder.getText().toString())
                                                ) {
                                            updateSalesEntries(
                                                    0,
                                                    "0",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    data.get(position).productcode);
                                        } else {
                                            updateSalesEntries(
                                                    0,
                                                    data.get(position).separator,
                                                    data.get(position).separatorname,
                                                    data.get(position).rollprice,
                                                    data.get(position).packprice,
                                                    "",
                                                    "",
                                                    "",
                                                    customer_no,
                                                    "",
                                                    "",
                                                    data.get(position).productcode);
                                        }
                                    }
                                }
                            });
                            skuAdapter.notifyDataSetChanged();
                            skuAdapter.setModulesAdapter(data);
                            recyler_data.setItemViewCacheSize(data.size());
                        },
                        throwable -> Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.getMessage())

                )
        );

        save.setOnClickListener(v->{
            mDis.add(dailySalesViewModule.validateUserSalesEntries("1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data->{
                                if(data == 0){
                                    Toast.makeText(getApplication(),"Please enter sales",Toast.LENGTH_SHORT).show();
                                }else{
                                    intent = new Intent(this, ConfirmSales.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("CUSTOMERS_ACCESS_KEYS", customer_key);
                                    intent.putExtra("CUSTOMER_NO", customer_no);
                                    startActivity(intent);
                                }
                            })
            );
        });

        back_page.setOnClickListener(v -> onBackPressed());
    }

    public void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                                   String inventory, String pricing, String orders,
                                   String customerno, String updatestatus, String entry_date_time,String productcode) {
        dailySalesViewModule.updateSalesEntries(
        user_id, separator, separatorname, rollprice, packprice,
                inventory, pricing, orders,
                customerno, updatestatus, entry_date_time, productcode
        );
    }
}