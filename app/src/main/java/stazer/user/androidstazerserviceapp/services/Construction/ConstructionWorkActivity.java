package stazer.user.androidstazerserviceapp.services.Construction;

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

public class ConstructionWorkActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private Button mScheduleServiceConstruction;
    private RadioButton mWorkServiceName;
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
        setContentView(R.layout.activity_contruction_work);
        mScheduleServiceConstruction = findViewById(R.id.scheduleServiceConstruction);
        mGroupService = findViewById(R.id.ConstructionServiceSelectGroup);
        mScheduleServiceConstruction.setOnClickListener(v -> {
            int selectedId = mGroupService.getCheckedRadioButtonId();
            mWorkServiceName = (RadioButton) findViewById(selectedId);

            if (mGroupService.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please Select Your Service", Toast.LENGTH_SHORT).show();
            } else {
                Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                categoryIntent.putExtra("serviceType", "Construction ");
                categoryIntent.putExtra("categoryType", mWorkServiceName.getText().toString());
                startActivity(categoryIntent);
            }
        });
    }
}