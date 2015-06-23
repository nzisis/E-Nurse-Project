package com.example.vromia.e_nurseproject.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vromia.e_nurseproject.Data.DoctorItem;
import com.example.vromia.e_nurseproject.Data.HeathDatabase;
import com.example.vromia.e_nurseproject.R;
import com.example.vromia.e_nurseproject.Utils.JSONParser;
import com.example.vromia.e_nurseproject.Utils.SharedPrefsManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {


    private EditText etUsername, etPassword;
    private Button bConnect, bCreateUser;
    private int userID;
    private String userName, userSuname;
    private int success, dsuccess;
    private HeathDatabase hdb;

    private JSONParser jsonParser;
    private JSONArray jsonArray;

    private static String url = "http://nikozisi.webpages.auth.gr/enurse/check_user.php";
    private static String doctors_url = "http://nikozisi.webpages.auth.gr/enurse/get_doctors.php";

    private static final String TAG_CHECKSUCCESS = "success";
    private static final String TAG_USERID = "userid";
    private static final String TAG_DOCTORS = "doctors";
    private static final String TAG_DOCTORSUCCESS = "success";
    private static final String TAG_DOCTORID = "id";
    private static final String TAG_DOCTORNAME = "name";
    private static final String TAG_DOCTORSURNAME = "surname";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initUI();
        initBasicVariables();
        initListeners();

    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }


    private void initBasicVariables() {

        jsonParser = new JSONParser();
        jsonArray = null;
        userID = -1;
        success = -1;
        dsuccess = -1;
        hdb = new HeathDatabase(LoginActivity.this);
        userName = userSuname = "";


        if (haveNetworkConnection()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<NameValuePair> params = new ArrayList<>();
                    JSONObject jsonObject = jsonParser.makeHttpRequest(doctors_url, "GET", params);

                    try {
                        dsuccess = jsonObject.getInt(TAG_DOCTORSUCCESS);

                        if (dsuccess == 1) {

                            jsonArray = jsonObject.getJSONArray(TAG_DOCTORS);
                            Log.i("Length", jsonArray.length() + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);

                                int id = c.getInt(TAG_DOCTORID);

                                String name = c.getString(TAG_DOCTORNAME);
                                String surname = c.getString(TAG_DOCTORSURNAME);
                                Log.i("DoctorName", name);

                                DoctorItem doctor = new DoctorItem(id, name, surname);
                                hdb.InsertDoctor(doctor);
                            }
                            // hdb.showDoctors();
                            hdb.close();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }).start();

        } else {
            Toast.makeText(LoginActivity.this, "Παρακαλώ συνδεθείτε στο Διαδίκτυο", Toast.LENGTH_LONG).show();
        }
    }

    private void initListeners() {
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //execute the php script and see the result via json format
                if(haveNetworkConnection()){
                    new CheckUser().execute();
                }else{
                    Toast.makeText(LoginActivity.this, "Παρακαλώ συνδεθείτε στο Διαδίκτυο", Toast.LENGTH_LONG).show();
                }

            }
        });

        bCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveNetworkConnection()) {
                    Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Παρακαλώ συνδεθείτε στο Διαδίκτυο", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void initUI() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bConnect = (Button) findViewById(R.id.bConnect);
        bCreateUser = (Button) findViewById(R.id.bCreateUser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //AsyncTack < params,progress,result
    class CheckUser extends AsyncTask<String, String, String> {


        //Check user starting background thread
        @Override
        protected String doInBackground(String... args) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Log.i("username", username);
            Log.i("password", password);

            //Building Parameters to pass to php script check_user
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            //Getting JSON object passing  the url, the params, and the method that you want to pass the params
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {
                success = json.getInt(TAG_CHECKSUCCESS);

                if (success == 1) {
                    userID = json.getInt(TAG_USERID);
                    SharedPrefsManager manager=new SharedPrefsManager(LoginActivity.this);
                    manager.startEditing();
                    manager.setPrefsUserID(userID);
                    manager.commit();
                    Log.i("UserID",userID+"");
                    userName = json.getString(TAG_DOCTORNAME);
                    userSuname = json.getString(TAG_DOCTORSURNAME);
                } else {
                    Log.i("UserSuccess", "Fail");
                }

                Log.i("UserID", userID + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);

            if (success == 1) {
                Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);
                intent.putExtra("userSurname", userSuname);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Λάθος στοιχεία προσπαθήστε ξανά ή δημιουργήστε καινούριο λογαριασμό", Toast.LENGTH_LONG).show();
            }
        }
    }


}
