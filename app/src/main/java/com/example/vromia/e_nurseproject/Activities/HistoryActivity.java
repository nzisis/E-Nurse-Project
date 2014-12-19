package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.HistoryAdapter;


/**
 * Created by Vromia on 17/12/2014.
 */
public class HistoryActivity extends Activity {

    private ListView lv;
    private HeathDatabase db;
    private Cursor cursor;
    private HistoryAdapter adapter;

    private Menu menu;
    private boolean switcher=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.lvHistory);

        db=new HeathDatabase(HistoryActivity.this);
        cursor=db.getAllDietItems();

        startManagingCursor(cursor);
        adapter = new HistoryAdapter(getApplicationContext() , cursor);
        lv.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu=menu;
        getMenuInflater().inflate(R.menu.diet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

       // case R.id.toggleButton:

        if(id==R.id.toggleButton) {

            if (switcher) {
                switcher = false;
                menu.clear();
                getMenuInflater().inflate(R.menu.workout_menu, menu);
                cursor = db.getAllWorkoutItems();
                adapter = new HistoryAdapter(getApplicationContext(), cursor);
                lv.setAdapter(adapter);

            } else {
                switcher = true;
                menu.clear();
                getMenuInflater().inflate(R.menu.diet_menu, menu);
                cursor = db.getAllDietItems();
                adapter = new HistoryAdapter(getApplicationContext(), cursor);
                lv.setAdapter(adapter);

            }

        }

        return super.onOptionsItemSelected(item);
    }



}
