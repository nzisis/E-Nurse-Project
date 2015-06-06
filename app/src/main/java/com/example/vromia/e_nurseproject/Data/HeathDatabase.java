package com.example.vromia.e_nurseproject.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Vromia on 17/12/2014.
 */
public class HeathDatabase extends SQLiteOpenHelper {

    private static final int Database_Version = 1;
    private static final String Database_Name = "HealthDatabase";

    private static final String TABLE_DIET = "Diet";
    private static final String TABLE_WORKOUT = "Workout";
    private static final String TABLE_DOCTORS = "Doctors";

    //Table Diet columns
    private static final String KEY_DIET_ID = "_id";
    private static final String KEY_DIET_AMOUNT = "amount";
    private static final String KEY_DIET_DATE = "date";
    private static final String KEY_DIET_TIME = "time";
    private static final String KEY_DIET_CATEGORY = "category";

    //Table Workout columns
    private static final String KEY_WORKOUT_ID = "_id";
    private static final String KEY_WORKOUT_DATE = "date";
    private static final String KEY_WORKOUT_CATEGORY = "category";
    private static final String KEY_WORKOUT_TIME = "workTime";
    private static final String KEY_WORKOUT_PERIOD = "period";

    //Table Doctors columns
    private static final String Key_Did = "id";
    private static final String Key_Dname = "name";
    private static final String Key_Dsurname = "surname";


    private static final String Create_Diet_Table = "CREATE TABLE " + TABLE_DIET + "(" + KEY_DIET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_DIET_CATEGORY + " TEXT NOT NULL," + KEY_DIET_DATE + " TEXT NOT NULL," + KEY_DIET_AMOUNT + " DOUBLE," + KEY_DIET_TIME + " TEXT NOT NULL" + ")";

    private static final String Create_Workout_Table = "CREATE TABLE " + TABLE_WORKOUT + "(" + KEY_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_WORKOUT_CATEGORY + " TEXT NOT NULL," + KEY_WORKOUT_DATE + " TEXT NOT NULL," + KEY_WORKOUT_TIME + " DOUBLE," + KEY_WORKOUT_PERIOD + " TEXT NOT NULL" + ")";

    private static final String Create_Doctor_Table = "CREATE TABLE " + TABLE_DOCTORS + "(" + Key_Did + " INTEGER PRIMARY KEY ," +
            Key_Dname + " TEXT NOT NULL," + Key_Dsurname + " TEXT NOT NULL" + ")";


    private SQLiteDatabase db;


    public HeathDatabase(Context context) {
        super(context, Database_Name, null, Database_Version);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Diet_Table);
        db.execSQL(Create_Workout_Table);
        db.execSQL(Create_Doctor_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);

        onCreate(db);

    }

    public void InsertDiet(DietItem item) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_DIET_AMOUNT, item.getAmount());
        cv.put(KEY_DIET_CATEGORY, item.getCategory());
        cv.put(KEY_DIET_DATE, item.getDate());
        cv.put(KEY_DIET_TIME, item.getTime());

        Log.i("Category", item.getCategory());
        Log.i("Amount", item.getAmount() + "");
        Log.i("Time", item.getTime());
        Log.i("Date", item.getDate());


        db.insert(TABLE_DIET, null, cv);

    }

    public void InsertWorkout(WorkoutItem item) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_WORKOUT_CATEGORY, item.getCategory());
        cv.put(KEY_WORKOUT_PERIOD, item.getPeriodOfDay());
        cv.put(KEY_WORKOUT_DATE, item.getDate());
        cv.put(KEY_WORKOUT_TIME, item.getWorkTime() + "");

        db.insert(TABLE_WORKOUT, null, cv);
        Log.i("nikos", "workout inserted");
    }

    public void InsertDoctor(DoctorItem item) {
        ContentValues cv = new ContentValues();
        cv.put(Key_Did, item.getId());
        cv.put(Key_Dname, item.getName());
        cv.put(Key_Dsurname, item.getSurname());

        db.insert(TABLE_DOCTORS, null, cv);
    }

    public String showDoctors() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DOCTORS, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Log.i("Doctor", cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2));
                return cursor.getString(2);
            }
        }
        return null;
    }

    public ArrayList<String> getDoctorsFullName() {
        ArrayList<String> full_names = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DOCTORS, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String full_name = cursor.getString(1) + " " + cursor.getString(2);
                full_names.add(full_name);
            }
        }

        return full_names;
    }


    public Cursor getAllDietItems() {

        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DIET,
                null);
    }

    public Cursor getAllWorkoutItems() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_WORKOUT,
                null);
    }


    public Cursor getDietByRecent() {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DIET
                        + " ORDER BY " + KEY_DIET_DATE + " DESC , " + KEY_DIET_TIME + " DESC , " + KEY_DIET_ID + " DESC",
                null);

        return cursor;
    }

    public Cursor getWorkoutByRecent() {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_WORKOUT
                        + " ORDER BY " + KEY_WORKOUT_DATE + " DESC , " + KEY_WORKOUT_ID + " DESC",
                null);

        return cursor;
    }

    public Cursor getDietByCategory(String category) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DIET
                        + " WHERE " + KEY_DIET_CATEGORY + " LIKE '" + category
                        + "' ORDER BY " + KEY_DIET_DATE + " DESC , " + KEY_DIET_ID + " DESC",
                null);

        return cursor;
    }

    public Cursor getWorkoutByCategory(String category) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_WORKOUT
                        + " WHERE " + KEY_WORKOUT_CATEGORY + " LIKE '" + category
                        + "' ORDER BY " + KEY_WORKOUT_DATE + " DESC , " + KEY_WORKOUT_ID + " DESC",
                null);

        return cursor;
    }

    //date param must be in format yyyy-mm-dd
    public Cursor getDietByDate(String date) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DIET
                        + " WHERE " + KEY_DIET_DATE + " LIKE '" + date
                        + "' ORDER BY " + KEY_DIET_ID + " DESC",
                null);

        return cursor;
    }

    //date param must be in format yyyy-mm-dd
    public Cursor getWorkoutByDate(String date) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_WORKOUT
                        + " WHERE " + KEY_WORKOUT_DATE + " LIKE '" + date
                        + "' ORDER BY " + KEY_WORKOUT_ID + " DESC",
                null);

        return cursor;
    }

    //date param must be in format yyyy-mm-dd
    public Cursor getWorkoutByPeriod(String period) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_WORKOUT
                        + " WHERE " + KEY_WORKOUT_PERIOD + " LIKE '" + period
                        + "' ORDER BY " + KEY_WORKOUT_ID + " DESC",
                null);

        return cursor;
    }


}
