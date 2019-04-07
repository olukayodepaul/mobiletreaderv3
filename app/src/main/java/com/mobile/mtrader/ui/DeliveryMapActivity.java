package com.mobile.mtrader.ui;

import com.google.android.gms.location.LocationListener;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;
import com.mobile.mtrader.viewmodels.DeliverySalesMapViewmodel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.prosales)
    Button prosales;

    @BindView(R.id.users_name)
    TextView users_name;

    Intent intent;

    Context context;

    Bundle bundle;

    String customer_key;
    String customer_no;
    String outletname;
    String outletwaiver;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DeliverySalesMapViewmodel deliveryViewModel;

   // private GeofencingClient geofencingClient;

   /* private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_map);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        deliveryViewModel = ViewModelProviders.of(this,viewModelFactory).get(DeliverySalesMapViewmodel.class);


        if (bundle != null) {
            customer_key = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customer_no = bundle.getString("CUSTOMER_ID");
            outletname = bundle.getString("CUSTOMER_NAME");
            outletwaiver = bundle.getString("OUTLET_WAIVER");
        }

        back_button.setOnClickListener(v -> {
            onBackPressed();
        });


        prosales.setOnClickListener(v -> {
            deliveryViewModel.reInitialisProduct("",0,"","","");
        });

        deliveryViewModel.returnRep().observe(this, data->{
            if(data.equals("200")){
                intent = new Intent(this, DailySalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CUSTOMERS_ACCESS_KEYS", customer_key);
                intent.putExtra("CUSTOMER_ID", customer_no);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}



/*
   @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       // Add a marker in Sydney, Australia, and move the camera.
       LatLng sydney = new LatLng(-34, 151);
       mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       /*mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);

    }


    protected synchronized void buildGoogleApiClient(){

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    */
