package com.mobile.mtrader.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.firebasemodel.UserLocation;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.u_email)
    EditText u_email;

    @BindView(R.id.u_paswd)
    EditText u_paswd;

    @BindView(R.id.loginButton)
    Button loginButtons;

    ApplicationComponent component;

    LoginViewModel loginViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    TelephonyManager telephonyManager;

    int READ_PHONE_STATE_REQUEST = 0;

    Intent intent;

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        showProgressBar(false);
        mAuth = FirebaseAuth.getInstance();

        loginButtons.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST);
            }else if (!AppUtil.checkConnections(this)) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {
                showProgressBar(true);
                telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                loginViewModel.processLogin(u_email.getText().toString(), u_paswd.getText().toString(), imei,
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()), mAuth);
            }

        });

        loginViewModel.callBallAllResponse().observe(this, data -> {

            showProgressBar(false);

            String[] transData = data.split("\\~");

            if (transData[0].equals("1")) {
                Toast.makeText(this, transData[1], Toast.LENGTH_SHORT).show();
            }else if(transData[0].equals("3")){
                intent = new Intent(this,ModuleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}