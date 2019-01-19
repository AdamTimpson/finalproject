package com.adamtimpson.mobilityaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    Button setInterestsButton;
    Button newRouteButton;
    Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initClickListeners();
    }

    private void initClickListeners() {
        initSetInterestsListener();
        initNewRouteListener();
        initHelpListener();
    }

    private void initSetInterestsListener() {
        setInterestsButton = findViewById(R.id.myInterestsButton);
        setInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add functionality here
                Toast.makeText(MainMenuActivity.this, "TODO", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initNewRouteListener() {
        newRouteButton = findViewById(R.id.newRouteButton);
        newRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newRouteIntent = new Intent(view.getContext(), NewRouteActivity.class);
                startActivity(newRouteIntent);
            }
        });
    }

    private void initHelpListener() {
        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add functionality here
                Toast.makeText(MainMenuActivity.this, "TODO", Toast.LENGTH_LONG).show();
            }
        });
    }
}
