package com.adamtimpson.mobilityaid.database.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.Preference;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;
import com.adamtimpson.mobilityaid.util.LogInUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
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
        values.put(dbh.getPreferencesKeyPlaceType(), preference.getPlaceType());
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
        preference.setPlaceType(c.getString(2));
        preference.setPlaces(c.getString(3));

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
                preference.setPlaceType(c.getString(2));
                preference.setPlaces(c.getString(3));

                preferences.add(preference);
            } while(c.moveToNext());
        }

        Log.d("[DEBUG]", preferences.toString());

        return preferences;
    }

    public List<Preference> getPreferencesByUserId(Integer userId) {
        List<Preference> preferences = new ArrayList<>();

        for(Preference p: getAllPreferences()) {
            if(p.getUserId() == userId) {
                preferences.add(p);
            }
        }

        return preferences;
    }

    public Preference getPreferenceByPlaceType(String placeType) {
        for(Preference p: getPreferencesByUserId(LogInUtils.getInstance().getCurrentUser().getId())) {
            if(p.getPlaceType().equalsIgnoreCase(placeType)) {
                return p;
            }
        }

        return null;
    }

    public void updatePreference(Preference preference) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(dbh.getTablePreferences());
        sql.append(" set ");
        sql.append(dbh.getPreferencesKeyPlaces());
        sql.append(" = \'");
        sql.append(preference.getPlaces());
        sql.append("\' where id = ");
        sql.append(preference.getId());

        System.out.println("UPDATE PREFERENCE: " + sql.toString());

        db.execSQL(sql.toString());

        db.close();
    }

    public void deletePreference(Integer id) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(dbh.getTablePreferences(), dbh.getPreferencesKeyId() + " = ?", new String[] {String.valueOf(id)});

        db.close();
    }

    public Integer getIdByPlaceType(String placeType) {
        for(Preference p: getAllPreferences()) {
            String logInId = "" + LogInUtils.getInstance().getCurrentUser().getId();
            String userId = "" + p.getUserId();

            Boolean sameId = (logInId.equalsIgnoreCase(userId));
            Boolean samePlaceType = ((p.getPlaceType()).equalsIgnoreCase(placeType));

            System.out.println("P.PLACE TYPE: " + p.getPlaceType());
            System.out.println("PLACE TYPE: " + placeType);

            System.out.println("SAME ID: " + sameId.toString());
            System.out.println("SAME PLACE TYPE: " + samePlaceType.toString());

            if(sameId && samePlaceType) {
                return p.getId();
            }

        }

        return -1;
    }

}
