package com.adamtimpson.mobilityaid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.adamtimpson.mobilityaid.database.model.User;

import java.net.PasswordAuthentication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MobilityAidDB";
    private final String CREATE_USER_TABLE = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
    private static final String TABLE_USER = "user";
    private static final String USER_KEY_ID = "id";
    private static final String USER_KEY_FIRST_NAME = "firstName";
    private static final String USER_KEY_LAST_NAME = "lastName";
    private static final String USER_KEY_EMAIL = "email";
    private static final String USER_KEY_PASSWORD = "password";
    private static final String[] COLUMNS_USER = {USER_KEY_ID, USER_KEY_FIRST_NAME, USER_KEY_LAST_NAME, USER_KEY_EMAIL, USER_KEY_PASSWORD};
    private final String CREATE_ROUTE_TABLE = "CREATE TABLE route(id INTEGER PRIMARY KEY AUTOINCREMENT,  userId INTEGER, destinations TEXT)";
    private static final String TABLE_ROUTE = "route";
    private static final String ROUTE_KEY_ID = "id";
    private static final String ROUTE_KEY_USER_ID = "userId";
    private static final String ROUTE_KEY_DESTINATIONS = "destinations";
    private static final String COLUMNS_ROUTE[] = {ROUTE_KEY_ID, ROUTE_KEY_USER_ID, ROUTE_KEY_DESTINATIONS};
    private final String CREATE_PREFERENCES_TABLE = "CREATE TABLE preferences(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, places TEXT)";
    private static final String TABLE_PREFERENCES = "preferences";
    private static final String PREFERENCES_KEY_ID = "id";
    private static final String PREFERENCES_KEY_USER_ID = "userId";
    private static final String PREFERENCES_KEY_PLACES = "places";
    private static final String COLUMN_PREFERENCES[] = {PREFERENCES_KEY_ID, PREFERENCES_KEY_USER_ID, PREFERENCES_KEY_PLACES};


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");

        this.onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        Log.d("[DEBUG]", user.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_ID, user.getId());
        values.put(USER_KEY_FIRST_NAME, user.getFirstName());
        values.put(USER_KEY_LAST_NAME, user.getLastName());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);

        db.close();
    }

    public User getUser(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_USER, COLUMNS_USER, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
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

}
