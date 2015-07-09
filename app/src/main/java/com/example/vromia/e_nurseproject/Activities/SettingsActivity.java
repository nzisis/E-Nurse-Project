package com.example.vromia.e_nurseproject.Activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.vromia.e_nurseproject.Data.DietItem;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.Data.WorkoutItem;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.JSONParser;
import com.example.vromia.e_nurseproject.Utils.SharedPrefsManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikos on 3/6/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    private boolean hasAnimations;
    private HeathDatabase db;
    private static String url = "http://nikozisi.webpages.auth.gr/enurse/sync_data.php";
    private JSONParser jsonParser;
    private static final String TAG_SUCCESS = "success";
    private int success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hasAnimations = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).getBoolean("key_animations", true);

        addPreferencesFromResource(R.xml.preferences);

        Preference screen = (Preference) findPreference("key_about");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                builder.setMessage("About text")
                        .setTitle("About Us");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        screen = findPreference("key_animations");
        screen.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                hasAnimations = (boolean) newValue;
                return true;
            }
        });

        screen = findPreference("key_rate");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

                return false;
            }
        });

        screen = findPreference("key_export");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //TODO export to file
             db=new HeathDatabase(SettingsActivity.this);


                if(haveNetworkConnection()){
                    jsonParser=new JSONParser();
                    db=new HeathDatabase(SettingsActivity.this);

                    new SyncAppData().execute();
                }else{
                    Toast.makeText(SettingsActivity.this,"Παρακαλώ συνδεθείτε στο Διαδίκτυο",Toast.LENGTH_LONG).show();
                }


                return false;
            }
        });

        screen = findPreference("key_delete_exc");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                builder.setMessage("????? ???????? ??? ?????? ?? ?????????? ??? ?? ???????? ????????");
                builder.setPositiveButton("??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO delete excercise
                    }
                });
                builder.setNegativeButton("???????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });


        //when user clicks on "delete expense" preference item
        //ask for confirmation and delete the expense data records
        screen = findPreference("key_delete_food");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                builder.setMessage("????? ???????? ??? ?????? ?? ?????????? ??? ?? ???????? ??????????");
                builder.setPositiveButton("??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO delete food
                    }
                });
                builder.setNegativeButton("???????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }



    class SyncAppData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            SharedPrefsManager manager=new SharedPrefsManager(SettingsActivity.this);
            int userID=manager.getPrefsUserID();
            Log.i("USERID", userID + "");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userID", userID+""));

            Cursor dietCursor=db.getAllDietItems();
            if(dietCursor.getCount()!=0){
                JSONArray categories=new JSONArray();
                JSONArray times=new JSONArray();
                JSONArray dates=new JSONArray();
                for(dietCursor.moveToFirst(); !dietCursor.isAfterLast(); dietCursor.moveToNext()){
                    String category=dietCursor.getString(1);
                    String date=dietCursor.getString(2);
                    String mealTime=dietCursor.getString(4);
                    String time=mealTime+":00";

                    categories.put(category);
                    times.put(time);
                    dates.put(date);


                }
                params.add(new BasicNameValuePair("categories",categories.toString()));
                params.add(new BasicNameValuePair("dates",dates.toString()));
                params.add(new BasicNameValuePair("times", times.toString()));

            }


            Cursor workoutCursor=db.getAllWorkoutItems();
            if(workoutCursor.getCount()!=0){
                JSONArray categories=new JSONArray();
                JSONArray durationss=new JSONArray();
                JSONArray dates=new JSONArray();
                for(workoutCursor.moveToFirst(); !workoutCursor.isAfterLast(); workoutCursor.moveToNext()){
                    String category=workoutCursor.getString(1);
                    String reformedCategory="'"+category+"'";
                    String date=workoutCursor.getString(2);
                    double duration=Double.parseDouble(workoutCursor.getString(3));
                    Log.i("Duration ",reformedCategory);

                    categories.put(reformedCategory);
                    durationss.put((int)duration +"");
                    dates.put(date);
                }

                params.add(new BasicNameValuePair("WCategories",categories.toString()));
                params.add(new BasicNameValuePair("WDates",dates.toString()));
                params.add(new BasicNameValuePair("durations", durationss.toString()));
            }



            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            db.close();
            if(success==1){
                Toast.makeText(SettingsActivity.this,"Επιτυχής συγχρονισμός δεδομένων με την ιστοσελίδα",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(SettingsActivity.this,"Τα δεδόμενα στην ιστοσελίδα είναι ήδη ενημερωμένα",Toast.LENGTH_LONG).show();
            }
        }
    }




}
