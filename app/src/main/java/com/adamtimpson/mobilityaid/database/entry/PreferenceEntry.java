package com.adamtimpson.mobilityaid.database.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.Preference;
import com.adamtimpson.mobilityaid.database.model.Route;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class PreferenceEntry {

    private DatabaseHelper dbh;

    public PreferenceEntry(Context context) {
        dbh = new DatabaseHelper(context);
    }

    public void addPreference(Preference preference) {
        Log.d("[DEBUG]", preference.toString());

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getPreferencesKeyUserId(), preference.getUserId());
        values.put(dbh.getPreferencesKeyPlaces(), preference.getPlaces());

        db.insert(dbh.getTablePreferences(), null, values);

        db.close();
    }

    public Preference getPreference(Integer id) {
        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.query(dbh.getTablePreferences(), dbh.getColumnPreferences(), " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        Preference preference = new Preference();
        preference.setId(Integer.parseInt(c.getString(0)));
        preference.setUserId(Integer.parseInt(c.getString(1)));
        preference.setPlaces(c.getString(2));

        Log.d("[DEBUG]", preference.toString());

        return preference;
    }

    public List<Preference> getAllPreferences() {
        List<Preference> preferences = new LinkedList<Preference>();

        String query = "SELECT * FROM " + dbh.getTablePreferences();

        SQLiteDatabase db = dbh.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        Preference preference = null;
        if(c.moveToFirst()) {
            do {
                preference = new Preference();
                preference.setId(Integer.parseInt(c.getString(0)));
                preference.setUserId(Integer.parseInt(c.getString(1)));
                preference.setPlaces(c.getString(2));

                preferences.add(preference);
            } while(c.moveToNext());
        }

        Log.d("[DEBUG]", preferences.toString());

        return preferences;
    }

    public Integer updatePreference(Preference preference) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getRouteKeyUserId(), preference.getUserId());
        values.put(dbh.getPreferencesKeyPlaces(), preference.getPlaces());

        Integer result = db.update(dbh.getTablePreferences(), values, dbh.getPreferencesKeyId() + " = ?", new String[] {String.valueOf(preference.getId())});

        db.close();

        return result;
    }

    public void deletePreference(Route route) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(dbh.getTablePreferences(), dbh.getPreferencesKeyId() + " = ?", new String[] {String.valueOf(route.getId())});

        db.close();
    }

}
