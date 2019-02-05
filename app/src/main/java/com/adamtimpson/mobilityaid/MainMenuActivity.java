package com.adamtimpson.mobilityaid;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.VoiceUtils;

import java.util.ArrayList;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private ActivityUtils activityUtils = new ActivityUtils(this);

    Button setInterestsButton;
    Button setWalkingDistanceButton;
    Button newRouteButton;
    Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voice, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.voiceButton: {
                voiceCommand();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void voiceCommand() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening for command...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch(Exception e) {
            Log.e("[Exception]", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if(resultCode == RESULT_OK && null != data) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    processVoiceCommand(results.get(0));
                }

                break;
            }
        }
    }

    private void processVoiceCommand(String cmd) {
        if(cmd.equalsIgnoreCase(VoiceUtils.NEW_ROUTE)) {
            activityUtils.moveToNewRoute();
        } else {
            Toast.makeText(MainMenuActivity.this, VoiceUtils.getError(cmd), Toast.LENGTH_LONG).show();

        }
    }

    private void initClickListeners() {
        initSetInterestsListener();
        initSetWalkingDistance();
        initNewRouteListener();
        initHelpListener();
    }

    private void initSetInterestsListener() {
        setInterestsButton = findViewById(R.id.myInterestsButton);
        setInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetInterests();
            }
        });
    }

    private void initSetWalkingDistance() {
        setWalkingDistanceButton = findViewById(R.id.setDistanceButton);
        setWalkingDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetWalkingDistance();
            }
        });
    }

    private void initNewRouteListener() {
        newRouteButton = findViewById(R.id.newRouteButton);
        newRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToNewRoute();
            }
        });
    }

    private void initHelpListener() {
        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToHelp();
            }
        });
    }
}
