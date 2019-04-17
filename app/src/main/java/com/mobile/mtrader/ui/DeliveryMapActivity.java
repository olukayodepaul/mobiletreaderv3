package com.mobile.mtrader.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.DeviceLocation;
import com.mobile.mtrader.viewmodels.DeliverySalesMapViewmodel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.mobile.mtrader.util.keyStore.ERROR_DIALOG_REQUEST;
import static com.mobile.mtrader.util.keyStore.MAPVIEW_BUNDLE_KEY;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.mobile.mtrader.util.keyStore.PERMISSIONS_REQUEST_ENABLE_GPS;


public class DeliveryMapActivity extends FragmentActivity implements  RoutingListener /*, OnMapReadyCallback*/{

    private GoogleMap mMap;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.prosales)
    Button prosales;

    @BindView(R.id.users_name)
    TextView users_name;

    @BindView(R.id.map)
    MapView mMapView;

    Intent intent;

    Context context;

    Bundle bundle;

    String customer_key;
    String customer_no;
    String outletname;
    String outletwaiver;

    DeviceLocation deviceLocation;

    private GoogleMap googleMap;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DeliverySalesMapViewmodel deliveryViewModel;
    private final static String TAG = MainActivity.class.getSimpleName();

    private boolean mLocationPermissionGranted = false;

    private MarkerOptions place1, place2;

    private FusedLocationProviderClient mFusedLocationClient;

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark,R.color.primary,R.color.bg_blue,R.color.accent,R.color.primary_dark_material_light};


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
        polylines = new ArrayList<>();


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

            //getLastKnownLocation();
            //Log.i("control_this", Double.toString(deviceLocation.getLng())+" "+Double.toString(deviceLocation.lat));
            //Toast.makeText(this, Double.toString(deviceLocation.getLat())+" "+Double.toString(deviceLocation.getLng()),Toast.LENGTH_LONG).show();
            deliveryViewModel.reInitialisProduct("", 0, "", "", "");

        });

        deliveryViewModel.returnRep().observe(this, data -> {
            if (data.equals("200")) {
                intent = new Intent(this, DailySalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CUSTOMERS_ACCESS_KEYS", customer_key);
                intent.putExtra("CUSTOMER_ID", customer_no);
                startActivity(intent);
            }
        });
        initGoogleMap(savedInstanceState);
    }

    private void initGoogleMap(Bundle saveInstanceState) {
        Bundle mapViewBundle = null;
        if (saveInstanceState != null) {
            mapViewBundle = saveInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        //mMapView.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted == true) {
                mMapView.onResume();
            } else {
                getLocationPermission();
            }
        }
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

    /*@Override
    public void onMapReady(GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap = map;

        googleMap.addMarker(place2);

        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);

        //map.addMarker(new MarkerOptions().position(latLng).title(outletname));
        googleMap.setMyLocationEnabled(true);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(6.4627883 ,3.5578067));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }
    */


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    //use this for geofencing
    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if(location!=null){
                deviceLocation = new DeviceLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private boolean checkMapServices() {
        if(isServicesOK()) {
            if(isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    //Check if GPS is enable on the device
    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //check the startActivityForResult to see if the user enable the gps
    //if no then call getLocationPermission() to re-initiate the dialog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(!mLocationPermissionGranted) {
                    getLocationPermission();
                }
            }
        }
    }

    //check location permission if it is enable.
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //check if google play service is install on the device
    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS) {
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
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

    private void getRouteToMakker(){

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(new LatLng(6.4627866, 3.5578000), new LatLng(6.1627883 ,3.5578067))
                .build();
        routing.execute();
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route  , int shortestRouteIndex) {

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(6.5627883 ,3.5578067));
        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.));
        googleMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(new LatLng(6.4627866, 3.5578000));
        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        googleMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }
}


