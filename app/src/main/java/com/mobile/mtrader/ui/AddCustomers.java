package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.cache.OutletClassCache;
import com.mobile.mtrader.cache.OutletLanguageCache;
import com.mobile.mtrader.cache.OutletTypeCache;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.CustomerActivityViewmModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.jar.Manifest;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;

public class AddCustomers extends BaseActivity {

    ApplicationComponent component;

    CustomerActivityViewmModel customerActivityViewmModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.u_name)
    TextInputEditText u_name;

    @BindView(R.id.u_email)
    TextInputEditText u_email;

    @BindView(R.id.u_paswd)
    TextInputEditText u_paswd;

    @BindView(R.id.u_phone)
    TextInputEditText u_phone;

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

    @BindView(R.id.slap_photo)
    ImageView slap_photo;

    OutletClassCache outletClassCache;

    OutletLanguageCache outletLanguageCache;

    OutletTypeCache outletTypeCache;

    ArrayList<String> outletClassList;

    ArrayAdapter<String> arrayAdapter;

    ArrayAdapter<String> arrayAdapterLang;

    ArrayAdapter<String> arrayAdapterType;

    private static final int CAMERAL_PERMISSION_CODE = 1000;

    private static final int IMAGE_CAPTURE_CODE = 1001;

    Uri image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);
        ButterKnife.bind(this);
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
        init();
    }

    public void init() {

        slap_photo.setOnClickListener(v->{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                if(checkSelfPermission(android.Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED ){
                        //Permission not enable, Request it
                    String[] permission = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission,CAMERAL_PERMISSION_CODE );
                }else{
                    //permission already granted
                    openCamera();
                }
            }else{
                openCamera();
            }

        });

        back_page.setOnClickListener(view -> {
            onBackPressed();
        });

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
            }
        });

        showProgressBar(false);
    }

    private void openCamera() {
        ContentValues values =  new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new Pictures");
        values.put(MediaStore.Images.Media.DESCRIPTION, "from the camera");
        image_url = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_url);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERAL_PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this, "Camera Permission Denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //call when the image was out
        if(resultCode==RESULT_OK) {
            slap_photo.setRotation(90);
            slap_photo.setImageURI(image_url);
        }
    }
}
