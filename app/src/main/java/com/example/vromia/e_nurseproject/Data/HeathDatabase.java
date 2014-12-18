package com.example.vromia.e_nurseproject.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vromia on 17/12/2014.
 */
public class HeathDatabase extends SQLiteOpenHelper{

    private static final int Database_Version = 1;
    private static final String Database_Name = "HealthDatabase";

    private static final String Table_Diet = "Diet";
    private static final String Table_Workout = "Workout";

    //Table Diet columns
    private static final String Key_Id = "_id";
    private static final String Key_Amount = "amount";
    private static final String Key_Date = "date";
    private static final String Key_Time="time";
    private static final String Key_Category = "category";

    //Table Workout columns
    private static final String Key_WId = "_id";
    private static final String Key_WDate = "date";
    private static final String Key_WCategory = "category";
    private static final String Key_WorkTime="work time";
    private static final String Key_PeriodOfDay="period of day";

    private static final String Create_Diet_Table= "CREATE TABLE "+ Table_Diet + "(" + Key_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Key_Category + " TEXT NOT NULL," + Key_Date + " TEXT NOT NULL," + Key_Amount + " DOUBLE," + Key_Time + " TEXT NOT NULL" + ")";

    private static final String Create_Workout_Table= "CREATE TABLE "+ Table_Workout + "(" + Key_WId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Key_WCategory + " TEXT NOT NULL," + Key_WDate + " TEXT NOT NULL," + Key_WorkTime + " DOUBLE," + Key_PeriodOfDay + " TEXT NOT NULL" + ")";


    private SQLiteDatabase db;


    public HeathDatabase(Context context){
        super(context, Database_Name, null, Database_Version);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Diet_Table);
        db.execSQL(Create_Workout_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Table_Diet);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Workout);

        onCreate(db);

    }

    public void InsertDiet(DietItem item){

        ContentValues cv=new ContentValues();
        cv.put(Key_Amount,item.getAmount());
        cv.put(Key_Category,item.getCategory());
        cv.put(Key_Date,item.getDate());
        cv.put(Key_Time,item.getTime());



        db.insert(Table_Diet,null,cv);

    }

    public void InsertWorkout(WorkoutItem item){
        ContentValues cv=new ContentValues();
        cv.put(Key_WCategory,item.getCategory());
        cv.put(Key_PeriodOfDay,item.getPeriodOfDay());
        cv.put(Key_WDate,item.getDate());
        cv.put(Key_WorkTime,item.getWorkTime());

        db.insert(Table_Workout,null,cv);
    }

    public Cursor getAllDietItems(){

        return getReadableDatabase().rawQuery("SELECT * FROM " + Table_Diet,
                null);
    }

    public Cursor getAllWorkoutItems(){
        return getReadableDatabase().rawQuery("SELECT * FROM " + Table_Workout,
                null);
    }
}
