package com.example.vromia.e_nurseproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Orion on 18/12/2014.
 */
public class SharedPrefsManager {
    //the Shared Preferences file name
    private static final String SHARED_PREFS = "UserDetailSharedPrefs";

    //Shared Preferences attributes
    private static final String PREFS_ONOMA = "onoma";
    private static final String PREFS_ILIKIA = "ilikia";
    private static final String PREFS_YPSOS = "ypsos";
    private static final String PREFS_BAROS = "baros";
    private static final String PREFS_ISTORIKOPATHISEON = "istorikoPathiseon";
    private static final String PREFS_FYLO = "fylo";

    //the SharedPreferences and Editor objects
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    //constructor
    public SharedPrefsManager(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    //commit all changes done to the editor
    public void commit() {
        editor.commit();
    }

    //open the editor object for commiting changes
    public void startEditing() {
        editor = prefs.edit();
    }

    /**
     * Below are the setters and getters for each attribute
     */
    public String getPrefsOnoma() {
        return prefs.getString(PREFS_ONOMA, "");
    }

    public int getPrefsIlikia() {
        return prefs.getInt(PREFS_ILIKIA, 0);
    }

    public float getYpsos() {
        return prefs.getFloat(PREFS_YPSOS, 0);
    }

    public float getPrefsBaros() {
        return prefs.getFloat(PREFS_BAROS, 0);
    }

    public String getPrefsIstorikoPathiseon() {
        return prefs.getString(PREFS_ISTORIKOPATHISEON, "");
    }

    public String getPrefsFylo() {
        return prefs.getString(PREFS_FYLO, "");
    }

    public void setPrefsOnoma(String onoma) {
        editor.putString(PREFS_ONOMA, onoma);
    }

    public void setPrefsIlikia(int ilikia) {
        editor.putInt(PREFS_ILIKIA, ilikia);
    }

    public void setPrefsYpsos(float ypsos) {
        editor.putFloat(PREFS_YPSOS, ypsos);
    }

    public void setPrefsBaros(float baros) {
        editor.putFloat(PREFS_BAROS, baros);
    }

    public void setPrefsIstorikoPathiseon(String istorikoPthiseon) {
        editor.putString(PREFS_ISTORIKOPATHISEON, istorikoPthiseon);
    }

    public void setPrefsFylo(String fylo) {
        editor.putString(PREFS_FYLO, fylo);
    }

}
