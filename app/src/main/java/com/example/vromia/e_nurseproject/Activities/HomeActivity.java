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
                    startActivity(new Intent(HomeActivity.this, UserDetailsActivity.class));
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
}
