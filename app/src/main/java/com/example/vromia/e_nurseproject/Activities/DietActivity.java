package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
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
    private ImageButton dateBut;
    private ImageButton hourBut;

    private CalendarDatePickerDialog cdate;//gui for showing date
    private RadialTimePickerDialog timeDialog;//gui for showing date
    private String date,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        initUI();
        initListeners();

        //TODO how to add to spinner a string of array
        String categories[]=Resources.getSystem().getStringArray(R.array.categories);
        ArrayAdapter adapter=new ArrayAdapter(DietActivity.this,R.layout.activity_diet,categories);
        spinner.setAdapter(adapter);

        Calendar  c = Calendar.getInstance();
        cdate= CalendarDatePickerDialog.newInstance(listener,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        //Initialize variable date to current date
        String day = c.get(Calendar.DAY_OF_MONTH) + "";
        String month = (c.get(Calendar.MONTH)+1) + "";
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        if (c.get(Calendar.MONTH)+1 < 10) {
            month = "0" + (c.get(Calendar.MONTH)+1);
        }

        date = c.get(Calendar.YEAR) + "-" + month + "-" + day;



        timeDialog = RadialTimePickerDialog.newInstance(timelistener, c.getTime().getHours(), c.getTime().getMinutes(), true);


        //TODO initialize variable hour to current

    }

    public void initUI( ){
        bHour = (ImageButton) findViewById(R.id.imbtHour);
        bDate = (ImageButton) findViewById(R.id.imbtDate);
        bBack = (Button) findViewById(R.id.btBack);
        bOk = (Button) findViewById(R.id.btOk);
        quantField = (EditText) findViewById(R.id.etQuant);
        hourBut = (ImageButton) findViewById(R.id.imbtHour);
        spinner = (Spinner) findViewById(R.id.spChooseFood);
        dateBut = (ImageButton) findViewById(R.id.imbtDate);
    }

    public void initListeners(){
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
                double quantity=-1;
                try {
                  quantity = Double.valueOf(quantField.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(DietActivity.this,"Plz insert a numeric value",Toast.LENGTH_LONG);
                }
                String foodName = spinner.getSelectedItem().toString();

                HeathDatabase db=new HeathDatabase(DietActivity.this);//instance of current database
                Log.i("Msg",date);
                DietItem item=new DietItem(foodName,date,quantity,hour);
                db.InsertDiet(item);
                db.close();


            }
        });
    }


    private CalendarDatePickerDialog.OnDateSetListener listener = new CalendarDatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int i, int i2, int i3) {
            String month, day;
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
            //etDate.setText(reverseDate());
        }
    };

    private RadialTimePickerDialog.OnTimeSetListener timelistener=new RadialTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
          //Check this
           hour= i+"-"+i2;

        }
    };



}
