package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.Data.WorkoutItem;
import com.example.vromia.e_nurseproject.R;

import java.util.ArrayList;


/**
 * Created by Vromia on 17/12/2014.
 */
public class WorkoutActivity extends Activity {
    private Button bMorn;
    private Button bNoon;
    private Button bNight;
    private ImageButton bDate;
    private Button bBack;
    private Button bOk;
    private EditText quantField;
    private Spinner spinner;
    private ImageButton dateBut;
    private CalendarDatePickerDialog cdate;//gui for showing date
    private String date,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        initUI();
        initListeners();
        bMorn.setActivated(true);

        String categories[]=getResources().getStringArray(R.array.workoutNames);
        ArrayList<String> finalCategories=new ArrayList<>();
        for(int i=0; i<categories.length; i++){
            finalCategories.add(categories[i]);
        }
        ArrayAdapter adapter=new ArrayAdapter(WorkoutActivity.this,R.layout.spinner_item,R.id.tvSpinnerCategories,finalCategories);
        spinner.setAdapter(adapter);



    }

    public void initUI( ){
        bMorn  = (Button) findViewById(R.id.rbMorn); // Na kanoume activate kapoio apo default gia na apofygoume errors an den epileksei o xrhsths kati?
        bNoon  = (Button) findViewById(R.id.rbNoon);
        bNight  = (Button) findViewById(R.id.rbNight);
        bDate = (ImageButton) findViewById(R.id.imbtDate);
        bBack = (Button) findViewById(R.id.btBack);
        bOk = (Button) findViewById(R.id.btOk);
        quantField = (EditText) findViewById(R.id.etDuration);
        spinner = (Spinner) findViewById(R.id.spChooseEx);
        dateBut = (ImageButton) findViewById(R.id.imbtDate);
    }


    public void initListeners(){
        bMorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bNoon.setActivated(false);
                bNoon.setSelected(false);
                bNight.setActivated(false);
                bNight.setPressed(false);
            }
        });

        bNoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bMorn.setActivated(false);
                bNight.setActivated(false);
            }
        });

        bNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bMorn.setActivated(false);
                bNoon.setActivated(false);
            }
        });

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cdate.show(getSupportFragmentManager(), "Calendar Dialog");
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
                String exName = spinner.getSelectedItem().toString();
                Double quantity = Double.valueOf(quantField.getText().toString());
                String tod;
                if ( bMorn.isActivated())
                    tod="Πρωί";
                else if ( bNoon.isActivated())
                    tod="Μεσημέρι";
                else if ( bNight.isActivated())
                    tod="Βράδυ";
                HeathDatabase db=new HeathDatabase(WorkoutActivity.this);//instance of current database
                Log.i("Msg", date);
                WorkoutItem item=new WorkoutItem(exName,date,quantity,hour);
                db.InsertWorkout(item);
                db.close();
            }
        });
    }
}
