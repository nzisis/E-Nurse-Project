package Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Created by Vromia on 17/12/2014.
 */
public class WorkoutActivity extends Activity {
    private Button bMorn;
    private Button bNoon;
    private Button bNight;
    private Button bDate;
    private Button bBack;
    private Button bOk;
    private EditText quantField;
    private Spinner spinner;
    private ImageButton dateBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

    }

    public void initUI( ){
        bMorn  = (Button) findViewById(R.id.rbMorn); // Na kanoume activate kapoio apo default gia na apofygoume errors an den epileksei o xrhsths kati?
        bNoon  = (Button) findViewById(R.id.rbNoon);
        bNight  = (Button) findViewById(R.id.rbNight);
        bDate = (Button) findViewById(R.id.imbtDate);
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
                bNight.setActivated(false);
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
                else {
                    // TODO print you must choose "Time of Day"
                    return;
                }
                // TODO get the date apo ton imButton Date
                // TODO we are ready to write on DB
            }
        });
    }
}
