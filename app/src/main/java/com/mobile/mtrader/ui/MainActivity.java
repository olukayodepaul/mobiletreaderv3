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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.di.module.PicassoModule;
import com.mobile.mtrader.di.module.RetrofitModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.Pasers;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


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
    Pasers pasers;
    String usersName;
    TelephonyManager telephonyManager;
    int READ_PHONE_STATE_REQUEST = 0;

    CompositeDisposable mDis = new CompositeDisposable();

    private final static String TAG = MainActivity.class.getSimpleName();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

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
        pasers = new Pasers();

        progressbar.setVisibility(View.GONE);
        loginViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);

        loginButtons.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST);
            }else{

                telephonyManager  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                usersName = u_email.getText().toString();
                String userPassword = u_paswd.getText().toString();

                if(!AppUtil.checkConnection(this)){
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(usersName) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }else{
                    loginViewModel.getInitCount().observe(this, avail-> {
                        showProgressDialog();
                        if(avail==0){
                            loginViewModel.setEmployeesDailySalesData(usersName,userPassword,imei);
                        }else{
                            pasers.setUsersname(usersName);
                            pasers.setImei(imei);
                            pasers.setPasssord(userPassword);
                            loginViewModel.getEmployesProfiles();
                        }
                    });
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

        loginViewModel.emDetails().observe(this, rep-> {
            if(!rep.mdate.equals(date)) {
                intent = new Intent(this,ModuleActivity.class);
                startActivity(intent);
                finish();
            }else{
                loginViewModel.setEmployeesDailySalesData(pasers.getUsersname(), pasers.getPasssord(), pasers.getImei());
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

