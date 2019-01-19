package com.adamtimpson.mobilityaid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private static final int MY_REQUEST_INT = 177; // 177 because why not...

    private GoogleMap mMap;

    private LocationManager locationManager;

    private ArrayList<String> selectedPlaces = NewRouteActivity.getSelectedPlaces();

    private LatLng _currentLatLng;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    private JSONObject jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("[DEBUG] ", Arrays.toString(selectedPlaces.toArray()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable current location

        // Check permission of Location - GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If the permission is not granted...
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }

            return;
        } else {
            // If permission is granted...
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.setIndoorEnabled(true);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, this);

            Criteria mCriteria = new Criteria();
            String bestProvider = String.valueOf(locationManager.getBestProvider(mCriteria, true));

            Location mLocation = locationManager.getLastKnownLocation(bestProvider);
            if(mLocation != null) {
                final double currentLat = mLocation.getLatitude();
                final double currentLong = mLocation.getLongitude();

                _currentLatLng = new LatLng(currentLat, currentLong);

                LatLng currentLatLng = new LatLng(currentLat, currentLong);

                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

                geoLocate();
            }
        }
    }

    private void geoLocate() {
        String searchString = selectedPlaces.get(0);

        Log.d("[DEBUG]", searchString);

        String key = "AIzaSyBjrZuM98cB5tjaITTc5LBRuZXez6cY6H0";
        String latLngString = "" + _currentLatLng.latitude + "," + _currentLatLng.longitude;
        String radiusString = "5000";
        String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latLngString + "&radius=" + radiusString + "&type=" + searchString + "&key=" + key;

        Log.d("[DEBUG]", "URL: " + urlString);

        // Use Volley to send a HttpConnectionRequest
        requestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.GET, urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonResponse = new JSONObject(response);

                    JSONArray results = jsonResponse.getJSONArray("results");

                    JSONObject geo;
                    JSONObject location;

                    geo = results.getJSONObject(0);
                    location = geo.getJSONObject("geometry").getJSONObject("location");

                    LatLng markerLatLng = new LatLng(Double.parseDouble(location.get("lat").toString()), Double.parseDouble(location.get("lng").toString()));

                    Log.d("[DEBUG]", markerLatLng.toString());

                    MarkerOptions options;
                    options = new MarkerOptions();
                    options.position(markerLatLng);
                    options.title("TEST");

                    mMap.addMarker(options);
                } catch(Exception e) {
                    Log.d("[DEBUG]", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("[DEBUG]", error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    private void sendVolleyRequest() {
        requestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.GET, urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonResponse = new JSONObject(response);

                    JSONArray results = jsonResponse.getJSONArray("results");

                    JSONObject geo;
                    JSONObject location;

                    geo = results.getJSONObject(0);
                    location = geo.getJSONObject("geometry").getJSONObject("location");

                    LatLng markerLatLng = new LatLng(Double.parseDouble(location.get("lat").toString()), Double.parseDouble(location.get("lng").toString()));

                    Log.d("[DEBUG]", markerLatLng.toString());

                    MarkerOptions options;
                    options = new MarkerOptions();
                    options.position(markerLatLng);
                    options.title("TEST");

                    mMap.addMarker(options);
                } catch(Exception e) {
                    Log.d("[DEBUG]", e.getMessage());
                }
            }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
