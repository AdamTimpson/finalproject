package com.adamtimpson.mobilityaid.database.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.Distance;
import com.adamtimpson.mobilityaid.database.model.Preference;
import com.adamtimpson.mobilityaid.database.model.Route;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class DistanceEntry {

    private DatabaseHelper dbh;

    public DistanceEntry(Context context) {
        dbh = new DatabaseHelper(context);
    }

    public void addDistance(Distance distance) {
        Log.d("[DEBUG]", distance.toString());

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getDistanceKeyUserId(), distance.getUserId());
        values.put(dbh.getDistanceKeyDistance(), distance.getDistance());

        db.insert(dbh.getTableDistance(), null, values);

        db.close();
    }

    public List<Distance> getAllDistances() {
        List<Distance> distances = new LinkedList<Distance>();

        String query = "SELECT * FROM " + dbh.getTableDistance();

        SQLiteDatabase db = dbh.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        Distance distance= null;
        if(c.moveToFirst()) {
            do {
                distance = new Distance();
                distance.setId(Integer.parseInt(c.getString(0)));
                distance.setUserId(Integer.parseInt(c.getString(1)));
                distance.setDistance(Integer.parseInt(c.getString(2)));

                distances.add(distance);
            } while(c.moveToNext());
        }

        return distances;
    }

    public Distance getDistance(Integer id) {
        Distance distance = null;

        if(getAllDistances().isEmpty() || getAllDistances() == null) {
            return distance;
        }

        for(Distance d: getAllDistances()) {
            if(d.getId() == id) {
                return d;
            }
        }

        return distance;
    }

    public Distance getDistanceByUserId(Integer id) {
        Distance distance = null;

        if(getAllDistances().isEmpty() || getAllDistances() == null) {
            return distance;
        }

        for(Distance d: getAllDistances()) {
            if(d.getUserId() == id) {
                return d;
            }
        }

        return distance;
    }

    public Integer updateDistance(Distance distance) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getDistanceKeyUserId(), distance.getUserId());
        values.put(dbh.getDistanceKeyDistance(), distance.getDistance());

        Integer result = db.update(dbh.getTableDistance(), values, dbh.getDistanceKeyId() + " = ?", new String[] {String.valueOf(distance.getId())});

        db.close();

        return result;
    }

    public void deleteDistance(Distance distance) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(dbh.getTableDistance(), dbh.getDistanceKeyId() + " = ?", new String[] {String.valueOf(distance.getId())});

        db.close();
    }
}
