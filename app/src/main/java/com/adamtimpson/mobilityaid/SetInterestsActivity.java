package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetInterestsActivity extends AppCompatActivity {

    private final String[] keys = NewRouteActivity.PLACES_KEYS;
    private final String[] values = NewRouteActivity.PLACES_VALUES;

    private ListView placesList;

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

    }

    private List<String> getKeysFromValues(List<String> values) {
        List<String> keys = new ArrayList<String>();

        for(String s: values) {
            if(Arrays.asList(values).contains(s)) {
                keys.add(Arrays.asList(this.keys).get(Arrays.asList(this.values).indexOf(s)));
            }
        }

        return keys;
    }

}
