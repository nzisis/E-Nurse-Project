package com.example.vromia.e_nurseproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vromia.e_nurseproject.Activities.HomeActivity;
import com.example.vromia.e_nurseproject.Activities.UserDetailsActivity;
import com.example.vromia.e_nurseproject.Utils.SharedPrefsManager;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //boolean isPass = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("PREFS_START_OFF_APP" , false);
        SharedPrefsManager sharedPrefsManager=new SharedPrefsManager(MainActivity.this);

        boolean pass=sharedPrefsManager.getPrefsStart();

        if(pass){

            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            this.finish();

        }else{



            startActivity(new Intent(MainActivity.this, UserDetailsActivity.class));
            this.finish();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
