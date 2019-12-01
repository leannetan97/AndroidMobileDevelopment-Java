package com.layyan.mapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private int proximityRadius = 5000;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int REQUEST_USER_LOCATION_CODE = 99;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.ET_search).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            onClick(findViewById(R.id.IV_search));
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User's Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentUserLocationMarker = mMap.addMarker((markerOptions));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,
                    (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //    shouldShowRequestPermissionRationale will return true only if the application was
    //    launched earlier and the user "denied" the permission WITHOUT checking "never ask again".
//    In other cases (app launched first time, or the app launched earlier too and the user
//    denied permission by checking "never ask again"), the return value is false.
    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) { // this is checking IS THE APP
                // LAUNCHED 1st Time? YES = return false, else Check is user tick DENY? DENY tick
                // = return true,
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_USER_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_USER_LOCATION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission,
                                           @NonNull int[] grantResult) {
        switch (requestCode) {
            case REQUEST_USER_LOCATION_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    if (googleApiClient == null) {
                        buildGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient =
                new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    public void onClick(View view) {
        String hospital = "hospital";
        String school = "school";
        String restaurant = "restaurant";

        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        switch (view.getId()) {
            case R.id.IV_search:
                mMap.clear();
                hideSoftKeyboard(this);
                EditText etSearch = findViewById(R.id.ET_search);
                String address = etSearch.getText().toString();
                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();
                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);
//                    Geocoder is used to transform the (lat, lng) into address.
                    try {
                        addressList = geocoder.getFromLocationName(address, 6);
                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Log.d("address", addressList.get(i).toString());
                                System.out.println(addressList.get(i).toString());
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(),
                                        userAddress.getLongitude());
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                mMap.addMarker(userMarkerOptions);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                            }
                        } else {
                            Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please enter any location name.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_hospital:
                mMap.clear();
                String url = getUrl(latitude, longitude, hospital);
                transferData[0] = mMap;
                transferData[1] = url;
                System.out.println("hey");
                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby hospitals...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby hospitals...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_school:
                mMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby schools...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby schools...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_restaurant:
                mMap.clear();
                url = getUrl(latitude, longitude, restaurant);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby restaurants...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby restaurants...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis" +
                ".com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + proximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");

        googleURL.append("&key=AIzaSyDOAqQb42sgtR0Vq3PvWEFEPiCRtcmKdJo");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());
        return googleURL.toString();
    }
}
