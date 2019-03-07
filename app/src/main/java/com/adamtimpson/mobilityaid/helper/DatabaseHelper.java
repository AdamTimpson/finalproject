package com.adamtimpson.mobilityaid.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;

    private final String CREATE_USER_TABLE = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
    private final String CREATE_ROUTE_TABLE = "CREATE TABLE route(id INTEGER PRIMARY KEY AUTOINCREMENT,  userId INTEGER, name TEXT, destinations TEXT)";
    private final String CREATE_PREFERENCES_TABLE = "CREATE TABLE preferences(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, places TEXT)";

    private static final String DATABASE_NAME = "mobility_aid";
    private static final String TABLE_USER = "user";
    private static final String USER_KEY_ID = "id ";
    private static final String USER_KEY_FIRST_NAME = "firstName";
    private static final String USER_KEY_LAST_NAME = "lastName";
    private static final String USER_KEY_EMAIL = "email";
    private static final String USER_KEY_PASSWORD = "password";
    private static final String[] COLUMNS_USER = {USER_KEY_ID, USER_KEY_FIRST_NAME, USER_KEY_LAST_NAME, USER_KEY_EMAIL, USER_KEY_PASSWORD};
    private static final String TABLE_ROUTE = "route";
    private static final String ROUTE_KEY_ID = "id";
    private static final String ROUTE_KEY_USER_ID = "userId";
    private static final String ROUTE_KEY_NAME = "name";
    private static final String ROUTE_KEY_DESTINATIONS = "destinations";
    private static final String COLUMNS_ROUTE[] = {ROUTE_KEY_ID, ROUTE_KEY_USER_ID, ROUTE_KEY_NAME, ROUTE_KEY_DESTINATIONS};
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

    public Integer getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public String getCreateUserTable() {
        return CREATE_USER_TABLE;
    }

    public String getTableUser() {
        return TABLE_USER;
    }

    public String getUserKeyId() {
        return USER_KEY_ID;
    }

    public String getUserKeyFirstName() {
        return USER_KEY_FIRST_NAME;
    }

    public String getUserKeyLastName() {
        return USER_KEY_LAST_NAME;
    }

    public String getUserKeyEmail() {
        return USER_KEY_EMAIL;
    }

    public String getUserKeyPassword() {
        return USER_KEY_PASSWORD;
    }

    public String[] getColumnsUser() {
        return COLUMNS_USER;
    }

    public String getCreateRouteTable() {
        return CREATE_ROUTE_TABLE;
    }

    public String getTableRoute() {
        return TABLE_ROUTE;
    }

    public String getRouteKeyId() {
        return ROUTE_KEY_ID;
    }

    public String getRouteKeyUserId() {
        return ROUTE_KEY_USER_ID;
    }

    public String getRouteKeyName() {
        return ROUTE_KEY_NAME;
    }

    public String getRouteKeyDestinations() {
        return ROUTE_KEY_DESTINATIONS;
    }

    public String[] getColumnsRoute() {
        return COLUMNS_ROUTE;
    }

    public String getCreatePreferencesTable() {
        return CREATE_PREFERENCES_TABLE;
    }

    public String getTablePreferences() {
        return TABLE_PREFERENCES;
    }

    public String getPreferencesKeyId() {
        return PREFERENCES_KEY_ID;
    }

    public String getPreferencesKeyUserId() {
        return PREFERENCES_KEY_USER_ID;
    }

    public String getPreferencesKeyPlaces() {
        return PREFERENCES_KEY_PLACES;
    }

    public String[] getColumnPreferences() {
        return COLUMN_PREFERENCES;
    }
}
