package com.adamtimpson.mobilityaid.util;

import android.content.Context;
import android.content.Intent;

import com.adamtimpson.mobilityaid.HelpActivity;
import com.adamtimpson.mobilityaid.MapsActivity;
import com.adamtimpson.mobilityaid.NewRouteActivity;
import com.adamtimpson.mobilityaid.SetInterestsActivity;
import com.adamtimpson.mobilityaid.SetWalkingDistanceActivity;

public class ActivityUtils {

    private Context context;

    public ActivityUtils(Context context) {
        this.context = context;
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

}
