package com.example.vromia.e_nurseproject.Activities;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.DrawerAdapter;

/**
 * Created by Vromia on 17/12/2014.
 */
public class HomeActivity extends Activity {

    private Button diet, workout;
    private String[] actions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initListeners();

        actions=getResources().getStringArray(R.array.actions);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerAdapter adapter = new DrawerAdapter(HomeActivity.this, R.layout.drawer_item, actions);

        mDrawerList.setAdapter(adapter);
//        drawer.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.drawer_menu)));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList.setOnItemClickListener(drawerClickListener);


        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Drawer Just Opened");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle("Drawer just closed");
                invalidateOptionsMenu();

            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);



    }

    private void initUI() {
        diet = (Button) findViewById(R.id.diet);
        workout = (Button) findViewById(R.id.workout);
    }

    private void initListeners() {
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DietActivity.class);
                startActivity(i);
            }
        });


        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, WorkoutActivity.class);
                startActivity(i);
            }
        });



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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private ListView.OnItemClickListener drawerClickListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(HomeActivity.this, HistoryActivity.class));

                    break;
                case 1:
                    startActivity(new Intent(HomeActivity.this, UserDetailsActivity.class));
                    break;
                case 2:
                    Toast.makeText(HomeActivity.this,"Το E-Nurse Project αναπτύχθηκε στα πλάισια του κώδικα σχολείου της Μονάδας Αριστείας σε 2 μέρες.",Toast.LENGTH_LONG).show();

                    break;
                case 3:
                    //startActivity(new Intent(HomeActivity.this, SettingsActivity2.class));
                    break;
                case 4:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}