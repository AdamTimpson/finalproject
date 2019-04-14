package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.PreferenceEntry;
import com.adamtimpson.mobilityaid.database.model.Preference;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class PreferenceSettingsActivity extends AppCompatActivity {

    private final String KEY = "AIzaSyBjrZuM98cB5tjaITTc5LBRuZXez6cY6H0";
    public static String place = ""; // Should be set in the previous activity FORMAT: key:value - ATM:atm
    private static String latLng = "";

    private Button saveButton;
    private Button deleteButton;
    private Button clearButton;

    private EditText inputField;

    private RequestQueue requestQueue;

    private StringRequest stringRequest;

    private JSONObject jsonResponse;

    private PreferenceEntry preferenceEntry = new PreferenceEntry(this);

    private ActivityUtils activityUtils = new ActivityUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_settings);

        Toast.makeText(this, place, Toast.LENGTH_LONG).show();

        appendText(place.substring(0, place.indexOf(":")));

        initClickListeners();

    }

    private void initClickListeners() {
        saveButton = findViewById(R.id.preferenceSettingsSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputField = findViewById(R.id.preferenceNameField);

                StringBuffer uri = new StringBuffer();
                uri.append("https://maps.googleapis.com/");
                uri.append("maps/api/place/findplacefromtext/");
                uri.append("json?input=");
                uri.append(inputField.getText().toString());
                uri.append("&inputtype=textquery&fields=name,geometry");
                uri.append("&key=");
                uri.append(KEY);

                sendRequest(uri.toString());
                latLng = getLatLngFromResponse(jsonResponse);

                if(!latLng.equalsIgnoreCase("-1")) {
                    Toast.makeText(PreferenceSettingsActivity.this, latLng, Toast.LENGTH_LONG).show();

                    preferenceEntry.addPreference(new Preference(0, LogInUtils.getInstance().getCurrentUser().getId(), place.split(":")[1], latLng));

                    activityUtils.moveToNewRoute();

                } else {
                    Toast.makeText(PreferenceSettingsActivity.this, "Try again...", Toast.LENGTH_LONG).show();
                }


            }
        });

        deleteButton = findViewById(R.id.preferenceSettingsDeleteButton);
        clearButton = findViewById(R.id.preferenceSettingsClearButton);

    }

    private void sendRequest(String uri) {
        requestQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    jsonResponse = new JSONObject(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

    private String getLatLngFromResponse(JSONObject jsonResponse) {
        try {
            StringBuffer latLng = new StringBuffer();

            JSONArray results = jsonResponse.getJSONArray("candidates");
            Log.d("JSON: ", results.toString());

            JSONObject geo = results.getJSONObject(0).getJSONObject("geometry");
            Log.d("JSON: ", geo.toString());

            JSONObject location = geo.getJSONObject("location");
            Log.d("JSON: ", location.toString());

            latLng.append(location.get("lat"));
            latLng.append(",");
            latLng.append(location.get("lng"));

            Log.d("JSON: ", latLng.toString());

            return latLng.toString();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return "-1";
    }

    private void appendText(String text) {
        TextView textView = findViewById(R.id.locationField);
        textView.setText(textView.getText().toString() + " (" + text + ")");
    }

}
