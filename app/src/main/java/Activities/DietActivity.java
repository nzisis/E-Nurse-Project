package Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.vromia.e_nurseproject.R;


/**
 * Created by Vromia on 17/12/2014.
 */
public class DietActivity extends Activity {

    private Button bHour;
    private Button bDate;
    private Button bBack;
    private Button bOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

    }

    public void initUI( ){
        bHour = (Button) findViewById(R.id.imbtHour);
        bDate = (Button) findViewById(R.id.imbtDate);
        bBack = (Button) findViewById(R.id.btBack);
        bOk = (Button) findViewById(R.id.btOk);
    }

    public void initListeners(){
        bHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                EditText editText = (EditText) findViewById(R.id.etQuant);
                Double quantity = Double.valueOf(editText.getText().toString());
                Spinner spinner = (Spinner) findViewById(R.id.spChoosFood);
                String foodName = spinner.getSelectedItem().toString();
                ImageButton dateBut = (ImageButton) findViewById(R.id.imbtDate);
                // TODO Get the date from the imButton
                ImageButton hourBut = (ImageButton) findViewById(R.id.imbtHour);
                // TODO Same as above
            }
        });
    }
}
