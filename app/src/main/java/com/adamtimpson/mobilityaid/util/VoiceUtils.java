package com.adamtimpson.mobilityaid.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class VoiceUtils {

    private final TextToSpeech tts;

    private final Integer QUEUE = TextToSpeech.QUEUE_FLUSH;

    private final String SET_INTERESTS = "set preferences";
    private final String WALKING_DISTANCE = "set walking distance";
    private final String NEW_ROUTE = "plan a new route";
    private final String HELP = "view help";
    private final String MY_ROUTES = "my routes";

    public VoiceUtils(Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    Integer langCode = tts.setLanguage(Locale.ENGLISH);

                    if (langCode == TextToSpeech.LANG_MISSING_DATA || langCode == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.d("[DEBUG]", "Language not supported");
                    } else {
                        Log.d("[DEBUG]", "Language supported");
                    }

                    Log.d("[DEBUG]", "Initialization success...");
                }
            }
        });
    }

    public String getError(String cmd) {
        return "Sorry, '" + cmd + "' is an invalid command!";
    }

    public Integer getQueue() {
        return QUEUE;
    }

    public TextToSpeech getTts() {
        return tts;
    }

    public String getSetInterests() {
        return SET_INTERESTS;
    }

    public String getWalkingDistance() {
        return WALKING_DISTANCE;
    }

    public String getNewRoute() {
        return NEW_ROUTE;
    }

    public String getHelp() {
        return HELP;
    }

    public String getMyRoutes() {
        return MY_ROUTES;
    }
}
