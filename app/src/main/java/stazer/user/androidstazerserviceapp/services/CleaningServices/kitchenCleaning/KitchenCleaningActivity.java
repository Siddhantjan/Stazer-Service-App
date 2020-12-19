package stazer.user.androidstazerserviceapp.services.CleaningServices.kitchenCleaning;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class KitchenCleaningActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private Button mScheduleKitchenBtn;
    private RadioButton mKitchenCleaningServiceName;
    private RadioGroup mKitchenGroupService;
    private CheckBox mGasStove, mChimney, mFloor, mSink, mShelves, mAppliance, mWindowFans;


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        checkBoxStatingPos();
        mKitchenGroupService.clearCheck();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kitchen_cleaning);

        mKitchenGroupService = findViewById(R.id.kitchenCleanServiceSelectGroup);

        //CheckBox Hooks
        mGasStove = findViewById(R.id.chr1ForKitchen);
        mShelves = findViewById(R.id.ch1r1ForKitchen);
        mWindowFans = findViewById(R.id.ch3r1ForKitchen);
        mAppliance = findViewById(R.id.ch4r1ForKitchen);
        mChimney = findViewById(R.id.ch1r2ForKitchen);
        mFloor = findViewById(R.id.ch2r2ForKitchen);
        mSink = findViewById(R.id.ch2r3ForKitchen);

        mScheduleKitchenBtn = findViewById(R.id.scheduleServiceKitchen);


        mScheduleKitchenBtn.setOnClickListener(v -> {
            int selectedId = mKitchenGroupService.getCheckedRadioButtonId();
            mKitchenCleaningServiceName = (RadioButton) findViewById(selectedId);

            if (mKitchenGroupService.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please Select Your Service", Toast.LENGTH_SHORT).show();
            } else if (!mFloor.isChecked() && !mSink.isChecked() && !mChimney.isChecked() && !mAppliance.isChecked() && !mShelves.isChecked() && !mGasStove.isChecked() && !mWindowFans.isChecked()) {
                Toast.makeText(this, "Check at-least ", Toast.LENGTH_SHORT).show();
            } else {
                StringBuffer KitchenPartsText = new StringBuffer();
                KitchenPartsText.append("Cleaning Part is : ");
                if (mFloor.isChecked()) {
                    KitchenPartsText.append(" " + mFloor.getText().toString());
                }
                if (mSink.isChecked()) {
                    KitchenPartsText.append(" " + mSink.getText().toString());
                }
                if (mChimney.isChecked()) {
                    KitchenPartsText.append(" " + mChimney.getText().toString());
                }
                if (mAppliance.isChecked()) {
                    KitchenPartsText.append(" " + mAppliance.getText().toString());
                }
                if (mShelves.isChecked()) {
                    KitchenPartsText.append(" " + mShelves.getText().toString());
                }
                if (mGasStove.isChecked()) {
                    KitchenPartsText.append(" " + mGasStove.getText().toString());
                }
                if (mWindowFans.isChecked()) {
                    KitchenPartsText.append(" " + mWindowFans.getText().toString());
                }
                String KitchenParts = KitchenPartsText.toString();
                String ServiceCount = ("Kitchen : " + mKitchenCleaningServiceName.getText().toString());

                Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                categoryIntent.putExtra("serviceType", ServiceCount);
                categoryIntent.putExtra("categoryType", KitchenParts);
                startActivity(categoryIntent);
            }
        });
    }
    private void  checkBoxStatingPos(){
        mShelves.setChecked(false);
        mGasStove.setChecked(false);
        mAppliance.setChecked(false);
        mChimney.setChecked(false);
        mSink.setChecked(false);
        mFloor.setChecked(false);
        mWindowFans.setChecked(false);
    }
}