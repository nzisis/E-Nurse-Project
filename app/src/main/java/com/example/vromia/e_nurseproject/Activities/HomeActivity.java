package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vromia.e_nurseproject.Data.DietItem;
import com.example.vromia.e_nurseproject.Data.DoctorItem;
import com.example.vromia.e_nurseproject.Data.GridItem;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.Data.WorkoutItem;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.GridAdapter;
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
 * Created by Vromia on 2/6/2015.
 */
public class HomeActivity extends Activity {

    private GridView gridView;
    private ArrayList<GridItem> items;

    private  HeathDatabase db;

    private static String url = "http://nikozisi.webpages.auth.gr/enurse/get_data.php";
    private JSONParser jsonParser;
    private ArrayList<DietItem> dietItems;
    private ArrayList<WorkoutItem> workoutItems;

    private boolean updateDiet=false,updateWorkout=false;

    private static final String TAG_DIET = "nutrition";
    private static final String TAG_MEALTIME = "mealTime";
    private static final String TAG_DATE = "date";
    private static final String TAG_MEAL = "meal";

    private static final String TAG_WORKOUT= "exercise";
    private static final String TAG_TYPE= "type";
    private static final String TAG_DURATION= "duration";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if(haveNetworkConnection()){

            jsonParser=new JSONParser();
            dietItems=new ArrayList<>();
            workoutItems=new ArrayList<>();
            db=new HeathDatabase(HomeActivity.this);

            new SyncWebData().execute();


        }




        gridView = (GridView) findViewById(R.id.gridview);
        items = new ArrayList<>();

        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_food), "Διατροφή"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_excercise), "Άσκηση"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_profile), "Προφίλ"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_history), "Ιστορικό"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_settings), "Ρυθμίσεις"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_exit), "Έξοδος"));


        GridAdapter adapter = new GridAdapter(HomeActivity.this, R.layout.grid_item_menu, items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(HomeActivity.this, DietActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(HomeActivity.this, WorkoutActivity.class));
                } else if (position == 2) {
                    Intent intent=new Intent(HomeActivity.this, UserDetailsActivity.class);
                    intent.putExtra("Menu","menu");
                    startActivity(intent);
                } else if (position == 3) {
                    startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                } else if (position == 5) {
                    finish();
                }
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


    class SyncWebData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... args) {
            SharedPrefsManager manager=new SharedPrefsManager(HomeActivity.this);
            int userID=manager.getPrefsUserID();
            Log.i("USERID",userID+"");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userID", userID+""));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {

                JSONArray dietArray=json.getJSONArray(TAG_DIET);
                JSONArray workoutArray=json.getJSONArray(TAG_WORKOUT);


                Cursor dietCursor=db.getAllDietItems();
                Cursor workoutCursor=db.getAllWorkoutItems();
                if(dietCursor.getCount()!=dietArray.length()){

                    updateDiet=true;

                    for (int i = 0; i < dietArray.length(); i++) {
                        JSONObject c = dietArray.getJSONObject(i);

                        String meal=c.getString(TAG_MEAL);
                        String date=c.getString(TAG_DATE);
                        String mealTime=c.getString(TAG_MEALTIME);

                        String dateTokens[]=date.split(" ");
                        String mealTimeTokens[]=mealTime.split(":");

                        DietItem dietItem=new DietItem(meal,dateTokens[0],1,mealTimeTokens[0]+":"+mealTimeTokens[1]);
                        dietItems.add(dietItem);

                    }


                }


                if(workoutCursor.getCount()!=workoutArray.length()){

                    updateWorkout=true;
                    for (int i = 0; i < workoutArray.length(); i++) {
                        JSONObject c = workoutArray.getJSONObject(i);

                        double duration=c.getDouble(TAG_DURATION);
                        String date=c.getString(TAG_DATE);
                        String type=c.getString(TAG_TYPE);

                        String dateTokens[]=date.split(" ");


                        WorkoutItem item=new WorkoutItem(type,dateTokens[0],duration,dateTokens[1]);
                        workoutItems.add(item);

                    }



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            boolean update=false;
            if(updateDiet){

                for(int i=0; i<dietItems.size(); i++){
                    DietItem item=dietItems.get(i);
                    if(!db.dietTupleExists(item.getCategory(),item.getTime())){
                        db.InsertDiet(item);
                        update=true;
                    }

                }

            }

            if(updateWorkout){

                for(int i=0; i<workoutItems.size(); i++){
                    WorkoutItem workoutItem=workoutItems.get(i);
                    if(!db.workoutTupleExists(workoutItem.getCategory(), workoutItem.getWorkTime(), workoutItem.getDate())){
                        db.InsertWorkout(workoutItem);
                        update=true;
                    }

                }

            }


            if(update){
                Toast.makeText(HomeActivity.this,"Πραγματοποιήθηκε ενημέρωση των δεδομένων σας",Toast.LENGTH_LONG).show();
            }


            db.close();


        }
    }



}
