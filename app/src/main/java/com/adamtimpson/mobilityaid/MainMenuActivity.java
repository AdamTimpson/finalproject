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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;
import com.adamtimpson.mobilityaid.util.VoiceUtils;

import java.util.ArrayList;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private VoiceUtils voiceUtils;

    private Button setInterestsButton;
    private Button setWalkingDistanceButton;
    private Button newRouteButton;
    private Button helpButton;

    private LinearLayout setInterestsLayout;
    private LinearLayout setWalkingDistanceLayout;
    private LinearLayout newRouteLayout;
    private LinearLayout helpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        voiceUtils = new VoiceUtils(getApplicationContext());

        initClickListeners();

        LogInUtils logInUtils = LogInUtils.getInstance();
        System.out.println("THE CURRENT LOGGED IN USER IS... " + logInUtils.getCurrentUser().toString());
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
        if(cmd.equalsIgnoreCase(voiceUtils.getSetInterests())) {
            activityUtils.moveToSetInterests();
        } else if(cmd.equalsIgnoreCase(voiceUtils.getWalkingDistance())) {
            activityUtils.moveToSetWalkingDistance();
        } else if(cmd.equalsIgnoreCase(voiceUtils.getNewRoute())) {
            activityUtils.moveToNewRoute();
        } else if(cmd.equalsIgnoreCase(voiceUtils.getHelp())) {
            activityUtils.moveToHelp();
        } else {
            Toast.makeText(MainMenuActivity.this, voiceUtils.getError(cmd), Toast.LENGTH_LONG).show();
        }
    }

    private void initClickListeners() {
        initSetInterestsListener();
        initSetWalkingDistanceListener();
        initNewRouteListener();
        initHelpListener();
    }

    private void initSetInterestsListener() {
        setInterestsButton = findViewById(R.id.myInterestsButton);
        setInterestsLayout = findViewById(R.id.myInterestsLayout);

        // Button click
        setInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetInterests();
            }
        });

        // Layout click
        setInterestsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetInterests();
            }
        });

        // Button long click
        setInterestsButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getSetInterests(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });

        // Layout long click
        setInterestsLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getSetInterests(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });
    }

    private void initSetWalkingDistanceListener() {
        setWalkingDistanceButton = findViewById(R.id.setDistanceButton);
        setWalkingDistanceLayout = findViewById(R.id.setDistanceLayout);

        // Button click
        setWalkingDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetWalkingDistance();
            }
        });

        // Layout click
        setWalkingDistanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToSetWalkingDistance();
            }
        });

        // Button long click
        setWalkingDistanceButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getWalkingDistance(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });

        // Layout long click
        setWalkingDistanceLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getWalkingDistance(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });
    }

    private void initNewRouteListener() {
        newRouteButton = findViewById(R.id.newRouteButton);
        newRouteLayout = findViewById(R.id.newRouteLayout);

        // Button click
        newRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToNewRoute();
            }
        });

        // Layout click
        newRouteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToNewRoute();
            }
        });

        // Button long click
        newRouteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getNewRoute(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });

        // Layout long click
        newRouteLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getNewRoute(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });
    }

    private void initHelpListener() {
        helpButton = findViewById(R.id.helpButton);
        helpLayout = findViewById(R.id.helpLayout);

        // Button click
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToHelp();
            }
        });

        // Layout click
        helpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToHelp();
            }
        });

        // Button long click
        helpButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getHelp(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });

        // Layout long click
        helpLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                voiceUtils.getTts().speak(voiceUtils.getHelp(), voiceUtils.getQueue(), null, null);

                return true;
            }
        });
    }
}
