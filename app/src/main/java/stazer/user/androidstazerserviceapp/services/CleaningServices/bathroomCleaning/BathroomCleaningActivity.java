package stazer.user.androidstazerserviceapp.services.CleaningServices.bathroomCleaning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class BathroomCleaningActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private EditText mBathroomCount;
    private Button mScheduleBathroomBtn;
    private RadioButton mBathCleaningServiceName;
    private RadioGroup mGroupService;


    @Override
    protected void onStop() {

        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {

        mBathroomCount.setText(null);
        mGroupService.clearCheck();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bathroom_cleaning);

        mBathroomCount = findViewById(R.id.countBathroom);
        mScheduleBathroomBtn = findViewById(R.id.scheduleServiceBath);

        mGroupService = findViewById(R.id.BathCleanServiceSelectGroup);


        mScheduleBathroomBtn.setOnClickListener(v -> {
            int selectedId = mGroupService.getCheckedRadioButtonId();
            mBathCleaningServiceName = (RadioButton) findViewById(selectedId);


            if (TextUtils.isEmpty(mBathroomCount.getText().toString())){
                Toast.makeText(BathroomCleaningActivity.this, "Enter Number  of Bathrooms", Toast.LENGTH_SHORT).show();
            }
          else  if (mGroupService.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(this, "Please Select Your Service", Toast.LENGTH_SHORT).show();
            }
            else {
                String ServiceCount =(mBathroomCount.getText().toString() +" Bathroom CLeaning" );
                    Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                    categoryIntent.putExtra("serviceType", ServiceCount);
                    categoryIntent.putExtra("categoryType", mBathCleaningServiceName.getText().toString());
                    startActivity(categoryIntent);
                }
        });


    }
}