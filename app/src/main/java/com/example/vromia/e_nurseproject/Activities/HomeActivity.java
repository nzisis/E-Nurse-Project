package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

        gridView=(GridView) findViewById(R.id.gridview);
        items=new ArrayList<>();

        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));
        items.add(new GridItem(BitmapFactory.decodeResource(this.getResources(), R.drawable.clock),"mhsto"));


        GridAdapter adapter=new GridAdapter(HomeActivity.this,R.layout.grid_item_menu,items);
        gridView.setAdapter(adapter);

    }
}
