package com.mobile.mtrader.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mobile.mtrader.adapter.ConfirmSalesAdapter;
import com.mobile.mtrader.frameworks.confirmsku.ConfirmSkuContract;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.util.AppUtil;
import java.text.DecimalFormat;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ConfirmSales extends AppCompatActivity implements ConfirmSkuContract.ConfirmView {

    @BindView(R.id.back_page)
    ImageView back_page;

    Bundle bundle;

    String dbToken, custTokenKey;

    @BindView(R.id.saleValues)
    RecyclerView saleValues;

    @Inject
    RealmService realmService;

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

    //@Inject
    //ConfirmSkuImp mView;

    Intent intent;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    RecyclerView.LayoutManager layoutManager;

    ConfirmSalesAdapter confirmSalesAdapter;

    DecimalFormat myFormatter;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sales);
        ButterKnife.bind(this);
        /*ApplicationComponent applicationComponent = Apps.get(this).getApplicationComponent();
        confirmSalesComponent = DaggerConfirmSalesComponent.builder()
                .applicationComponent(applicationComponent)
                .confirmSalesActivityContextModule(new ConfirmSalesActivityContextModule(this))
                .confirmActivityPreModule(new ConfirmActivityPreModule(this))
                .build();
        confirmSalesComponent.injectModuleConfirm(this);
        myFormatter = new DecimalFormat("#,###");
        progressbar.setVisibility(View.GONE);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            dbToken = bundle.getString("DB_SALES_ENTRY");
            custTokenKey = bundle.getString("CUSTOMERS_KEYS");
        }

        order_t.setText(realmService.totalInvertoryEntries());
        app_price_t.setText(realmService.totalPricingEntries());
        invent_t.setText(realmService.totalOrderEntries());
        long totalPrice = Math.round((int) realmService.totalAmount());
        tv_price_t.setText(myFormatter.format(totalPrice));

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        saleValues.setLayoutManager(layoutManager);
        confirmSalesAdapter = new ConfirmSalesAdapter(this, realmService);
        saleValues.setAdapter(confirmSalesAdapter);

        btns.setOnClickListener(v->{
            if(!confirms.getText().toString().equals(custTokenKey)) {
                AppUtil.showAlertDialog(this, "Verification","Enter valid verification code","Close");
            }else{
                if(!AppUtil.checkConnection(this)) {
                    mView.pushDataToSever("1");
                }else{
                    mView.pushDataToSever("2");
                }
            }
        });
        back_page.setOnClickListener(v-> onBackPressed());
        */
    }


    @Override
    public void showError(String message, String title, String buttons) {
        progressbar.setVisibility(View.GONE);
        AppUtil.showAlertDialogWithIntent(this, title,message,buttons,SalesActivity.class);
    }

    @Override
    public void showProgressDialog() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showComplete() {

    }
}




