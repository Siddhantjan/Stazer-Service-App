package stazer.user.androidstazerserviceapp.services.CleaningServices.Sanitization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.CleaningServices.bathroomCleaning.BathroomCleaningActivity;

public class SanitizationServiceActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private EditText mSizeSanitization;
    private Button mScheduleSanitizationBtn;
    private CheckBox mHome,mClinic,mShowRooms, mRestaurants,mSmallOffices,mLargesOffices,mHostels;
    private RadioButton mSanitizationServiceName;
    private RadioGroup mGroupService;
    @Override
    protected void onStop() {

        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        mGroupService.clearCheck();
        mSizeSanitization.setText(null);
        checkBoxStatingPos();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanitization_service);

        //Hooks
        mHome = findViewById(R.id.homeCheckBox);
        mClinic = findViewById(R.id.doctorClinicCheckBox);
        mShowRooms = findViewById(R.id.showroomsCheckBox);
        mRestaurants = findViewById(R.id.restaurantCheckbox);
        mSmallOffices = findViewById(R.id.retailShopCheckBox);
        mLargesOffices = findViewById(R.id.largeOfficeCheckBox);
        mHostels = findViewById(R.id.hostelsInstitutes);

        mSizeSanitization = findViewById(R.id.sizeOfPlace);
        mGroupService = findViewById(R.id.sanitizaionServiceSelectGroup);

        mScheduleSanitizationBtn = findViewById(R.id.scheduleServiceSanitization);

        mScheduleSanitizationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mGroupService.getCheckedRadioButtonId();
                mSanitizationServiceName = (RadioButton) findViewById(selectedId);

                if (!mHome.isChecked() && !mClinic.isChecked() && !mShowRooms.isChecked() &&
                        !mHostels.isChecked() &&  !mLargesOffices.isChecked() && !mRestaurants.isChecked() && !mSmallOffices.isChecked()) {
                    Toast.makeText(SanitizationServiceActivity.this, "Check At least One Place", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(mSizeSanitization.getText().toString())){
                    Toast.makeText(SanitizationServiceActivity.this, "Enter the Size of Your Area in Square feet ", Toast.LENGTH_SHORT).show();
                }
                else  if (mGroupService.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(SanitizationServiceActivity.this, "Please Select Type of Sanitization ", Toast.LENGTH_SHORT).show();
                }
                else {
                    StringBuffer sanitizationsText = new StringBuffer();
                    sanitizationsText.append("Sanitization Part :");
                    if (mHome.isChecked()) {
                        sanitizationsText.append(" " +mHome.getText().toString());
                    }
                    if (mHostels.isChecked()) {
                        sanitizationsText.append(" " +mHostels.getText().toString());
                    }
                    if (mClinic.isChecked()) {
                        sanitizationsText.append(" " + mClinic.getText().toString());
                    }
                    if (mShowRooms.isChecked()) {
                        sanitizationsText.append(" " + mShowRooms.getText().toString());
                    }
                    if (mLargesOffices.isChecked()) {
                        sanitizationsText.append(" " + mLargesOffices.getText().toString());
                    }
                    if (mRestaurants.isChecked()) {
                        sanitizationsText.append(" " + mRestaurants.getText().toString());
                    }
                    if (mSmallOffices.isChecked()) {
                        sanitizationsText.append(" " + mSmallOffices.getText().toString());
                    }

                    String sanitizationsAreaText = sanitizationsText.toString();
                    String ServiceType = ("Sanitization-Type :"+ mSanitizationServiceName.getText().toString() );

                    Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
                    categoryIntent.putExtra("serviceType", ServiceType);
                    categoryIntent.putExtra("categoryType", sanitizationsAreaText);
                    startActivity(categoryIntent);
                }
            }
        });
    }

    private void  checkBoxStatingPos(){
        mHome.setChecked(false);
        mClinic.setChecked(false);
        mHostels.setChecked(false);
        mShowRooms.setChecked(false);
        mRestaurants.setChecked(false);
        mSmallOffices.setChecked(false);
        mLargesOffices.setChecked(false);

    }
}