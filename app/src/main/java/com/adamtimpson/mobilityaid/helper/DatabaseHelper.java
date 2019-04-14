package com.adamtimpson.mobilityaid.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;

    private final String CREATE_USER_TABLE = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
    private final String CREATE_ROUTE_TABLE = "CREATE TABLE route(id INTEGER PRIMARY KEY AUTOINCREMENT,  userId INTEGER, name TEXT, destinations TEXT)";
    private final String CREATE_PREFERENCES_TABLE = "CREATE TABLE preferences(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, placeType TEXt, places TEXT)";
    private final String CREATE_DISTANCE_TABLE = "CREATE TABLE distance(id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, distance INTEGER)";

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
    private static final String PREFERENCES_KEY_PLACE_TYPE= "placeType";
    private static final String PREFERENCES_KEY_PLACES = "places";
    private static final String COLUMN_PREFERENCES[] = {PREFERENCES_KEY_ID, PREFERENCES_KEY_USER_ID, PREFERENCES_KEY_PLACE_TYPE, PREFERENCES_KEY_PLACES};
    private static final String TABLE_DISTANCE = "distance";
    private static final String DISTANCE_KEY_ID = "id";
    private static final String DISTANCE_KEY_USER_ID = "userId";
    private static final String DISTANCE_KEY_DISTANCE = "distance";
    private static final String COLUMN_DISTANCE[] = {DISTANCE_KEY_ID, DISTANCE_KEY_USER_ID, DISTANCE_KEY_DISTANCE};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ROUTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_PREFERENCES_TABLE);
        sqLiteDatabase.execSQL(CREATE_DISTANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS route");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS preferences");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS distance");

        this.onCreate(sqLiteDatabase);
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

    public String getTablePreferences() {
        return TABLE_PREFERENCES;
    }

    public String getPreferencesKeyId() {
        return PREFERENCES_KEY_ID;
    }

    public String getPreferencesKeyUserId() {
        return PREFERENCES_KEY_USER_ID;
    }

    public String getPreferencesKeyPlaceType() {
        return PREFERENCES_KEY_PLACE_TYPE;
    }

    public String getPreferencesKeyPlaces() {
        return PREFERENCES_KEY_PLACES;
    }

    public String[] getColumnPreferences() {
        return COLUMN_PREFERENCES;
    }

    public static String getTableDistance() {
        return TABLE_DISTANCE;
    }

    public static String getDistanceKeyId() {
        return DISTANCE_KEY_ID;
    }

    public static String getDistanceKeyUserId() {
        return DISTANCE_KEY_USER_ID;
    }

    public static String getDistanceKeyDistance() {
        return DISTANCE_KEY_DISTANCE;
    }

    public static String[] getColumnsDistance() {
        return COLUMN_DISTANCE;
    }
}
