package com.mobile.mtrader.ui;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

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


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    OutletClassCache outletClassCache;

    OutletLanguageCache outletLanguageCache;

    OutletTypeCache outletTypeCache;

    ArrayList<String> outletClassList;

    ArrayAdapter<String> arrayAdapter;

    ArrayAdapter<String> arrayAdapterLang;

    ArrayAdapter<String> arrayAdapterType;

    BottomSheetDialog bottomSheetDialog;

    private static final int REQUEST_CODE = 1001;

    private boolean mPermissions;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String TAG = "AddCustomers";

    static final int REQUEST_TAKE_PHOTO = 1;

    String imageFilePath;

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
        bottomSheetDialog = new BottomSheetDialog(this);
        showProgressBar(false);
        eventInit();
        spinnerInit();
        cameraInit();
    }

    public void cameraInit() {
        if(mPermissions){
            if(!checkCameraHardware(this)) {
                showSnackBar("You need a camera to use this application", Snackbar.LENGTH_INDEFINITE);
            }
        }
        else{
            verifyPermissions();
        }
    }


    public void eventInit(){
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.botton_sheet_dialog_photo, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);

        //open camera
       /* iv_camera.setOnClickListener(v->{
            openCameraIntent();
            //bottomSheetDialog.show();
        });*/

        back_page.setOnClickListener(view -> {
            onBackPressed();
        });

        registerBtn.setOnClickListener(view->{

            String custname = u_name.getText().toString();
            String contactname = u_email.getText().toString();
            String address = u_paswd.getText().toString();
            String phoneno = u_phone.getText().toString();
            int outlet_class_id = outletClassCache.getValueId(u_group.getSelectedItem().toString());
            int outlet_language_id = outletLanguageCache.getValueId(u_gender.getSelectedItem().toString());
            int outlet_type_id = outletTypeCache.getValueId(vehicleType.getSelectedItem().toString());


            /*if(TextUtils.isEmpty(custname) || !TextUtils.isDigitsOnly(phoneno) || TextUtils.isEmpty(address) || TextUtils.isEmpty(contactname) ||
                    TextUtils.isEmpty(contactname) || contactname.length()<=9){
                Toast.makeText(this, "Please enter all the fields and enter valid phone number", Toast.LENGTH_SHORT).show();
            }else if (!AppUtil.checkConnection(this)) {
                //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                buildAlertMessageMobileDataOff();
            }else{
                showProgressBar(true);
                uploadImage();
                customerActivityViewmModel.mapOutlet(custname,contactname,address,
                        phoneno,outlet_class_id,outlet_language_id,
                        1,outlet_type_id,mPhoto);
            }*/
            showProgressBar(true);

        });
    }

    public void spinnerInit() {

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
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public void verifyPermissions() {
        String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0] ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1] ) == PackageManager.PERMISSION_GRANTED) {
            mPermissions = true;
            cameraInit();
        } else {
            ActivityCompat.requestPermissions(AddCustomers.this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE) {
            if(mPermissions){
                cameraInit();
            }
            else{
                verifyPermissions();
            }
        }
    }

    private void showSnackBar(final String text, final int length) {
        View view = this.findViewById(android.R.id.content).getRootView();
        Snackbar.make(view, text, length).show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  ".jpg",storageDir);
        imageFilePath = image.getAbsolutePath();
        Log.d(TAG, "onCreate: "+imageFilePath);
        return image;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                pictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(pictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Glide.with(AddCustomers.this).load(imageFilePath).into(slap_photo);
            Log.d(TAG, "onCreate: "+"NO");
        }
    }
}
