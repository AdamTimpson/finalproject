package com.adamtimpson.mobilityaid.util;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;

import com.adamtimpson.mobilityaid.HelpActivity;
import com.adamtimpson.mobilityaid.MainActivity;
import com.adamtimpson.mobilityaid.MainMenuActivity;
import com.adamtimpson.mobilityaid.MapsActivity;
import com.adamtimpson.mobilityaid.MyRoutesActivity;
import com.adamtimpson.mobilityaid.NewRouteActivity;
import com.adamtimpson.mobilityaid.PreferenceSettingsActivity;
import com.adamtimpson.mobilityaid.RegisterActivity;
import com.adamtimpson.mobilityaid.SaveRouteActivity;
import com.adamtimpson.mobilityaid.SetInterestsActivity;
import com.adamtimpson.mobilityaid.SetWalkingDistanceActivity;

public class ActivityUtils {

    private Context context;

    public ActivityUtils(Context context) {
        this.context = context;
    }

    public void moveToMain() {
        this.context.startActivity(new Intent(this.context, MainActivity.class));
    }

    public void moveToSetInterests() {
        this.context.startActivity(new Intent(this.context, SetInterestsActivity.class));
    }

    public void moveToSetWalkingDistance() {
        this.context.startActivity(new Intent(this.context, SetWalkingDistanceActivity.class));
    }

    public void moveToNewRoute() {
        this.context.startActivity(new Intent(this.context, NewRouteActivity.class));
    }

    public void moveToHelp() {
        this.context.startActivity(new Intent(this.context, HelpActivity.class));
    }

    public void moveToMaps() {
        this.context.startActivity(new Intent(this.context, MapsActivity.class));
    }

    public void moveToMainMenu() {
        this.context.startActivity(new Intent(this.context, MainMenuActivity.class));
    }

    public void moveToRegister() {
        this.context.startActivity(new Intent(this.context, RegisterActivity.class));
    }

    public void moveToSaveRoute() {
        this.context.startActivity(new Intent(this.context, SaveRouteActivity.class));
    }

    public void moveToMyRoutes() {
        this.context.startActivity(new Intent(this.context, MyRoutesActivity.class));
    }

    public void moveToPreferenceSettings() {
        this.context.startActivity(new Intent(this.context, PreferenceSettingsActivity.class));
    }

}
