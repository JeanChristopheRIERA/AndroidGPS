package com.example.gps;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;

/**
 * Display a googleMap and listen to GPS sensor
 * A GPSFragment manage the location module (GPS°
 * A NavigationFragment control the user
 * This activity implements IGPSActivity, so it ca moveCamera() to centre map to GPS location
 */
public class MapsActivity extends FragmentActivity implements IGPSActivity, OnMapReadyCallback {
    private GPSFragment gpsFrangment;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsFrangment = (GPSFragment)getSupportFragmentManager().findFragmentById(R.id.gpsLocation);
        if (gpsFrangment == null){
            gpsFrangment = new GPSFragment(this);
            FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
            gpsTransaction.replace(R.id.gpsLocation, gpsFrangment);
            gpsTransaction.addToBackStack(null);
            gpsTransaction.commit();
        }

        NavigationFrangment navigationFrangment = (NavigationFrangment) getSupportFragmentManager().findFragmentById(R.id.navigation);
        if (navigationFrangment == null){
            navigationFrangment = new NavigationFrangment();
            FragmentTransaction navTransaction = getSupportFragmentManager().beginTransaction();
            navTransaction.replace(R.id.navigation, navigationFrangment);
            navTransaction.addToBackStack(null);
            navTransaction.commit();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {mMap = googleMap;}

    @Override
    public void moveCamera() {
       try {
           gpsFrangment.setPlaceName("ville" + gpsFrangment.getPlaceName());
       }catch (IOException e){
           gpsFrangment.setPlaceName("Ville icconnue" );
       }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsFrangment.getPosition(), 15f));
    }

    @Override
    public  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantedResults){
        switch (requestCode){
            case REQUEST_CODE :{
                if(grantedResults.length > 0 && grantedResults [0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE_LOCATION granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }break;
        }
        gpsFrangment = new GPSFragment(this);
        FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
        gpsTransaction.replace(R.id.gpsLocation, gpsFrangment);
        gpsTransaction.addToBackStack(null);
        gpsTransaction.commit();

    }
}