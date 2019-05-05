package com.mobile.mtrader.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.cache.OutletClassCache;
import com.mobile.mtrader.cache.OutletLanguageCache;
import com.mobile.mtrader.cache.OutletTypeCache;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.Pasers;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.CustomerActivityViewmModel;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mobile.mtrader.util.keyStore.ERROR_DIALOG_REQUEST;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ENABLE_GPS;

public class CustomerProfile extends BaseActivity{

    ApplicationComponent component;

    CustomerActivityViewmModel customerActivityViewmModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    Bundle bundle;

    int customerid;

    @BindView(R.id.u_name)
    TextInputEditText u_name;

    @BindView(R.id.u_email)
    TextInputEditText u_email;

    @BindView(R.id.u_paswd)
    TextInputEditText u_paswd;

    @BindView(R.id.u_phone)
    TextInputEditText u_phone;

    ArrayList<String> outletClassList;

    OutletClassCache outletClassCache;

    OutletLanguageCache outletLanguageCache;

    OutletTypeCache outletTypeCache;

    ArrayAdapter<String> arrayAdapter;

    ArrayAdapter<String> arrayAdapterLang;

    ArrayAdapter<String> arrayAdapterType;

    @BindView(R.id.u_group)
    Spinner u_group;

    @BindView(R.id.u_gender)
    Spinner u_gender;

    @BindView(R.id.vehicleType)
    Spinner vehicleType;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.registerBtn)
    Button registerBtn;

    CompositeDisposable cDisposable = new CompositeDisposable();

    private boolean mLocationPermissionGranted = false;

    private FusedLocationProviderClient mFusedLocationClient;

    Pasers pasers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        ButterKnife.bind(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        customerActivityViewmModel = ViewModelProviders.of(this, viewModelFactory).get(CustomerActivityViewmModel.class);
        outletClassCache = new OutletClassCache();
        outletLanguageCache = new OutletLanguageCache();
        outletTypeCache = new OutletTypeCache();
        showProgressBar(true);
        bundle = getIntent().getExtras();
        pasers = new Pasers();

        if (bundle != null) {
            customerid = bundle.getInt("CUSTOMERS_ID_INFO_APPS");
        }

        back_page.setOnClickListener(view->
            onBackPressed()
        );

        registerBtn.setOnClickListener(view -> {
            getLocationPermission();
            editProfile();
        });

        init();
    }


    public void editProfile(){
        String Uname = u_name.getText().toString();
        String Uemail = u_email.getText().toString();
        String Upaswd = u_paswd.getText().toString();
        String Uphone = u_phone.getText().toString();
        int outlet_class_id = outletClassCache.getValueId(u_group.getSelectedItem().toString());
        int outlet_language_id = outletLanguageCache.getValueId(u_gender.getSelectedItem().toString());
        int outlet_type_id = outletTypeCache.getValueId(vehicleType.getSelectedItem().toString());

        if(TextUtils.isEmpty(Uname) || !TextUtils.isDigitsOnly(Uphone) || TextUtils.isEmpty(Uemail) || TextUtils.isEmpty(Upaswd) ||
                TextUtils.isEmpty(Uphone) || Uphone.length()<=9 ) {
            Toast.makeText(this, "Please enter all the fields and enter valid phone number", Toast.LENGTH_SHORT).show();
        }else if (!AppUtil.checkConnection(this)) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }else{
            customerActivityViewmModel.reSetUserProfile(Uname, Uemail, Upaswd, Long.parseLong(Uphone), outlet_class_id, outlet_language_id, outlet_type_id, customerid,  pasers.getLat(), pasers.getLng());
            showProgressBar(true);
        }
    }

    public void init(){
        cDisposable.add(customerActivityViewmModel.getIndividualCustomerProfiles(customerid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    u_name.setText(data.outletname);
                    u_email.setText(data.contactname);
                    u_paswd.setText(data.outletaddress);
                    u_phone.setText(data.contactphone);

                    customerActivityViewmModel.getGroupUserSpinners(1).observe(this, userSpinners -> {
                        outletClassList = new ArrayList<>();
                        if(outletClassCache.size()>0){outletClassCache.clear();}
                        if(userSpinners!=null) {
                            for(int i = 0 ; i < userSpinners.size() ; i++){
                                outletClassList.add(userSpinners.get(i).name);
                                outletClassCache.add(userSpinners.get(i).id, userSpinners.get(i).name);
                            }
                            arrayAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            u_group.setAdapter(arrayAdapter);
                            u_group.setSelection(outletClassCache.getIndexById(Integer.parseInt(data.outletclassid)));
                        }
                    });

                    customerActivityViewmModel.getGroupUserSpinners(2).observe(this, userSpinners -> {
                        outletClassList = new ArrayList<>();
                        if(outletLanguageCache.size()>0){outletLanguageCache.clear();}
                        if(userSpinners!=null) {
                            for(int i = 0 ; i < userSpinners.size() ; i++){
                                outletClassList.add(userSpinners.get(i).name);
                                outletLanguageCache.add(userSpinners.get(i).id, userSpinners.get(i).name);
                            }
                            arrayAdapterLang = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                            arrayAdapterLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            u_gender.setAdapter(arrayAdapterLang);
                            u_gender.setSelection(outletLanguageCache.getIndexById(Integer.parseInt(data.outletlanguageid)));
                        }
                    });

                    customerActivityViewmModel.getGroupUserSpinners(3).observe(this, userSpinners -> {
                        outletClassList = new ArrayList<>();
                        if(outletTypeCache.size()>0){outletTypeCache.clear();}
                        if(userSpinners!=null) {
                            for(int i = 0 ; i < userSpinners.size() ; i++){
                                outletClassList.add(userSpinners.get(i).name);
                                outletTypeCache.add(userSpinners.get(i).id, userSpinners.get(i).name);
                            }
                            arrayAdapterType = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                            arrayAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            vehicleType.setAdapter(arrayAdapterType);
                            vehicleType.setSelection(outletTypeCache.getIndexById(Integer.parseInt(data.outlettypeid)));
                        }
                    });
                    showProgressBar(false);
                },throwable -> {
                    showProgressBar(false);
                })

        );

        customerActivityViewmModel.BroadCastResponse().observe(this, data -> {

            String[] splits = data.split("\\~");
            showProgressBar(false);
            if(splits[0].equals("200")){
                Intent intent = new Intent(getApplication(),CustomerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplication(),splits[1], Toast.LENGTH_SHORT).show();
            }
        });

    }


    //-------Location Tracker require permission--------//


    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (!mLocationPermissionGranted) {
                getLocationPermission();
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location = task.getResult();
                    pasers.setLat(location.getLatitude());
                    pasers.setLng(location.getLongitude());
                }
            }
        });
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getLastLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(!mLocationPermissionGranted){
                    getLocationPermission();
                }
            }
        }

    }
}
