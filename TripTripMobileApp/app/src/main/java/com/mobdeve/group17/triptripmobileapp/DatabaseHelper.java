package com.mobdeve.group17.triptripmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TripTrip.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_BDAY = "user_bday";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Trip table name
    private static final String TABLE_TRIP = "trip";
    private static final String COLUMN_TRIP_ID = "trip_id";
    private static final String COLUMN_TRIP_TITLE = "trip_title";
    private static final String COLUMN_TRIP_STARTDATE = "trip_startdate";
    private static final String COLUMN_TRIP_ENDDATE = "trip_enddate";
    private static final String COLUMN_TRIP_STARTLOCATION = "trip_startlocation";
    private static final String COLUMN_TRIP_ENDLOCATION = "trip_endlocation";
    private static final String COLUMN_TRIP_TYPE = "trip_type";
    private static final String COLUMN_TRIP_DESCRIP = "trip_description";
    private static final String COLUMN_TRIP_USEREMAIL = "trip_userEmail";
    private static final String COLUMN_TRIP_PIC = "trip_pic";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_EMAIL + " TEXT" + " primary key," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_BDAY + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TRIP_TABLE = "CREATE TABLE " + TABLE_TRIP + "("
            + COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TRIP_TITLE + " TEXT,"
            + COLUMN_TRIP_STARTDATE + " TEXT," + COLUMN_TRIP_ENDDATE + " TEXT,"
            + COLUMN_TRIP_STARTLOCATION + " TEXT," + COLUMN_TRIP_ENDLOCATION
            + " TEXT," + COLUMN_TRIP_TYPE + " TEXT," + COLUMN_TRIP_DESCRIP + " TEXT,"
            + COLUMN_TRIP_USEREMAIL + " TEXT," + COLUMN_TRIP_PIC + " BLOB" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TRIP_TABLE = "DROP TABLE IF EXISTS " + TABLE_TRIP;
    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TRIP_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TRIP_TABLE);
        // Create tables again
        onCreate(db);
    }

    private String hash(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //could also be MD5, SHA-256 etc.
            md.reset();
            md.update(password.getBytes("UTF-8"));
            byte[] resultByte = md.digest();
            password = String.format("%01x", new java.math.BigInteger(1, resultByte));

        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException ex) {
        }
        return password;
    }
    /**
     * This method is to create user record
     */
    public Boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_BDAY, user.getBirthday());
        values.put(COLUMN_USER_PASSWORD, hash(user.getPassword()));
        // Inserting Row
        long result = db.insert(TABLE_USER, null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * This method is to fetch a user and return the user records
     *
     * @return user
     */
    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        Cursor cursor = db.rawQuery("Select * from user where user_email = ?", new String[] {email});
        User user = new User();
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
                user.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_USER_BDAY)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(hash(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from user where user_email = ?", new String[] {email});
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * This method is to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUserEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where user_email = ? " +
                        "and user_password = ?", new String[]{email, hash(password)});

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method is to find a user using email and birthday
     */

    public User getUser_emailBirthday(String email, String bday) {
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        Cursor cursor = db.rawQuery("Select * from user where user_email = ? and user_bday = ?", new String[] {email, bday});
        User user = new User();
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            user.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_USER_BDAY)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(hash(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    /**
     * This method is to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_BDAY, user.getBirthday());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, hash(user.getPassword()));
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }
//    /**
//     * This method is to delete user record
//     *
//     * @param user
//     */
//    public void deleteUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // delete user record by id
//        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
//                new String[]{String.valueOf(user.getId())});
//        db.close();
//    }

    /** This method is to create a Trip record
     *
     * @param trip
     * @param userEmail
     * @return
     */
    public Boolean addTrip(Trip trip, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_TITLE, trip.getTripTitle());
        values.put(COLUMN_TRIP_STARTDATE, trip.getStartDate());
        values.put(COLUMN_TRIP_ENDDATE, trip.getEndDate());
        values.put(COLUMN_TRIP_STARTLOCATION, trip.getStartLocation());
        values.put(COLUMN_TRIP_ENDLOCATION, trip.getEndLocation());
        values.put(COLUMN_TRIP_TYPE, trip.getTripType());
        values.put(COLUMN_TRIP_DESCRIP, trip.getDescription());
        values.put(COLUMN_TRIP_PIC, trip.getTripPicId());
        values.put(COLUMN_TRIP_USEREMAIL, userEmail);
        // Inserting Row
        long result = db.insert(TABLE_TRIP, null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    /** This method is to get a specific trip of a user by trip id
     *
     * @param tripID
     * @return trip
     */
    public Trip getSpecificTrip(int tripID) {

        Trip trip = new Trip();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from trip where trip_id = ?"
                ,new String[]{String.valueOf(tripID)});
        if(cursor != null){
            if(cursor.moveToFirst()) {
                    trip.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ID))));
                    trip.setTripTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_TITLE)));
                    trip.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_STARTDATE)));
                    trip.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ENDDATE)));
                    trip.setStartLocation(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_STARTLOCATION)));
                    trip.setEndLocation(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ENDLOCATION)));
                    trip.setTripType(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_TYPE)));
                    trip.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_DESCRIP)));
                    trip.setTripPicId(cursor.getBlob(cursor.getColumnIndex(COLUMN_TRIP_PIC)));
            }
        }

        cursor.close();
        db.close();
        return trip;
    }

    public ArrayList<Trip> getTripsByUser(String email) {

        ArrayList<Trip> trips = new ArrayList<Trip>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from trip where trip_userEmail = ? order by trip_id"
                ,new String[]{email});
        if(cursor != null){
            if(cursor.moveToFirst()) {
                do{
                    Trip trip = new Trip();
                    trip.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ID))));
                    trip.setTripTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_TITLE)));
                    trip.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_STARTDATE)));
                    trip.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ENDDATE)));
                    trip.setStartLocation(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_STARTLOCATION)));
                    trip.setEndLocation(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ENDLOCATION)));
                    trip.setTripType(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_TYPE)));
                    trip.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_DESCRIP)));
                    trip.setTripPicId(cursor.getBlob(cursor.getColumnIndex(COLUMN_TRIP_PIC)));
                    trips.add(trip);
                }while(cursor.moveToNext());

            }
        }

        cursor.close();
        db.close();
        return trips;
    }

    /**
     * This method to update trip record
     *
     * @param trip
     */
    public void updateTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_TITLE, trip.getTripTitle());
        values.put(COLUMN_TRIP_STARTDATE, trip.getStartDate());
        values.put(COLUMN_TRIP_ENDDATE, trip.getEndDate());
        values.put(COLUMN_TRIP_STARTLOCATION, trip.getStartLocation());
        values.put(COLUMN_TRIP_ENDLOCATION, trip.getEndLocation());
        values.put(COLUMN_TRIP_TYPE, trip.getTripType());
        values.put(COLUMN_TRIP_DESCRIP, trip.getDescription());
        values.put(COLUMN_TRIP_PIC, trip.getTripPicId());
        // updating row
        db.update(TABLE_TRIP, values, COLUMN_TRIP_ID + " = ?",
                new String[]{String.valueOf(trip.getId())});
        db.close();
    }

        /**
     * This method is to delete trip record
     *
     * @param tripID
     */
    public void deleteTrip(int tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete trip record by id
        db.delete(TABLE_TRIP, COLUMN_TRIP_ID + " = ?",
                new String[]{String.valueOf(tripID)});
        db.close();
    }
}
