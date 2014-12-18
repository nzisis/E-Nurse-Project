package com.example.vromia.e_nurseproject.Activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vromia.e_nurseproject.R;

/**
 * Created by Vromia on 17/12/2014.
 */
public class HomeActivity extends Activity {

    private Button diet, workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initListeners();
    }

    private void initUI() {
        diet = (Button) findViewById(R.id.diet);
        workout = (Button) findViewById(R.id.workout);
    }

    private void initListeners() {
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DietActivity.class);
                startActivity(i);
            }
        });


        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, WorkoutActivity.class);
                startActivity(i);
            }
        });

    }
}