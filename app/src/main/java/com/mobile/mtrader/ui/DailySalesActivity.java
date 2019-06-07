package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.SkuAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.DailySalesViewModule;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DailySalesActivity extends BaseActivity {


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

    String latlng;

    String arrTime;

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
        showProgressBar(true);

        if (bundle != null) {
            customer_key = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customer_no = bundle.getString("CUSTOMER_ID");
            latlng = bundle.getString("GEOLATLNG");
            arrTime = bundle.getString("ARRIVAL_TIME");
        }

        dailySalesViewModule.mapSoqToProducts(customer_no);

        dailySalesViewModule.getviewCom().observe(this, check -> {
            if (check.equals("200")) {

                mDis.add(dailySalesViewModule.getLiveCustomers()
                        .delaySubscription(5, TimeUnit.MINUTES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {

                                    recyler_data.setLayoutManager(new LinearLayoutManager(this));
                                    recyler_data.setHasFixedSize(true);
                                    skuAdapter = new SkuAdapter(this, customer_no);
                                    Log.e("mapper", "----------------" + "in");
                                    recyler_data.setAdapter(skuAdapter);

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
                                                            "",
                                                            data.get(position).productcode
                                                    );
                                                } else {

                                                    String dates = new SimpleDateFormat("HH:mm:ss").format(new Date());

                                                    mDis.add(
                                                            dailySalesViewModule.getUsersIndividualInformation()
                                                                    .subscribeOn(Schedulers.io())
                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribe(emData -> {
                                                                        updateSalesEntries(
                                                                                emData.user_id,
                                                                                data.get(position).separator,
                                                                                data.get(position).separatorname,
                                                                                data.get(position).rollprice,
                                                                                data.get(position).packprice,
                                                                                getInventory.getText().toString(),
                                                                                getPricing.getText().toString(),
                                                                                getOrder.getText().toString(),
                                                                                customer_no,
                                                                                "1",
                                                                                dates,
                                                                                data.get(position).soq,
                                                                                data.get(position).productcode);
                                                                    })
                                                    );
                                                }
                                            }
                                        }
                                    });
                                    skuAdapter.notifyDataSetChanged();
                                    skuAdapter.setModulesAdapter(data);
                                    recyler_data.setItemViewCacheSize(data.size());
                                    showProgressBar(false);
                                },
                                throwable -> Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.getMessage())
                        )
                );
            }
        });

        save.setOnClickListener(v -> {
            mDis.add(dailySalesViewModule.validateUserSalesEntries("1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {

                                mDis.add(dailySalesViewModule.countAllSalesEntries()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(allData -> {

                                            if (!allData.equals(data)) {
                                                Toast.makeText(getApplication(), "Please enter all the field", Toast.LENGTH_SHORT).show();
                                            } else {
                                                intent = new Intent(this, ConfirmSales.class);
                                                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("CUSTOMERS_ACCESS_KEYS", customer_key);
                                                intent.putExtra("CUSTOMER_NO", customer_no);
                                                intent.putExtra("GEOLATLNG", latlng);
                                                intent.putExtra("ARRIVAL_TIME", arrTime);
                                                startActivity(intent);
                                            }
                                        }, throwable -> {
                                            Toast.makeText(getApplication(), "Please enter sales", Toast.LENGTH_SHORT).show();
                                        })
                                );
                            })
            );
        });

        back_page.setOnClickListener(v -> onBackPressed());
    }

    public void updateSalesEntries(int user_id, String separator, String separatorname, String rollprice, String packprice,
                                   String inventory, String pricing, String orders,
                                   String customerno, String updatestatus, String entry_date_time, String soq, String productcode) {
        dailySalesViewModule.updateSalesEntries(
                user_id, separator, separatorname, rollprice, packprice,
                inventory, pricing, orders,
                customerno, updatestatus, entry_date_time, soq, productcode
        );
    }
}