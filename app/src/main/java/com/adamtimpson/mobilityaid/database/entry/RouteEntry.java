package com.adamtimpson.mobilityaid.database.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.Route;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class RouteEntry {

    private DatabaseHelper dbh;

    public RouteEntry(Context context) {
        dbh = new DatabaseHelper(context);
    }

    public void addRoute(Route route) {
        Log.d("[DEBUG]", route.toString());

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getRouteKeyId(), route.getUserId());
        values.put(dbh.getRouteKeyDestinations(), route.getDestinations());

        db.insert(dbh.getTableRoute(), null, values);

        db.close();
    }

    public Route getRoute(Integer id) {
        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.query(dbh.getTableRoute(), dbh.getColumnsRoute(), " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        Route route = new Route();
        route.setId(Integer.parseInt(c.getString(0)));
        route.setUserId(Integer.parseInt(c.getString(1)));
        route.setDestinations(c.getString(2));

        Log.d("[DEBUG]", route.toString());

        return route;
    }

    public List<Route> getAllRoutes() {
        List<Route> routes = new LinkedList<Route>();

        String query = "SELECT * FROM " + dbh.getTableRoute();

        SQLiteDatabase db = dbh.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        Route route = null;
        if(c.moveToFirst()) {
            do {
                route = new Route();
                route.setId(Integer.parseInt(c.getString(0)));
                route.setUserId(Integer.parseInt(c.getString(1)));
                route.setDestinations(c.getString(2));

                routes.add(route);
            } while(c.moveToNext());
        }

        Log.d("[DEBUG]", routes.toString());

        return routes;
    }

    public Integer updateRoute(Route route) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getRouteKeyUserId(), route.getUserId());
        values.put(dbh.getRouteKeyDestinations(), route.getDestinations());

        Integer result = db.update(dbh.getTableRoute(), values, dbh.getRouteKeyId() + " = ?", new String[] {String.valueOf(route.getId())});

        db.close();

        return result;
    }

    public void deleteRoute(Route route) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(dbh.getTableRoute(), dbh.getRouteKeyId() + " = ?", new String[] {String.valueOf(route.getId())});

        db.close();
    }

}
