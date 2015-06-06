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
public class HeathDatabase extends SQLiteOpenHelper{

    private static final int Database_Version = 1;
    private static final String Database_Name = "HealthDatabase";

    private static final String Table_Diet = "Diet";
    private static final String Table_Workout = "Workout";
    private static final String Table_Doctors="Doctors";

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

    //Table Doctors columns
    private static final String Key_Did = "id";
    private static final String Key_Dname = "name";
    private static final String Key_Dsurname = "surname";



    private static final String Create_Diet_Table= "CREATE TABLE "+ Table_Diet + "(" + Key_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Key_Category + " TEXT NOT NULL," + Key_Date + " TEXT NOT NULL," + Key_Amount + " DOUBLE," + Key_Time + " TEXT NOT NULL" + ")";

    private static final String Create_Workout_Table= "CREATE TABLE "+ Table_Workout + "(" + Key_WId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Key_WCategory + " TEXT NOT NULL," + Key_WDate + " TEXT NOT NULL," + Key_WorkTime + " DOUBLE," + Key_PeriodOfDay + " TEXT NOT NULL" + ")";

    private static final String Create_Doctor_Table = "CREATE TABLE "+ Table_Doctors + "(" + Key_Did + " INTEGER PRIMARY KEY ," +
            Key_Dname + " TEXT NOT NULL," + Key_Dsurname + " TEXT NOT NULL" + ")";


    private SQLiteDatabase db;


    public HeathDatabase(Context context){
        super(context, Database_Name, null, Database_Version);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Diet_Table);
        db.execSQL(Create_Workout_Table);
        db.execSQL(Create_Doctor_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Table_Diet);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Workout);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Doctors);

        onCreate(db);

    }

    public void InsertDiet(DietItem item){

        ContentValues cv=new ContentValues();
        cv.put(Key_Amount,item.getAmount());
        cv.put(Key_Category,item.getCategory());
        cv.put(Key_Date,item.getDate());
        cv.put(Key_Time,item.getTime());

        Log.i("Category",item.getCategory());
        Log.i("Amount",item.getAmount()+"");
        Log.i("Time",item.getTime());
        Log.i("Date",item.getDate());



        db.insert(Table_Diet,null,cv);

    }

    public void InsertWorkout(WorkoutItem item){
        ContentValues cv=new ContentValues();
        cv.put(Key_WCategory,item.getCategory());
        cv.put(Key_PeriodOfDay,item.getPeriodOfDay());
        cv.put(Key_WDate,item.getDate());
        cv.put(Key_WorkTime,item.getWorkTime()+"");

        db.insert(Table_Workout,null,cv);
    }

    public void InsertDoctor(DoctorItem item){
        ContentValues cv=new ContentValues();
        cv.put(Key_Did,item.getId());
        cv.put(Key_Dname,item.getName());
        cv.put(Key_Dsurname,item.getSurname());

        db.insert(Table_Doctors,null,cv);
    }

    public String showDoctors(){
        Cursor cursor=getReadableDatabase().rawQuery("SELECT * FROM "+Table_Doctors,null);
        if(cursor!=null){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Log.i("Doctor", cursor.getString(0) + " - "+cursor.getString(1) +" - "+cursor.getString(2));
               return cursor.getString(2);
            }
        }
        return null;
    }

    public ArrayList<String> getDoctorsFullName(){
        ArrayList<String> full_names=new ArrayList<>();
        Cursor cursor=getReadableDatabase().rawQuery("SELECT * FROM "+Table_Doctors,null);
        if(cursor!=null){
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                String full_name=cursor.getString(1) + " "+ cursor.getString(2);
                full_names.add(full_name);
            }
        }

        return full_names;
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
