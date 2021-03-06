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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.DistanceEntry;
import com.adamtimpson.mobilityaid.database.entry.PreferenceEntry;
import com.adamtimpson.mobilityaid.database.entry.UserEntry;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.util.FetchURL;
import com.adamtimpson.mobilityaid.util.LogInUtils;
import com.adamtimpson.mobilityaid.util.TaskLoadedCallback;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, TaskLoadedCallback {

    private static final int MY_REQUEST_INT = 177; // 177 because why not...

    private String key = "AIzaSyBjrZuM98cB5tjaITTc5LBRuZXez6cY6H0";

    private Integer currentIndex = 0;
    private Integer nextIndex = 1;
    private Integer zoomMagnification = 14;

    private Button nextPointButton;

    private GoogleMap mMap;

    private LocationManager locationManager;

    private Boolean orderDestinations = NewRouteActivity.getOrderDestinations(); // If the user wants the destinations to be ordered or not

    private List<String> selectedPlaces = null; // The destinations the user has chosen (as VALUES not KEYS ie book_shop not 'Book Shop')

    private ArrayList<LatLng> allMarkerLatLngArray = new ArrayList<>();

    private LatLng _currentLatLng;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    private JSONObject jsonResponse;

    private Polyline currentPolyline;

    private User curentUser = LogInUtils.getInstance().getCurrentUser();

    private UserEntry userEntry = new UserEntry(this);
    private PreferenceEntry preferenceEntry = new PreferenceEntry(this);
    private DistanceEntry distanceEntry = new DistanceEntry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initNextPointButton();

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
                allMarkerLatLngArray.add(currentLatLng);

                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Starting Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

                selectedPlaces = getSelectedPlaces();
                geoLocate();
            }
        }
    }

    /**
     * Checks if the user has clicked on a route from MyRoutes
     * if they have. then use that route. If they have not,
     * the route to be used will be obtained from NewRoute
     */
    private List<String> getSelectedPlaces() {
        List<String> routePlaces = new ArrayList<>();

        if(MyRoutesActivity.selectedRoute == null) {
            routePlaces = NewRouteActivity.getSelectedPlaces();
        } else {
            String route = MyRoutesActivity.selectedRoute.getDestinations(); // {'atm', 'bank', 'bakery'}
            route = route.replace("[", "");
            route = route.replace("]", "");
            route = route.replace(" " , "");
            route = route.replace("'", ""); // atm,bank,bakery

            routePlaces = Arrays.asList(route.split(","));

        }

        return routePlaces;
    }

    private void geoLocate() {
        for(int i = 0; i < selectedPlaces.size(); i++) {
            sendVolleyRequest(selectedPlaces.get(i));
        }
    }

    private void sendVolleyRequest(final String searchString) {
        Log.d("[DEBUG] searchString: ", searchString);

        String radiusString = "";

        if(distanceEntry.getDistanceByUserId(curentUser.getId()) == null || distanceEntry.getDistanceByUserId(curentUser.getId()).getDistance() == null) {
            radiusString = "5000";
        } else if(distanceEntry.getDistanceByUserId(curentUser.getId()).getDistance() != null) {
            radiusString = "" + (distanceEntry.getDistanceByUserId(curentUser.getId()).getDistance() * 1000);
        }

        String latLngString = "" + _currentLatLng.latitude + "," + _currentLatLng.longitude;
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

                    JSONObject geo = results.getJSONObject(0);
                    JSONObject location = geo.getJSONObject("geometry").getJSONObject("location");

                    LatLng markerLatLng = new LatLng(Double.parseDouble(location.get("lat").toString()), Double.parseDouble(location.get("lng").toString()));

                    Log.d("[DEBUG]", markerLatLng.toString());

                    if(userEntry.hasPreferenceSetFor(searchString)) {

                        String placeLatLng = preferenceEntry.getPreferenceByPlaceType(searchString).getPlaces();

                        Double placeLat = Double.parseDouble(placeLatLng.split(",")[0]);
                        Double placeLng = Double.parseDouble(placeLatLng.split(",")[1]);

                        markerLatLng = new LatLng(placeLat, placeLng);

                        addMarker(markerLatLng, searchString);

                    } else {
                        addMarker(markerLatLng, geo.getString("name"));
                    }

                    allMarkerLatLngArray.add(markerLatLng);

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

    private void addMarker(LatLng markerLatLng, String title) {
        MarkerOptions options = new MarkerOptions();
        options.position(markerLatLng);
        options.title(title);

        mMap.addMarker(options);
    }

    private String getDirectionsUrl(LatLng origin, LatLng destination) {
        String originString = "origin=" + origin.latitude + "," + origin.longitude;

        String destinationString = "destination=" + destination.latitude + "," + destination.longitude;

        String urlParameters = originString + "&" + destinationString + "&mode=walking";

        String url = "https://maps.googleapis.com/maps/api/directions/json" + "?" + urlParameters + "&key=" + key;

        return url;

    }

    private void initNextPointButton() {
        nextPointButton = findViewById(R.id.nextPointButton);

        nextPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                System.out.println("PLACES: " + selectedPlaces.toString());

                if(orderDestinations) {
                    Toast.makeText(MapsActivity.this, "Not yet implemented...", Toast.LENGTH_LONG).show();
                } else {
                    if((currentIndex >= 0 && nextIndex < allMarkerLatLngArray.size()))   {
                        new FetchURL(MapsActivity.this).execute(getDirectionsUrl(allMarkerLatLngArray.get(currentIndex), allMarkerLatLngArray.get(nextIndex)), "walking");

                        currentIndex++;
                        nextIndex++;
                    } else {
                        Toast.makeText(MapsActivity.this, "You have no more destinations planned", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomMagnification);
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

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline != null) currentPolyline.remove();

        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    private Double calculateDistance(LatLng a, LatLng b) {
        Location aLoc = new Location("a");
        aLoc.setLatitude(a.latitude);
        aLoc.setLongitude(a.longitude);

        Location bLoc = new Location("b");
        bLoc.setLatitude(b.latitude);
        bLoc.setLongitude(b.longitude);

        return Double.valueOf(aLoc.distanceTo(bLoc));
    }
}
