package com.adamtimpson.mobilityaid.database.entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class UserEntry {

    private Context context;

    private DatabaseHelper dbh;

    public UserEntry(Context context) {
        this.context = context;
        dbh = new DatabaseHelper(this.context);
    }

    public void addUser(User user) {
        Log.d("[DEBUG]", user.toString());

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getUserKeyFirstName(), user.getFirstName());
        values.put(dbh.getUserKeyLastName(), user.getLastName());
        values.put(dbh.getUserKeyEmail(), user.getEmail());
        values.put(dbh.getUserKeyPassword(), user.getPassword());

        db.insert(dbh.getTableUser(), null, values);

        db.close();
    }

    public User getUser(Integer id) {
        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.query(dbh.getTableUser(), dbh.getColumnsUser(), " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        User user = new User();
        user.setId(Integer.parseInt(c.getString(0)));
        user.setFirstName(c.getString(1));
        user.setLastName(c.getString(2));
        user.setEmail(c.getString(3));
        user.setPassword(c.getString(4));

        Log.d("[DEBUG]", user.toString());

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        String query = "SELECT * FROM " + dbh.getTableUser();

        SQLiteDatabase db = dbh.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        User user = null;
        if(c.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(c.getString(0)));
                user.setFirstName(c.getString(1));
                user.setLastName(c.getString(2));
                user.setEmail(c.getString(3));
                user.setPassword(c.getString(4));

                users.add(user);
            } while(c.moveToNext());
        }

        Log.d("[DEBUG]", users.toString());

        return users;
    }

}
