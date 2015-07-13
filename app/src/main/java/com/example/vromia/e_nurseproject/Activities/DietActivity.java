package com.example.vromia.e_nurseproject.Activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.example.vromia.e_nurseproject.Data.DietItem;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Vromia on 17/12/2014.
 */
public class DietActivity extends FragmentActivity {

    private ImageButton bHour;
    private ImageButton bDate;
    private Button bBack;
    private Button bOk;
    private EditText quantField;
    private Spinner spinner;
    private CalendarDatePickerDialog cdate;//gui for showing date
    private RadialTimePickerDialog timeDialog;//gui for showing date
    private String date, hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        initUI();
        initListeners();


        String categories[] = getResources().getStringArray(R.array.foodNames);
        ArrayList<String> finalCategories = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            finalCategories.add(categories[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(DietActivity.this, R.layout.spinner_item, R.id.tvSpinnerCategories, finalCategories);
        spinner.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        cdate = CalendarDatePickerDialog.newInstance(listener,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        //Initialize variable date to current date
        String day = c.get(Calendar.DAY_OF_MONTH) + "";
        String month = (c.get(Calendar.MONTH) + 1) + "";
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        if (c.get(Calendar.MONTH) + 1 < 10) {
            month = "0" + (c.get(Calendar.MONTH) + 1);
        }

        date = c.get(Calendar.YEAR) + "-" + month + "-" + day;

        timeDialog = RadialTimePickerDialog.newInstance(timelistener, c.getTime().getHours(), c.getTime().getMinutes(), true);

        // Initialize variable hour to current hour
        int temp_hour = c.get(Calendar.HOUR_OF_DAY);
        int temp_min = c.get(Calendar.MINUTE);
        String temp_hour_fixed, temp_min_fixed;
        // TODO This block actually does nothing atm...?
        if (temp_hour < 10) {
            temp_hour_fixed = "0" + String.valueOf(temp_hour);
        } else {
            temp_hour_fixed = String.valueOf(temp_hour);
        }
        if (temp_min < 10) {
            temp_min_fixed = "0" + String.valueOf(temp_min);
        } else {
            temp_min_fixed = String.valueOf(temp_min);
        }
        hour = temp_hour_fixed + ":" + temp_min_fixed;

    }

    public void initUI() {
        bHour = (ImageButton) findViewById(R.id.imbtHour);
        bDate = (ImageButton) findViewById(R.id.imbtDate);
        bBack = (Button) findViewById(R.id.btBack);
        bOk = (Button) findViewById(R.id.btOk);
        quantField = (EditText) findViewById(R.id.etQuant);
        spinner = (Spinner) findViewById(R.id.spChooseFood);

        bHour.setImageResource(R.drawable.clock);
        bDate.setImageResource(R.drawable.calendar);


    }

    public void initListeners() {
        bHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show(getSupportFragmentManager(), "Nikos");
            }
        });
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdate.show(getSupportFragmentManager(), "Calendar Dialog");
            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double quantity = 1;
                try {
                    quantity = Double.valueOf(quantField.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(DietActivity.this, "Plz insert a numeric value", Toast.LENGTH_LONG);

                }
                String foodName = spinner.getSelectedItem().toString();

                HeathDatabase db = new HeathDatabase(DietActivity.this);//instance of current database

                DietItem item = new DietItem(foodName, date, quantity, hour);
                db.InsertDiet(item);
                db.close();
                Toast.makeText(DietActivity.this, "Εισαγωγή επιτυχής", Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }


    private CalendarDatePickerDialog.OnDateSetListener listener = new CalendarDatePickerDialog.OnDateSetListener() {
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
            date = i + "-" + month + "-" + day;
        }
    };

    private RadialTimePickerDialog.OnTimeSetListener timelistener = new RadialTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
            String hours, mins;
            if (i < 10)
                hours = "0" + i;
            else
                hours = String.valueOf(i);
            if (i2 < 10)
                mins = "0" + i2;
            else
                mins = String.valueOf(i2);
            hour = hours + ":" + mins;
            Log.i("msg", hour);
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

        if (PreferenceManager.getDefaultSharedPreferences(DietActivity.this).getBoolean("key_animations", false))
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
