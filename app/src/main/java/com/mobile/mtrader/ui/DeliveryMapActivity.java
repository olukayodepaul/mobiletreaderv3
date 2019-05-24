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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.Pasers;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.DeliverySalesMapViewmodel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.mobile.mtrader.util.keyStore.ERROR_DIALOG_REQUEST;
import static com.mobile.mtrader.util.keyStore.MAPVIEW_BUNDLE_KEY;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ENABLE_GPS;


public class DeliveryMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.prosales)
    Button prosales;

    @BindView(R.id.users_name)
    TextView users_name;

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.back_butto)
    Button back_butto;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    Intent intent;

    Bundle bundle;

    String customer_key;

    String customer_no;

    String outletname;

    String outletwaiver;

    String outletlat;

    String outletlng;

    String depotlat;

    String depotlng;

    ApplicationComponent component;

    Pasers pasers;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DeliverySalesMapViewmodel deliveryViewModel;

    private boolean mLocationPermissionGranted = false;

    private GoogleMap googleMap;

    private FusedLocationProviderClient mFusedLocationClient;

    private static final String TAG = "DeliveryMapActivity";

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
        deliveryViewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliverySalesMapViewmodel.class);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        pasers = new Pasers();
        mProgressBar.setVisibility(View.INVISIBLE);

        if (bundle != null) {

            customer_key = bundle.getString("CUSTOMERS_ACCESS_KEYS");
            customer_no = bundle.getString("CUSTOMER_ID");
            outletname = bundle.getString("CUSTOMER_NAME");
            outletwaiver = bundle.getString("OUTLET_WAIVER");

            depotlat = bundle.getString("OUTLET_LAT");
            depotlng = bundle.getString("OUTLET_LNG");

            //destination route
            outletlat = bundle.getString("DEPOT_LAT");
            outletlng = bundle.getString("DEPOT_LNG");

        }

        users_name.setText(outletname);

        back_button.setOnClickListener(v -> {
            onBackPressed();
        });

        //check internet connection in an android.


        back_butto.setOnClickListener(view -> {
            mProgressBar.setVisibility(View.VISIBLE);
            if (!AppUtil.checkConnections(this)) {
                mProgressBar.setVisibility(View.INVISIBLE);
                buildAlertMessageMobileDataOff();
            }else {
                deliveryViewModel.setOutletClose(customer_no,new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        });

        prosales.setOnClickListener(v -> {
            if (!AppUtil.checkConnection(this)) {
                buildAlertMessageMobileDataOff();
            }else {
                deliveryViewModel.updateFromSalesEntries();
                getLocationPermission();
            }
        });

        deliveryViewModel.rpRep().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String data) {
                String dataSplits[] = data.split("\\~");
                if(dataSplits[0].equals("200")) {
                    Intent intent = new Intent(getApplication(),SalesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplication(), dataSplits[1], Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        deliveryViewModel.returnRep().observe(this, data -> {
            String dataSplits[] = data.split("\\~");
            if (dataSplits[0].equals("200")) {
                intent = new Intent(this, DailySalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CUSTOMERS_ACCESS_KEYS", customer_key);
                intent.putExtra("CUSTOMER_ID", customer_no);
                intent.putExtra("GEOLATLNG",pasers.getLat()+"~"+pasers.getLng());
                intent.putExtra("ARRIVAL_TIME",  new SimpleDateFormat("HH:mm:ss").format(new Date()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please clocking to proceed", Toast.LENGTH_SHORT).show();
            }
        });
        initialiseMap(savedInstanceState);
    }

    private void getLastLocation() {
        Log.d(TAG, "getLastLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()) {
                    Location location = task.getResult();
                    pasers.setLat(location.getLatitude());
                    pasers.setLng(location.getLongitude());
                }else{
                    pasers.setLat(0.0);
                    pasers.setLng(0.0);
                }
            }
        });
    }

    private void initialiseMap(Bundle savedInstanceState) {

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (!mLocationPermissionGranted) {
                getLocationPermission();
            } else {
                mMapView.onResume();
            }
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
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

    private void buildAlertMessageMobileDataOff() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires mobile Data to be on?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
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

    public boolean isServicesOK() {
        // Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            //Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            //Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
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
        //Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (!mLocationPermissionGranted) {
                    getLocationPermission();
                }
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap = map;
        //map.setMyLocationEnabled(true);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(outletlat), Double.parseDouble(outletlng))).title(outletname));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(depotlat), Double.parseDouble(depotlng)))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.setMyLocationEnabled(true);
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(outletlat) ,Double.parseDouble(outletlng)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.setOnInfoWindowClickListener(this);

    }


    @Override
    public  void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public  void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.getSnippet().equals("This is you")){
            marker.hideInfoWindow();
        }
        else{

            final AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryMapActivity.this);
            builder.setMessage(marker.getSnippet())
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
     }
    }

/*Location crntLocation=new Location("crntlocation");
            crntLocation.setLatitude(Double.parseDouble(outletlat));
            crntLocation.setLongitude(Double.parseDouble(outletlng));

            Location newLocation=new Location("newLocation");
            newLocation.setLatitude(pasers.getLat());
            newLocation.setLongitude(pasers.getLng());*/

    //distance = crntLocation.distanceTo(newLocation) / 1000;
    //

    //geo fencing using distance W the calculation.
            /*float[] distance = new float[10];
            Location.distanceBetween(pasers.getLat(),pasers.getLng(),Double.parseDouble(outletlat),Double.parseDouble(outletlng),distance);
            float dis = distance[0];*/

    // Toast.makeText(this, Float.toString(dis)+' '+pasers.getLat(), Toast.LENGTH_SHORT).show();
}


