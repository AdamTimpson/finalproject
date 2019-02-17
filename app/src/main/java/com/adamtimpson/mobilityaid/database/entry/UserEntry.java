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

    private DatabaseHelper dbh;

    public UserEntry(Context context) {
        dbh = new DatabaseHelper(context);
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

    public Integer updateUser(User user) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbh.getUserKeyFirstName(), user.getFirstName());
        values.put(dbh.getUserKeyLastName(), user.getLastName());
        values.put(dbh.getUserKeyEmail(), user.getEmail());
        values.put(dbh.getUserKeyPassword(), user.getPassword());

        Integer result = db.update(dbh.getTableUser(), values, dbh.getUserKeyId() + " = ?", new String[] {String.valueOf(user.getId())});

        db.close();

        return result;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(dbh.getTableUser(), dbh.getUserKeyId() + " = ?", new String[] {String.valueOf(user.getId())});

        db.close();
    }

    public Boolean doesContain(String email) {
        List<User> users = getAllUsers();

        for(User u: users) {
            if(u.getEmail().equalsIgnoreCase(email)) {
                Log.d("[DEBUG]", dbh.getTableUser() + " does contain an entry with email: " + email);
                return true;
            }
        }

        Log.d("[DEBUG]", dbh.getTableUser() + " does NOT contain an entry with email: " + email);
        return false;
    }

    public Boolean doesContain(Integer id) {
        List<User> users = getAllUsers();

        for(User u: users) {
            if(u.getId() == id) {
                Log.d("[DEBUG]", dbh.getTableUser() + " does contain an entry with id: " + id);
                return true;
            }
        }

        Log.d("[DEBUG]", dbh.getTableUser() + " does NOT contain an entry with id: " + id);
        return false;
    }

    public String getPasswordByEmail(String email) {
        String password = "";

        List<User> users = getAllUsers();

        for(User u: users) {
            if(u.getEmail().equalsIgnoreCase(email))
                password = u.getPassword();
        }

        return password;
    }

}
