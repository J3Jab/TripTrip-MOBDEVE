package com.mobdeve.group17.triptripmobileapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    public PreferenceUtils(){

    }

    public static boolean saveLogin(Boolean login, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(String.valueOf(Constants.KEY_LOGIN), login);
        prefsEditor.apply();
        return true;
    }

    public static boolean IsLoggedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(String.valueOf(Constants.KEY_LOGIN), false);
    }

    public static boolean saveEmail(String email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_EMAIL, email);
        prefsEditor.apply();
        return true;
    }

    public static String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMAIL, null);
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, null);
    }

    public static boolean saveName(String name, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_NAME, name);
        prefsEditor.apply();
        return true;
    }

    public static String getName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_NAME, null);
    }

    public static boolean saveBirthday(String birthday, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_BDAY, birthday);
        prefsEditor.apply();
        return true;
    }

    public static String getBirthday(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_BDAY, null);
    }

    public static boolean saveTripId(int trip_id, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_TRIPID, trip_id);
        prefsEditor.apply();
        return true;
    }

    public static int getTripId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.KEY_TRIPID, 0);
    }

    public static boolean saveTripLocation(String trip_Loc, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TRIPLOCATION, trip_Loc);
        prefsEditor.apply();
        return true;
    }

    public static String getTripLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TRIPLOCATION, null);
    }

    public static boolean saveTripDate(String trip_date, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TRIPDATE, trip_date);
        prefsEditor.apply();
        return true;
    }

    public static String getTripDate(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TRIPDATE, null);
    }
}
