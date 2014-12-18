package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
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
}
