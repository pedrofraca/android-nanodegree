package io.github.pedrofraca.babydiary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.github.pedrofraca.babydiary.R;

/**
 * Created by pedrofraca on 21/05/16.
 */
public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    private static int REQUEST_LOCATION_PERMISSIONS=1;
    public static String RESULT_DATA_LOCATION="result_data";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng mCurrentLocation;

    public static Intent newIntent(Activity caller){
        return new Intent(caller,SelectLocationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.use_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra(RESULT_DATA_LOCATION,mCurrentLocation);
                setResult(RESULT_OK,resultData);
                finish();
            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        centerMapInCurrentLocation();
    }

    private void centerMapInCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mCurrentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            centerInMapAndAddPin(mCurrentLocation);

        }
    }

    private void centerInMapAndAddPin(LatLng currentLocation) {
        mCurrentLocation=currentLocation;
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation
                , 12.0f));
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Sydney"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean denied=false;
        if(requestCode==REQUEST_LOCATION_PERMISSIONS){
            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED) {
                    denied=true;
                }
            }
        }

        if(!denied){
            centerMapInCurrentLocation();
        } else {

        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        centerInMapAndAddPin(latLng);
    }
}