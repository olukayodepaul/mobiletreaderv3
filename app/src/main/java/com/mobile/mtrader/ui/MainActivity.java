package com.mobile.mtrader.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.di.module.PicassoModule;
import com.mobile.mtrader.di.module.RetrofitModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.u_email)
    EditText u_email;

    @BindView(R.id.u_paswd)
    EditText u_paswd;

    @BindView(R.id.loginButton)
    Button loginButtons;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    Intent intent;

    ApplicationComponent component;

    LoginViewModel loginViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    String imei;
    TelephonyManager telephonyManager;
    int READ_PHONE_STATE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);

        progressbar.setVisibility(View.GONE);
        loginViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);


        loginButtons.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST);
            }else{

                telephonyManager  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                String usersName = u_email.getText().toString();
                String userPassword = u_paswd.getText().toString();

                if(!AppUtil.checkConnection(this)){
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(usersName) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }else{
                    showProgressDialog();
                    loginViewModel.setEmployeesDailySalesData(usersName,userPassword,imei);
                }
            }
        });

        loginViewModel.getNetRes().observe(this, s -> {

            hideProgressDialog();
            String[] res = s.split("\\~");

            if(Integer.parseInt(res[0])==200){

                intent = new Intent(this,ModuleActivity.class);
                startActivity(intent);
                finish();

            }else if(Integer.parseInt(res[0])==404){
                Toast.makeText(this, res[1], Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show();
            }

        });

        loginViewModel.getThrowable().observe(this, throwable -> {
            hideProgressDialog();
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public void showProgressDialog() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        progressbar.setVisibility(View.GONE);
    }

}

