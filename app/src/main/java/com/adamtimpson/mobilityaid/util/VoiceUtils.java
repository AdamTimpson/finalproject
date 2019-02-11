package com.adamtimpson.mobilityaid.util;

public class VoiceUtils {

    public static final String SET_INTERESTS = "set preferences";
    public static final String WALKING_DISTANCE = "set walking distance";
    public static final String NEW_ROUTE = "plan a new route";
    public static final String HELP = "view help";

    public static String getError(String cmd) {
        return "Sorry, '" + cmd + "' is an invalid command!";
    }

}
