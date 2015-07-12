package com.example.vromia.e_nurseproject.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.Data.DrugsItem;
import com.example.vromia.e_nurseproject.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Vromia on 17/12/2014.
 */
public class DrugsActivity extends FragmentActivity {
    private RadioButton bMorn;
    private RadioButton bNoon;
    private RadioButton bNight;
    private RadioGroup rGroup;
    private ImageButton bDate;
    private Button bBack;
    private Button bOk;
    private EditText quantField,etCause;
    private Spinner spinner;
    private CalendarDatePickerDialog cdate;//gui for showing date
    private String date, tod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);


        initUI();
        initListeners();
        bMorn.setActivated(true);

        String categories[] = getResources().getStringArray(R.array.drugNames);
        ArrayList<String> finalCategories = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            finalCategories.add(categories[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(DrugsActivity.this, R.layout.spinner_item, R.id.tvSpinnerCategories, finalCategories);
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
        tod = "Πρωί";
        bMorn.setChecked(true);
    }

    public void initUI() {
        bMorn = (RadioButton) findViewById(R.id.rbMorn);
        bNoon = (RadioButton) findViewById(R.id.rbNoon);
        bNight = (RadioButton) findViewById(R.id.rbNight);
        rGroup = (RadioGroup) findViewById(R.id.rdtod);
        bDate = (ImageButton) findViewById(R.id.imbtDate);
        quantField = (EditText) findViewById(R.id.etDuration);
        etCause=(EditText) findViewById(R.id.etCause);
        spinner = (Spinner) findViewById(R.id.spChooseEx);
        bBack = (Button) findViewById(R.id.btBack);
        bOk = (Button) findViewById(R.id.btOk);


        bDate.setImageResource(R.drawable.calendar);

    }


    public void initListeners() {
        bMorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tod = "Πρωί";
            }
        });

        bNoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tod = "Μεσημέρι";
            }
        });

        bNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tod = "Βράδυ";
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
                Double quantity = 10.0;
                String exName = spinner.getSelectedItem().toString();
                try {
                    quantity = Double.valueOf(quantField.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(DrugsActivity.this, "Plz insert a numeric value", Toast.LENGTH_LONG);
                }
                String cause=etCause.getText().toString();
                HeathDatabase db = new HeathDatabase(DrugsActivity.this);//instance of current database
                DrugsItem item = new DrugsItem(exName, date, quantity, tod,cause);
                Log.i("msg", exName + " " + date + " " + quantity + " " + tod);
                db.InsertDrugs(item);
                db.close();
                Toast.makeText(DrugsActivity.this, "Εισαγωγή επιτυχής", Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    private CalendarDatePickerDialog.OnDateSetListener listener = new CalendarDatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int i, int i2, int i3) {
            String month, day;
            i2++;
            if (i2 < 10) {
                month = "0" + i2;
            } else {
                month = String.valueOf(i2);
            }
            if (i3 < 10) {
                day = "0" + i3;
            } else {
                day = String.valueOf(i3);
            }
            date = i + "-" + month + "-" + day;
        }
    };
}