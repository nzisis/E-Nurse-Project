package Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

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

    }
}
