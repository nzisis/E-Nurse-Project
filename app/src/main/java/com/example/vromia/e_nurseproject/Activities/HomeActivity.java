package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.vromia.e_nurseproject.Data.GridItem;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.GridAdapter;

import java.util.ArrayList;

/**
 * Created by Vromia on 2/6/2015.
 */
public class HomeActivity extends Activity {

    private GridView gridView;
    private ArrayList<GridItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridView = (GridView) findViewById(R.id.gridview);
        items = new ArrayList<>();

        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_food), "Nutrition"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_excercise), "Excercise"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_profile), "Profile"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_history), "History"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_settings), "Settings"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_exit), "Exit"));


        GridAdapter adapter = new GridAdapter(HomeActivity.this, R.layout.grid_item_menu, items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                }
            }
        });

    }
}
