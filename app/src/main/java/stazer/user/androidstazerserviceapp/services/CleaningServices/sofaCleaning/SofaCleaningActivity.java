package stazer.user.androidstazerserviceapp.services.CleaningServices.sofaCleaning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class SofaCleaningActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private Button mScheduleSofaBtn;
    private RadioButton mSofaCleaningServiceName, mSofaCleaningServiceCategoryName;
    private RadioGroup mGroupService, mSubGroupService;


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        mGroupService.clearCheck();
        mSubGroupService.clearCheck();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sofa_cleaning);

        mScheduleSofaBtn = findViewById(R.id.scheduleServiceSofa);

        mGroupService = findViewById(R.id.SofaCleanServiceSelectGroup);
        mSubGroupService = findViewById(R.id.sofaCleanServiceSelectSubGroup);

        mScheduleSofaBtn.setOnClickListener(v -> {
            int selectedId = mGroupService.getCheckedRadioButtonId();
            mSofaCleaningServiceName = (RadioButton) findViewById(selectedId);

            int selectedSubId = mSubGroupService.getCheckedRadioButtonId();
            mSofaCleaningServiceCategoryName = (RadioButton) findViewById(selectedSubId);

            if (mGroupService.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please Select Your Sofa ", Toast.LENGTH_SHORT).show();
            } else if (mSubGroupService.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please Select at least one Option", Toast.LENGTH_SHORT).show();
            } else {
                Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                String serviceName = "Sofa Cleaning :" + mSofaCleaningServiceName.getText().toString();
                categoryIntent.putExtra("serviceType", serviceName);
                categoryIntent.putExtra("categoryType", mSofaCleaningServiceCategoryName.getText().toString());
                startActivity(categoryIntent);
            }
        });
    }
}