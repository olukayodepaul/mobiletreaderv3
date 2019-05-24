package com.mobile.mtrader.ui;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.ClockInAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.ClockInViewModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DepotClokingActivity extends BaseActivity {

    ClockInAdapter clockInAdapter;

    @BindView(R.id.user_baskets)
    RecyclerView user_baskets;

    @BindView(R.id.totalitems)
    TextView totalitems;

    @BindView(R.id.totalqty)
    TextView totalqty;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.clockout)
    Button clockout;

    @BindView(R.id.resume)
    Button resume;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ClockInViewModel clockInViewModel;

    DecimalFormat myFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_cloking);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        clockInViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClockInViewModel.class);

        showProgressBar(true);

        myFormatter = new DecimalFormat("#,###.00");

        back_page.setOnClickListener(v->onBackPressed());

        resume.setOnClickListener(v->{
            showProgressBar(true);
            if(!AppUtil.checkConnection(this)) {
                showProgressBar(false);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressBar(true);
                clockInViewModel.dailyRoster();
            }
        });

        clockout.setOnClickListener(view -> {
            if(!AppUtil.checkConnection(this)) {
                showProgressBar(false);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressBar(true);
                clockInViewModel.dailyClockOut();
            }
        });


        user_baskets.setLayoutManager(new LinearLayoutManager(this));
        user_baskets.setHasFixedSize(true);
        clockInAdapter = new ClockInAdapter(this);
        user_baskets.setAdapter(clockInAdapter);
        observeAll();
    }

    public void observeAll() {

        clockInViewModel.setBasket("1").observe(this, products -> {

                    showProgressBar(false);
                    double qty = 0.0;
                    for (int i = 0; i < products.size() ; i++) {
                        qty += Double.parseDouble(products.get(i).qty);
                    }
                    totalqty.setText(myFormatter.format(qty));
                    clockInAdapter.setModulesAdapter(products);
                }
        );

        clockInViewModel.getObserveResponse().observe(this, s -> {
            showProgressBar(false);
            String[] res = s.split("\\~");
            if(Integer.parseInt(res[0])==200){
                clockInViewModel.insertLastLocation();
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
