package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adamtimpson.mobilityaid.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetInterestsActivity extends AppCompatActivity {

    private final String[] keys = NewRouteActivity.PLACES_KEYS;
    private final String[] values = NewRouteActivity.PLACES_VALUES;

    private ListView placesList;

    private ActivityUtils activityUtils = new ActivityUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_interests);

        displayList(keys);

    }

    private void displayList(String[] places) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.keys);

        placesList = findViewById(R.id.interestPlacesList);
        placesList.setAdapter(adapter);
        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String placeString = placesList.getItemAtPosition(position).toString();
                String value = getValueFromKey(placeString);

                placeString += ":";
                placeString += value;

                PreferenceSettingsActivity.place = placeString;
                activityUtils.moveToPreferenceSettings();

            }
        });

    }

    private String getValueFromKey(String key) {
        for(int i = 0; i < keys.length; i ++) {
            if(key.equalsIgnoreCase(keys[i])) {
                return values[i];
            }
        }

        return "ERROR";
    }

}
