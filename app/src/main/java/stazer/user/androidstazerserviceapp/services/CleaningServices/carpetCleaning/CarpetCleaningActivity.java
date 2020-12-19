package stazer.user.androidstazerserviceapp.services.CleaningServices.carpetCleaning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.CleaningServices.bathroomCleaning.BathroomCleaningActivity;

public class CarpetCleaningActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private Button mScheduleCarpetBtn;
    private RadioButton mCarpetCleaningServiceName;
    private RadioGroup mGroupService;


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        mGroupService.clearCheck();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carpet_cleaning);


        mScheduleCarpetBtn = findViewById(R.id.scheduleServiceCarpet);

        mGroupService = findViewById(R.id.CarpetCleanServiceSelectGroup);

        mScheduleCarpetBtn.setOnClickListener(v -> {
            int selectedId = mGroupService.getCheckedRadioButtonId();
            mCarpetCleaningServiceName = (RadioButton) findViewById(selectedId);

            if (mGroupService.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please Select Your Service", Toast.LENGTH_SHORT).show();
            } else {
                Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                categoryIntent.putExtra("serviceType", "Carpet Cleaning ");
                categoryIntent.putExtra("categoryType", mCarpetCleaningServiceName.getText().toString());
                startActivity(categoryIntent);
            }
        });


    }
}