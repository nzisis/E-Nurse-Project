package com.example.vromia.e_nurseproject.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    private LinearLayout llAccount, llDiseases;
    private ListView listview;
    private RadioGroup fylo;
    private Button btBack, btOk;
    private ImageButton btAdd;
    private RadioButton rb_male, rb_female;
    private String Sfylo = "";
    private HeathDatabase hdb;
    private Spinner sDoctors;

    private ArrayList<String> diseases;

    private int userID = -1;
    private int cuSuccess = -1;
    private int sex = -1;
    private String userName, userSurname, history;
    private int age, male, weight,height;
    private String menu;

    private static String user_details_url = "http://nikozisi.webpages.auth.gr/enurse/get_user_details.php";
    private static String create_user_url = "http://nikozisi.webpages.auth.gr/enurse/create_user.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_AGE = "age";
    private static final String TAG_HISTORY = "history";
    private static final String TAG_MALE = "male";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_CUSUCCESS = "success";
    private static final String TAG_ID = "userID";
    private static final String TAG_HEIGHT = "height";

    private JSONParser jsonParser;
    private ProgressDialog pDialog;

    private SharedPrefsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        manager = new SharedPrefsManager(UserDetailsActivity.this);
        diseases = new ArrayList<>();

        initUI();
        userID = getIntent().getIntExtra("userID", -1);
        menu=getIntent().getStringExtra("Menu");
        if (userID != -1) {
            userName = getIntent().getStringExtra("userName");
            userSurname = getIntent().getStringExtra("userSurname");
            manager.startEditing();
            manager.setPrefsUserID(userID);
            manager.commit();
            //Log.i("Surname",userSurname);
        } else {
            if (manager.getPrefsUserID() != -1) {
                userName = manager.getPrefsUsername();
                userSurname = manager.getPrefsSurname();
            }
        }


        if(menu!=null){
            fillUIWithValues();
        }

        setUpUI();
        initListeners();

    }

    private void initUI() {
        llAccount = (LinearLayout) findViewById(R.id.llAccount);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);


//        listview = (ListView) findViewById(R.id.lvDiseaseHistory);
//        setListViewHeightBasedOnChildren(listview);

        llDiseases = (LinearLayout) findViewById(R.id.llDiseases);

        onoma = (EditText) findViewById(R.id.onoma);
        etSurname = (EditText) findViewById(R.id.etsurname);
        ilikia = (EditText) findViewById(R.id.ilikia);

        ypsos = (EditText) findViewById(R.id.ypsos);
        baros = (EditText) findViewById(R.id.baros);
//        istorikoPathiseon = (EditText) findViewById(R.id.istorikoPathiseon);
        fylo = (RadioGroup) findViewById(R.id.fylo);
        sDoctors = (Spinner) findViewById(R.id.spDoctors);

        btBack = (Button) findViewById(R.id.btBack);
        btOk = (Button) findViewById(R.id.btOk);
        btAdd = (ImageButton) findViewById(R.id.ibAddHistory);

        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
    }


    private void fillUIWithValues() {

        llAccount.setVisibility(View.GONE);

        onoma.setText(manager.getPrefsOnoma());
        etSurname.setText(manager.getPrefsSurname());
        ilikia.setText(manager.getPrefsIlikia() + "");
        ypsos.setText(manager.getYpsos() + "");
        baros.setText(manager.getPrefsBaros() + "");
        if (manager.getPrefsFylo().equals("Άνδρας")) {
            rb_male.setChecked(true);
        } else {
            rb_female.setChecked(true);
        }

        String d[] = manager.getPrefsIstorikoPathiseon().split("-");
        for (String i : d) {
            diseases.add(i);
        }
        addDiseases();
    }

    private void setUpUI() {
        hdb = new HeathDatabase(UserDetailsActivity.this);
        ArrayList<String> full_names = hdb.getDoctorsFullName();
        hdb.close();

        jsonParser = new JSONParser();

        final ArrayAdapter adapter = new ArrayAdapter(UserDetailsActivity.this, R.layout.spinner_item, R.id.tvSpinnerCategories, full_names);
        sDoctors.setAdapter(adapter);


        if (userID != -1 ) {
            llAccount.setVisibility(View.GONE);

            onoma.setText(userName);
            etSurname.setText(userSurname);
            new GetUser().execute();

        }




        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                diseases.add("Nikos");
                diseases.add("");
                addDiseases();
            }
        });
//        listview.setAdapter(new DiseaseAdapter(UserDetailsActivity.this, diseases));


    }


    private void addDiseases() {
        llDiseases.removeAllViewsInLayout();
        for (int i = 0; i < diseases.size(); i++) {

            final int pos = i;
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.list_item_disease_history, null);
            /**
             * getting id of row.xml
             */
            EditText diseaseName = (EditText) mLinearView
                    .findViewById(R.id.etDiseaseName);
            diseaseName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    diseases.set(pos, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ImageButton imageButton = (ImageButton) mLinearView
                    .findViewById(R.id.ibDelete);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diseases.remove(pos);
                    addDiseases();
                }
            });
            /**
             * set item into row
             */
            final String fName = diseases.get(i);
            diseaseName.setText(fName);

            /**
             * add view in top linear
             */

            llDiseases.addView(mLinearView);

            /**
             * get item row on click
             *
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(UserDetailsActivity.this, "Clicked item;" + fName,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initListeners() {
        fylo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (rb_male.isChecked()) {
                    Sfylo = "Άνδρας";
                    sex = 1;
                } else if (rb_female.isChecked()) {
                    Sfylo = "Γυναίκα";
                    sex = 0;
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
                String Sonoma, Silikia, Sypsos, Sbaros, SistorikoPathiseon, SUsername, SPassword, Ssurname;
                boolean flag = true;

                Sonoma = String.valueOf(onoma.getText());
                Silikia = String.valueOf(ilikia.getText());
                Sypsos = String.valueOf(ypsos.getText());
                Sbaros = String.valueOf(baros.getText());
                Ssurname = String.valueOf(etSurname.getText());
                SistorikoPathiseon = "";
                if(menu!=null && SistorikoPathiseon.length()>0){
                    for (int i = 0; i < diseases.size(); i++) {
                        if (!diseases.get(i).trim().equals("")) {
                            SistorikoPathiseon += diseases.get(i) + "-";
                        }
                    }

                    SistorikoPathiseon = SistorikoPathiseon.substring(0, SistorikoPathiseon.length() - 1);
                 }


                Log.i("nikos", SistorikoPathiseon);
/*
                if (Silikia.equals("")) {
                    Silikia = "0";
                }
                if (Sypsos.equals("")) {
                    Sypsos = "0";
                }
                if (Sbaros.equals("")) {
                    Sbaros = "0";
                }*/


                SharedPrefsManager spmanager = new SharedPrefsManager(UserDetailsActivity.this);


                Log.i("nikos", "userid = " + userID + "   prefs=" + manager.getPrefsUserID());
                if (userID == -1 && manager.getPrefsUserID() == -1) {
                    SUsername = String.valueOf(etUsername.getText());
                    SPassword = String.valueOf(etPassword.getText());

                    if (SUsername.equals("")) {
                        Toast.makeText(UserDetailsActivity.this, "Παρακαλώ γράψτε τα πεδία Όνομα Λογαριασμού - Κωδικός", Toast.LENGTH_LONG).show();
                        flag = false;
                    } else {
                        flag = true;
                        spmanager.startEditing();
                        spmanager.setPrefsUsername(SUsername);
                        spmanager.setPrefsPassword(SPassword);
                        spmanager.commit();
                        new createUser().execute();

                    }
                }


                spmanager.startEditing();

                spmanager.setPrefsOnoma(Sonoma);
                spmanager.setPrefsSurname(Ssurname);
                spmanager.setPrefsIlikia(Integer.parseInt(Silikia));
//                spmanager.setPrefsYpsos(Float.parseFloat(Sypsos));
                spmanager.setPrefsBaros(Float.parseFloat(Sbaros));
                spmanager.setPrefsYpsos(Float.parseFloat(Sypsos));
                spmanager.setPrefsIstorikoPathiseon(SistorikoPathiseon);
                spmanager.setPrefsFylo(Sfylo);


                spmanager.commit();


                if (!spmanager.getPrefsStart()) {
                    spmanager.startEditing();
                    spmanager.setPrefsStart(true);
                    spmanager.commit();
                }

                if (flag) {
                    startActivity(new Intent(UserDetailsActivity.this, HomeActivity.class));
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
                    Log.i("Success", "success");
                    age = json.getInt(TAG_AGE);
                    male = json.getInt(TAG_MALE);
                    history = json.getString(TAG_HISTORY);
                    weight = json.getInt(TAG_WEIGHT);
                    height=json.getInt(TAG_HEIGHT);


                    Log.i("values", age + " - " + male + " - " + history + " - " + weight);


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
            ilikia.setText(age + "");
            if (male == 1) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }

//            istorikoPathiseon.setText(history);
            baros.setText(weight + "");
            ypsos.setText(height+"");

        }


    }


    class createUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair("username", etUsername.getText().toString()));
            params.add(new BasicNameValuePair("password", etPassword.getText().toString()));
            params.add(new BasicNameValuePair("name", onoma.getText().toString()));
            params.add(new BasicNameValuePair("surname", etSurname.getText().toString()));
            params.add(new BasicNameValuePair("age", Integer.parseInt(ilikia.getText().toString()) + ""));
            params.add(new BasicNameValuePair("male", sex + ""));
//            params.add(new BasicNameValuePair("history", istorikoPathiseon.getText().toString()));
            params.add(new BasicNameValuePair("weight", Float.parseFloat(baros.getText().toString()) + ""));
            params.add(new BasicNameValuePair("height", Float.parseFloat(ypsos.getText().toString()) + ""));

            String doctor_full_name = sDoctors.getSelectedItem().toString();
            String tokens[] = doctor_full_name.split(" ");

            params.add(new BasicNameValuePair("doctor_name", tokens[0]));
            params.add(new BasicNameValuePair("doctor_surname", tokens[1]));

            JSONObject json = jsonParser.makeHttpRequest(create_user_url, "POST", params);

            try {
                cuSuccess = json.getInt(TAG_SUCCESS);
                int userID = json.getInt(TAG_ID);
                SharedPrefsManager manager = new SharedPrefsManager(UserDetailsActivity.this);
                manager.startEditing();
                manager.setPrefsUserID(userID);
                manager.commit();


                Log.i("USERID", userID + "");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

        if (PreferenceManager.getDefaultSharedPreferences(UserDetailsActivity.this).getBoolean("key_animations", false))
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }



}
