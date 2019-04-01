package com.mobile.mtrader.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobile.mtrader.adapter.SkuAdapter;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.ExposeSalesData;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DailySalesActivity extends AppCompatActivity {

   // @Inject
   // RealmService realmService;

    SkuAdapter skuAdapter;

    //@Inject
    //@ApplicationContext
    public Context mContext;

    //@Inject
    //@ActivityContext
    //public Context activityContext;

    @BindView(R.id.save)
    TextView save;

    @BindView(R.id.back_page)
    ImageView back_page;

    RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.recyler_data)
    RecyclerView recyler_data;

    Intent intent;

    Bundle bundle;

    String rValues;
    String custid;
    String uniquetoken;
    String cName;
    List<ExposeSalesData> entriesList;
    ExposeSalesData dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sales);
        ButterKnife.bind(this);

        /*ApplicationComponent applicationComponent = Apps.get(this).getApplicationComponent();
        skuSalesComponent = DaggerSkuSalesComponent.builder()
                .applicationComponent(applicationComponent)
                .skuActivityContextModule(new SkuActivityContextModule(this))
                .build();
        skuSalesComponent.injectDailySalesActivity(this);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            rValues = bundle.getString("CUSTOMERS_KEYS");
            custid = bundle.getString("CUSTOMER_UNIQUE_ID");
            cName = bundle.getString("CUSTOMER_NAMES_ID");
        }

        entriesList = new ArrayList<>();
        for (int i = 0; i < realmService.getUserSku().size(); i++) {
            dataMap = new ExposeSalesData(
                    realmService.getUserSku().get(i).productname,
                    realmService.getUserSku().get(i).productcode,
                    realmService.getUserSku().get(i).separator,
                    realmService.getUserSku().get(i).soq,
                    realmService.getUserSku().get(i).qty,
                    realmService.getUserSku().get(i).rollprice,
                    realmService.getUserSku().get(i).packprice,
                    "",
                    "",
                    "",
                    "",
                    "",
                    1,
                    1,
                    realmService.getUserSku().get(i).separatorname
            );
            entriesList.add(dataMap);
        }

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyler_data.setLayoutManager(layoutManager);
        skuAdapter = new SkuAdapter(this, entriesList, realmService, Integer.parseInt(custid),  cName);
        recyler_data.setAdapter(skuAdapter);
        recyler_data.setItemViewCacheSize(entriesList.size());

        save.setOnClickListener(v -> {
            realmService.deletePreviousSalesList();
            for (int i = 0; i < entriesList.size(); i++) {
                if (!entriesList.get(i).order.equals("") && !entriesList.get(i).pricing.equals("") && !entriesList.get(i).inventory.equals("")){
                    realmService.setSalesEntriesConverter(
                            entriesList.get(i).getProduct_name(),
                            entriesList.get(i).getProduct_code(),
                            entriesList.get(i).getSeparator(),
                            entriesList.get(i).getSoq(),
                            entriesList.get(i).getQty(),
                            entriesList.get(i).getRollprice(),
                            entriesList.get(i).getPackprice(),
                            entriesList.get(i).getOrder(),
                            entriesList.get(i).getCustomer_id(),
                            entriesList.get(i).getCustomer_name(),
                            entriesList.get(i).getInventory(),
                            entriesList.get(i).getPricing(),
                            entriesList.get(i).getStatus(),
                            entriesList.get(i).getS_status(),
                            entriesList.get(i).seperatorname
                    );
                }
            }

           if (realmService.totalSkuEntered() == 0 ) {
                AppUtil.showAlertDialog(this, "Entries Error", "Please enter SKU'S", "Close");
            } else {
                intent = new Intent(this, ConfirmSales.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("DB_SALES_ENTRY", uniquetoken);
                intent.putExtra("CUSTOMERS_KEYS", rValues);
                startActivity(intent);
            }
        });

        back_page.setOnClickListener(v -> onBackPressed());
        */
    }


}

