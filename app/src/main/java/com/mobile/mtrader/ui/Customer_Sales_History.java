package com.mobile.mtrader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.mobile.mtrader.adapter.SalesEntryHistoryAdapter;
import com.mobile.mtrader.mobiletreaderv3.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Customer_Sales_History extends AppCompatActivity {

    Bundle extras;

    String timeId;

    @BindView(R.id.sales_history)
    RecyclerView sales_history;

    @BindView(R.id.back_page)
    ImageView back_page;

    //@Inject
    //RealmService realmService;

    RecyclerView.LayoutManager layoutManager;

    SalesEntryHistoryAdapter salesEntryHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__sales__history);
        ButterKnife.bind(this);
        extras = getIntent().getExtras();
        /*ApplicationComponent applicationComponent = Apps.get(this).getApplicationComponent();
        salesHistoryComponent = DaggerSalesHistoryComponent.builder()
                .applicationComponent(applicationComponent)
                .salesActivityContextModule(new SalesActivityContextModule(this))
                .build();
        salesHistoryComponent.injectSalesActivity(this);

        back_page.setOnClickListener(v->{
            onBackPressed();
        });


        if (extras != null) {
            timeId = extras.getString("CUSTOMER_ID");
        }

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sales_history.setLayoutManager(layoutManager);
        salesEntryHistoryAdapter = new SalesEntryHistoryAdapter(this, realmService, timeId );
        sales_history.setAdapter(salesEntryHistoryAdapter);
        */
    }

}
