package com.adamtimpson.mobilityaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class NewRouteActivity extends Activity {

    private ActivityUtils activityUtils = new ActivityUtils(this);

    public ListView placesList;

    public static final String[] PLACES_VALUES = {
            "accounting",
            "airport",
            "amusement_park",
            "aquarium",
            "art_gallery",
            "atm",
            "bakery",
            "bank",
            "bar",
            "beauty_salon",
            "bicycle_store",
            "book_store",
            "bowling_alley",
            "bus_station",
            "cafe",
            "campground",
            "car_dealer",
            "car_rental",
            "car_repair",
            "car_wash",
            "casino",
            "cemetery",
            "church",
            "city_hall",
            "clothing_store",
            "convenience_store",
            "courthouse",
            "dentist",
            "department_store",
            "doctor",
            "electrician",
            "electronics_store",
            "embassy",
            "fire_station",
            "florist",
            "funeral_home",
            "furniture_store",
            "gas_station",
            "gym",
            "hair_care",
            "hardware_store",
            "hindu_temple",
            "home_goods_store",
            "hospital",
            "insurance_agency",
            "jewelry_store",
            "laundry",
            "lawyer",
            "library",
            "liquor_store",
            "local_government_office",
            "locksmith",
            "lodging",
            "meal_delivery",
            "meal_takeaway",
            "mosque",
            "movie_rental",
            "movie_theater",
            "moving_company",
            "museum",
            "night_club",
            "painter",
            "park",
            "parking",
            "pet_store",
            "pharmacy",
            "physiotherapist",
            "plumber",
            "police",
            "post_office",
            "real_estate_agency",
            "restaurant",
            "roofing_contractor",
            "rv_park",
            "school",
            "shoe_store",
            "shopping_mall",
            "spa",
            "stadium",
            "storage",
            "store",
            "subway_station",
            "supermarket",
            "synagogue",
            "taxi_stand",
            "train_station",
            "transit_station",
            "travel_agency",
            "veterinary_care",
            "zoo"
    };

    public static final String[] PLACES_KEYS = {
            "Accounting",
            "Airport",
            "Amusement Park",
            "Aquarium",
            "Art Gallery",
            "ATM",
            "Bakery",
            "Bank",
            "Bar",
            "Beauty Salon",
            "Bike Store",
            "Book Store",
            "Bowling Alley",
            "Bus Station",
            "Cafe",
            "Campground",
            "Car Dealer",
            "Car Rental",
            "Car Repair",
            "Car Wash",
            "Casino",
            "Graveyard",
            "Church",
            "City Hall",
            "Clothes Store",
            "Convenience Store",
            "Courthouse",
            "Dentist",
            "Department Store",
            "Doctor's Office",
            "Electrician",
            "Electronics Store",
            "Embassy",
            "Fire Station",
            "Florist",
            "Funeral Home",
            "Furniture Store",
            "Petrol Station",
            "Gym",
            "Hair Care",
            "Hardware Store",
            "Hindu Temple",
            "Home Goods Store",
            "Hospital",
            "Insurance Agency",
            "Jewelry Store",
            "Laundry",
            "Layer",
            "Library",
            "Off License",
            "Local Government Office",
            "Locksmith",
            "Lodging",
            "Meal Delivery",
            "Meal Takeaway",
            "Mosque",
            "Movie Rental",
            "Movie Theater",
            "Moving Company",
            "Museum",
            "Night Club",
            "Painter",
            "Park",
            "Parking",
            "Pet Store",
            "Pharmacy",
            "Physiotherapist",
            "Plumber",
            "Police",
            "Post Office",
            "Real Estate Agency",
            "Restaurant",
            "Roofing Contractor",
            "RV Park",
            "School",
            "Shoe Store",
            "Shopping Mall",
            "Spa",
            "Stadium",
            "Storage",
            "Store",
            "Subway Station",
            "Supermarket",
            "Synagogue",
            "Taxi Stand",
            "Train Station",
            "Transit Station",
            "Travel Agency",
            "Vet",
            "Zoo"
    };

    private ArrayList<String> addedPlaces = new ArrayList<String>();

    private static String placesString;

    private Button startButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);

        initClickListeners();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, PLACES_KEYS);

        placesList = findViewById(R.id.destinationsList);
        placesList.setAdapter(adapter);
        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(NewRouteActivity.this, "[DEBUG] " + (PLACES_KEYS[position] + ": " + PLACES_VALUES[position]), Toast.LENGTH_LONG).show(); // Debug
                addedPlaces.add(PLACES_KEYS[position]);
                resetChosenPlacesText(addedPlaces);
            }
        });
    }

    private void initClickListeners() {
        initStartButtonListener();
        initClearButtonListener();
    }

    private void initStartButtonListener() {
        startButton = findViewById(R.id.startRouteButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToMaps();
            }
        });
    }

    private void initClearButtonListener() {
        clearButton = findViewById(R.id.clearRouteButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.chosenDestinations);
                textView.setText("Current destinations: ");

                addedPlaces = new ArrayList<String>();

                Toast.makeText(NewRouteActivity.this, "Destinations list cleared...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetChosenPlacesText(ArrayList<String> places) {
        TextView textView = findViewById(R.id.chosenDestinations);
        textView.setText("Current destinations: " + getChosenPlaces(places));
    }

    private String getChosenPlaces(ArrayList<String> places) {
        placesString = "";
        for(String s: places) {
            if(!placesString.contains(s)) {
                placesString += s + ", ";
            }
        }

        return placesString;
    }

    public static ArrayList<String> getSelectedPlaces() {
        ArrayList<String> selectedPlaces = new ArrayList<String>();
        String[] placesStringArray = placesString.split(", ");

        for(String s: placesStringArray) {
            Integer position = Arrays.asList(PLACES_KEYS).indexOf(s);

            selectedPlaces.add(PLACES_VALUES[position]);
        }

        return selectedPlaces;
    }

}
