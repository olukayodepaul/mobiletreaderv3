package com.mobile.mtrader.ui;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.CustomerActivityViewmModel;
import com.pkmmte.view.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ENABLE_GPS;

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
    CircularImageView slap_photo;

    @BindView(R.id.iv_camera)
    CircularImageView iv_camera;


    OutletClassCache outletClassCache;

    OutletLanguageCache outletLanguageCache;

    OutletTypeCache outletTypeCache;

    ArrayList<String> outletClassList;

    ArrayAdapter<String> arrayAdapter;

    ArrayAdapter<String> arrayAdapterLang;

    ArrayAdapter<String> arrayAdapterType;

    private static final int CAMERAL_PERMISSION_CODE = 1000;

    private static final int IMAGE_CAPTURE_CODE = 100;

    Uri fileUri;

    String picturePath;

    Uri selectedImage;

    Bitmap photo;

    String mPhoto = "";

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
        showProgressBar(false);
        init();
    }

    public void init() {

        //open camera
        iv_camera.setOnClickListener(v->{
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

        registerBtn.setOnClickListener(view->{

            String custname = u_name.getText().toString();
            String contactname = u_email.getText().toString();
            String address = u_paswd.getText().toString();
            String phoneno = u_phone.getText().toString();
            int outlet_class_id = outletClassCache.getValueId(u_group.getSelectedItem().toString());
            int outlet_language_id = outletLanguageCache.getValueId(u_gender.getSelectedItem().toString());
            int outlet_type_id = outletTypeCache.getValueId(vehicleType.getSelectedItem().toString());

            Log.e("check_input", "----------------" + outlet_type_id);

            if(TextUtils.isEmpty(custname) || !TextUtils.isDigitsOnly(phoneno) || TextUtils.isEmpty(address) || TextUtils.isEmpty(contactname) ||
                    TextUtils.isEmpty(contactname) || contactname.length()<=9){
                Toast.makeText(this, "Please enter all the fields and enter valid phone number", Toast.LENGTH_SHORT).show();
            }else if (!AppUtil.checkConnection(this)) {
                //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                buildAlertMessageMobileDataOff();
            }else{
                showProgressBar(true);
                uploadImage();
                //customerActivityViewmModel.mapOutlet(custname,contactname,address,ba1, );
            }
        });

        customerActivityViewmModel.getGroupUserSpinners(1).observe(this, userSpinners -> {
            outletClassList = new ArrayList<>();
            if(outletClassCache.size()>0){
                outletClassCache.clear();
            }
            if(userSpinners!=null) {
                for(int i = 0 ; i < userSpinners.size() ; i++) {
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

    private void openCamera() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, IMAGE_CAPTURE_CODE);
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    //real image to be move to the server with retrofit.
    private void uploadImage() {
        Log.e("path", "----------------" + picturePath);
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        mPhoto = Base64.encodeToString(ba, Base64.DEFAULT); //Base64.encodeToString(ba, Base64.NO_WRAP);
        Log.e("base64", "-----" + mPhoto);
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
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap photo = (Bitmap) data.getExtras().get("data");

            slap_photo.setImageBitmap(photo);
        }
    }


    private void buildAlertMessageMobileDataOff() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires mobile Data to be on?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
