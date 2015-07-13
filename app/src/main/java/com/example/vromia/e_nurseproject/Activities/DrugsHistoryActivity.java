package com.example.vromia.e_nurseproject.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.DrugsHistoryAdapter;
import com.example.vromia.e_nurseproject.Utils.MyDrugsAdapter;

import java.util.Calendar;


/**
 * Created by Vromia on 17/12/2014.
 */
public class DrugsHistoryActivity extends FragmentActivity {

    private ListView lv;
    private HeathDatabase db;
    private Cursor cursor;
    private DrugsHistoryAdapter adapter;
    private MyDrugsAdapter myDrugs_adapter;

    private AlertDialog dialog = null;

    private Menu menu;
    private boolean isMyDrugs = true;

    FragmentManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.lvHistory);

        db = new HeathDatabase(DrugsHistoryActivity.this);
        cursor = db.getDistDrugs();

//        startManagingCursor(cursor);
        isMyDrugs = true;
        myDrugs_adapter = new MyDrugsAdapter(getApplicationContext(), cursor);
        lv.setAdapter(myDrugs_adapter);

        manager = getSupportFragmentManager();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.drugs_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] categories;

        switch (id) {
            case R.id.toggle:
                isMyDrugs = !isMyDrugs;
                if (isMyDrugs) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.drugs_menu, menu);
                    cursor = db.getDistDrugs();
                    myDrugs_adapter = new MyDrugsAdapter(DrugsHistoryActivity.this, cursor);
                    lv.setAdapter(myDrugs_adapter);
                } else {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.drugs_history_menu, menu);
                    cursor = db.getDrugsByRecent();
                    adapter = new DrugsHistoryAdapter(DrugsHistoryActivity.this, cursor);
                    lv.setAdapter(adapter);
                }
                break;
            case R.id.filtersDrugsCategory:
                categories = getResources().getStringArray(R.array.drugNames);

                builder.setTitle("����������");

                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {

                        String chosenCategory = categories[item];
                        cursor = db.getDrugsByCategory(chosenCategory);
                        refreshList(cursor);
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.filtersDrugsRecent:
                cursor = db.getDrugsByRecent();
                refreshList(cursor);
                break;
            case R.id.filtersDrugsDate:
                Calendar calendar = Calendar.getInstance();
                CalendarDatePickerDialog dateDialog = CalendarDatePickerDialog.newInstance(dateListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show(manager, "Tag");
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private CalendarDatePickerDialog.OnDateSetListener dateListener = new CalendarDatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int i, int i2, int i3) {
            String month, day;
            i2++;
            if (i2 < 10)
                month = "0" + i2;
            else
                month = String.valueOf(i2);
            if (i3 < 10)
                day = "0" + i3;
            else
                day = String.valueOf(i3);
            String date = i + "-" + month + "-" + day;
            cursor = db.getDrugsByDate(date);
            refreshList(cursor);
        }
    };

    public void refreshList(Cursor cursor) {
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

        if (PreferenceManager.getDefaultSharedPreferences(DrugsHistoryActivity.this).getBoolean("key_animations", false))
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}