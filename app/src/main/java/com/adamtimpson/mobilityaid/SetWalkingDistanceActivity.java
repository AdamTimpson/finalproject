package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adamtimpson.mobilityaid.database.entry.DistanceEntry;
import com.adamtimpson.mobilityaid.database.model.Distance;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;

public class SetWalkingDistanceActivity extends AppCompatActivity {

    private final Integer DEFAULT_WALKING_DISTANCE = 5;

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private DistanceEntry distanceEntry = new DistanceEntry(this);

    private User currentUser = LogInUtils.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_walking_distance);

        showCurrentWalkingDistance();
        initClickListener();
    }

    private void showCurrentWalkingDistance() {
        if(distanceEntry.getDistanceByUserId(currentUser.getId()) == null) {
            ((EditText) findViewById(R.id.setWalkingDistanceField)).setText("" + DEFAULT_WALKING_DISTANCE);

        } else {
            ((EditText) findViewById(R.id.setWalkingDistanceField)).setText("" + distanceEntry.getDistanceByUserId(currentUser.getId()).getDistance());
        }

    }

    private void initClickListener() {
        Button setButton = findViewById(R.id.setWalkingDistanceButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer walkingDistance = Integer.parseInt(((EditText) findViewById(R.id.setWalkingDistanceField)).getText().toString());

                if(distanceEntry.getDistanceByUserId(currentUser.getId()) == null) {
                    distanceEntry.addDistance(new Distance(0, currentUser.getId(), walkingDistance));
                } else {
                    Distance distance = distanceEntry.getDistanceByUserId(currentUser.getId());
                    distance.setDistance(walkingDistance);

                    distanceEntry.updateDistance(distance);
                }

                activityUtils.moveToMainMenu();
            }
        });
    }
}
