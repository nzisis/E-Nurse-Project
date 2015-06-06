package com.example.vromia.e_nurseproject.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.JSONParser;
import com.example.vromia.e_nurseproject.Utils.SharedPrefsManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vromia on 17/12/2014.
 */
public class UserDetailsActivity extends Activity {

    private EditText onoma, ilikia, ypsos, baros, istorikoPathiseon, email, etSurname, etUsername, etPassword;
    private LinearLayout llAccount;
    private RadioGroup fylo;
    private Button btBack, btOk;
    private RadioButton rb_male, rb_female;
    private String Sfylo = "";
    private HeathDatabase hdb;
    private Spinner sDoctors;

    private int userID = -1;
    private String userName,userSurname,history;
    private int age,male,weight;

    private static String user_details_url = "http://nikozisi.webpages.auth.gr/enurse/get_user_details.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_AGE = "age";
    private static final String TAG_HISTORY = "history";
    private static final String TAG_MALE = "male";
    private static final String TAG_WEIGHT = "weight";

    private JSONParser jsonParser;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        initUI();
        userID = getIntent().getIntExtra("userID", -1);
        if(userID!=-1){
            userName=getIntent().getStringExtra("userName");
            userSurname=getIntent().getStringExtra("userSurname");
            //Log.i("Surname",userSurname);
        }

        setUpUI();
        initListeners();
    }

    private void initUI() {
        llAccount = (LinearLayout) findViewById(R.id.llAccount);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);


        onoma = (EditText) findViewById(R.id.onoma);
        etSurname = (EditText) findViewById(R.id.etsurname);
        ilikia = (EditText) findViewById(R.id.ilikia);

        ypsos = (EditText) findViewById(R.id.ypsos);
        baros = (EditText) findViewById(R.id.baros);
        istorikoPathiseon = (EditText) findViewById(R.id.istorikoPathiseon);
        fylo = (RadioGroup) findViewById(R.id.fylo);
        sDoctors = (Spinner) findViewById(R.id.spDoctors);

        btBack = (Button) findViewById(R.id.btBack);
        btOk = (Button) findViewById(R.id.btOk);

        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
    }


    private void setUpUI() {
        hdb = new HeathDatabase(UserDetailsActivity.this);
        ArrayList<String> full_names = hdb.getDoctorsFullName();
        hdb.close();


        ArrayAdapter adapter = new ArrayAdapter(UserDetailsActivity.this, R.layout.spinner_item, R.id.tvSpinnerCategories, full_names);
        sDoctors.setAdapter(adapter);


        if (userID != -1) {
            llAccount.setVisibility(View.GONE);

            onoma.setText(userName);

            etSurname.setText(userSurname);


            jsonParser = new JSONParser();
            new GetUser().execute();

        }


    }


    private void initListeners() {
        fylo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (rb_male.isChecked()) {
                    Sfylo = "Άνδρας";
                } else if (rb_female.isChecked()) {
                    Sfylo = "Γυναίκα";
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
                String Sonoma, Silikia, Sypsos, Sbaros, SistorikoPathiseon, Semail;


                Sonoma = String.valueOf(onoma.getText());
                Silikia = String.valueOf(ilikia.getText());
                Sypsos = String.valueOf(ypsos.getText());
                Sbaros = String.valueOf(baros.getText());
                // Semail = String.valueOf(email.getText());

                if (Silikia.equals("")) {
                    Silikia = "0";
                }
                if (Sypsos.equals("")) {
                    Sypsos = "0";
                }
                if (Sbaros.equals("")) {
                    Sbaros = "0";
                }

                SistorikoPathiseon = String.valueOf(istorikoPathiseon.getText());
                SharedPrefsManager spmanager = new SharedPrefsManager(UserDetailsActivity.this);
                spmanager.startEditing();
                //setaro
                spmanager.setPrefsOnoma(Sonoma);
                spmanager.setPrefsIlikia(Integer.parseInt(Silikia));
                spmanager.setPrefsYpsos(Float.parseFloat(Sypsos));
                spmanager.setPrefsBaros(Float.parseFloat(Sbaros));
                spmanager.setPrefsIstorikoPathiseon(SistorikoPathiseon);
                //   spmanager.setPrefsEmail(Semail);
                spmanager.setPrefsFylo(Sfylo);

                spmanager.commit();


                if (!spmanager.getPrefsStart()) {
                    spmanager.startEditing();
                    spmanager.setPrefsStart(true);
                    spmanager.commit();
                    startActivity(new Intent(UserDetailsActivity.this, HomeActivity.class));

                } else {

                    finish();
                }




            }
        });
    }


    //AsyncTack < params,progress,result
    class GetUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserDetailsActivity.this);
            pDialog.setMessage("Αντιστοίχηση Στοιχείων. Παρακαλώ Περιμένετε");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        //Check user starting background thread
        @Override
        protected String doInBackground(String... args) {

            //Building Parameters to pass to php script check_user
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("patientID", userID + ""));


            //Getting JSON object passing  the url, the params, and the method that you want to pass the params
            JSONObject json = jsonParser.makeHttpRequest(user_details_url, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.i("Success","success");
                     age = json.getInt(TAG_AGE);
                     male = json.getInt(TAG_MALE);
                     history = json.getString(TAG_HISTORY);
                     weight = json.getInt(TAG_AGE);

                    Log.i("values",age +" - "+ male + " - "+history + " - "+weight);



                } else {
                    Log.i("UserSuccess", "Fail");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
             pDialog.dismiss();
            ilikia.setText(age+"");
            if (male == 1) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }

            istorikoPathiseon.setText(history);
            baros.setText(weight+"");

        }


    }
}
