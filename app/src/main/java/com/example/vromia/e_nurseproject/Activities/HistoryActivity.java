package com.example.vromia.e_nurseproject.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.HistoryAdapter;

import java.util.Calendar;


/**
 * Created by Vromia on 17/12/2014.
 */
public class HistoryActivity extends FragmentActivity {

    private ListView lv;
    private HeathDatabase db;
    private Cursor cursor;
    private HistoryAdapter adapter;

    private AlertDialog dialog = null;

    private Menu menu;
    private boolean isDiet = true;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.lvHistory);

        db = new HeathDatabase(HistoryActivity.this);
        cursor = db.getDietByRecent();

//        startManagingCursor(cursor);
        isDiet = true;
        adapter = new HistoryAdapter(getApplicationContext(), cursor, isDiet);
        lv.setAdapter(adapter);

        manager = getSupportFragmentManager();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.diet_menu, menu);
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
                isDiet = !isDiet;
                if (isDiet) {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.diet_menu, menu);
                    cursor = db.getDietByRecent();
                    adapter = new HistoryAdapter(HistoryActivity.this, cursor, isDiet);
                    lv.setAdapter(adapter);
                } else {
                    menu.clear();
                    getMenuInflater().inflate(R.menu.workout_menu, menu);
                    cursor = db.getWorkoutByRecent();
                    adapter = new HistoryAdapter(HistoryActivity.this, cursor, isDiet);
                    lv.setAdapter(adapter);
                }
                break;
            case R.id.filtersDietCategory:
                categories = getResources().getStringArray(R.array.foodNames);

                builder.setTitle("Categories");

                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {

                        String chosenCategory = categories[item];
                        cursor = db.getDietByCategory(chosenCategory);
                        refreshList(cursor);
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.filtersDietRecent:
                cursor = db.getDietByRecent();
                refreshList(cursor);
                break;
            case R.id.filtersDietDate:
                Calendar calendar = Calendar.getInstance();
                CalendarDatePickerDialog dateDialog = CalendarDatePickerDialog.newInstance(dateListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show(manager, "Tag");
                break;
            case R.id.filtersWorkoutCategory:
                categories = getResources().getStringArray(R.array.workoutNames);

                builder.setTitle("Categories");

                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {

                        String chosenCategory = categories[item];
                        cursor = db.getWorkoutByCategory(chosenCategory);
                        refreshList(cursor);
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.filtersWorkoutDate:
                calendar = Calendar.getInstance();
                dateDialog = CalendarDatePickerDialog.newInstance(dateListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show(manager, "Tag");
                break;
            case R.id.filtersWorkoutPeriod:
                break;
            case R.id.filtersWorkoutRecent:
                cursor = db.getWorkoutByRecent();
                refreshList(cursor);
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
            if (isDiet) {
                cursor = db.getDietByDate(date);
                refreshList(cursor);
            } else {
                cursor = db.getWorkoutByDate(date);
                refreshList(cursor);
            }
        }
    };

    public void refreshList(Cursor cursor) {
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }


}
