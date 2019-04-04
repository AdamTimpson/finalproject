package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adamtimpson.mobilityaid.database.entry.RouteEntry;
import com.adamtimpson.mobilityaid.database.model.Route;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;

public class SaveRouteActivity extends AppCompatActivity {

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private RouteEntry routeEntry = new RouteEntry(this);

    private EditText routeNameField;

    private String routeName = "Default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_route);

        routeNameField = findViewById(R.id.routeNameField);

        initClickListeners();
    }

    private void initClickListeners() {
        Button saveButton = findViewById(R.id.saveRouteButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routeName = routeNameField.getText().toString();

                saveButtonListener();
            }
        });
    }

    private void saveButtonListener() {
        Route route = new Route(0, LogInUtils.getInstance().getCurrentUser().getId(), routeName, LogInUtils.getInstance().getValuesToBeSaved().toString());
        routeEntry.addRoute(route);

        activityUtils.moveToMyRoutes();
    }

}
