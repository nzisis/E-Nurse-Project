package com.example.vromia.e_nurseproject.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.SharedPrefsManager;


/**
 * Created by Vromia on 17/12/2014.
 */
public class UserDetailsActivity extends Activity {

    private EditText onoma, ilikia, ypsos, baros, istorikoPathiseon,email;
    private RadioGroup fylo;
    private Button btBack, btOk;
    private RadioButton rb_male, rb_female;
    private String Sfylo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        initUI();
        initListeners();
    }

    private void initUI() {
        onoma = (EditText) findViewById(R.id.onoma);
        ilikia = (EditText) findViewById(R.id.ilikia);
        ypsos = (EditText) findViewById(R.id.ypsos);
        baros = (EditText) findViewById(R.id.baros);
        istorikoPathiseon = (EditText) findViewById(R.id.istorikoPathiseon);
        email=(EditText) findViewById(R.id.email);
        fylo = (RadioGroup) findViewById(R.id.fylo);
        btBack = (Button) findViewById(R.id.btBack);
        btOk = (Button) findViewById(R.id.btOk);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
    }

    private void initListeners() {
        fylo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (rb_male.isChecked()) {
                    Sfylo="Άνδρας";
                } else if (rb_female.isChecked()) {
                    Sfylo="Γυναίκα";
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sonoma, Silikia, Sypsos, Sbaros, SistorikoPathiseon,Semail;



                Sonoma = String.valueOf(onoma.getText());
                Silikia = String.valueOf(ilikia.getText());
                Sypsos = String.valueOf(ypsos.getText());
                Sbaros = String.valueOf(baros.getText());
                Semail = String.valueOf(email.getText());

                if(Silikia.equals("")){
                    Silikia="0";
                }
                if(Sypsos.equals("")){
                    Sypsos="0";
                }
                if(Sbaros.equals("")){
                    Sbaros="0";
                }

                SistorikoPathiseon = String.valueOf(istorikoPathiseon.getText());
                SharedPrefsManager spmanager=new SharedPrefsManager(UserDetailsActivity.this);
                spmanager.startEditing();
                //setaro
                spmanager.setPrefsOnoma(Sonoma);
                spmanager.setPrefsIlikia(Integer.parseInt(Silikia));
                spmanager.setPrefsYpsos(Float.parseFloat(Sypsos));
                spmanager.setPrefsBaros(Float.parseFloat(Sbaros));
                spmanager.setPrefsIstorikoPathiseon(SistorikoPathiseon);
                spmanager.setPrefsEmail(Semail);
                spmanager.setPrefsFylo(Sfylo);

                spmanager.commit();


                if(!spmanager.getPrefsStart()){
                    spmanager.startEditing();
                    spmanager.setPrefsStart(true);
                    spmanager.commit();
                    startActivity(new Intent(UserDetailsActivity.this,HomeActivity.class));

                }else{

                    finish();
                }





/*
                Log.i("Onoma",spmanager.getPrefsOnoma());
                Log.i("ilikia",spmanager.getPrefsIlikia()+"");
                Log.i("ypsos",spmanager.getYpsos()+"");
                Log.i("Baros",spmanager.getPrefsBaros()+"");
                Log.i("Istoriko",spmanager.getPrefsIstorikoPathiseon());
                Log.i("sex",spmanager.getPrefsFylo());
                Log.i("Email",spmanager.getPrefsEmail());
*/
            }
        });
    }
}
