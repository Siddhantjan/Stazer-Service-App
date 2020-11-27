package stazer.user.androidstazerserviceapp.BookingProcess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.BookingInfo.BookingActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.dateTimePicker.DateFragment;
import stazer.user.androidstazerserviceapp.BookingProcess.dateTimePicker.TimeFragment;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.R;

public class orderSchduleActivity extends AppCompatActivity {

    private TextView mSelectTime, mSelectDate;
    private EditText mDisplayTime, mDisplayDate;
    FirebaseDatabase database;
    DatabaseReference userInfoRef;
    private TextView mServiceType;
    private Button btn_bookingSchedule;
    private TextView mName, mMobileNumber,  mAddress;
    private String UserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order_schdule);

        //these are invisible hooks
        mName = findViewById(R.id.customer_name_sch);
        mMobileNumber = findViewById(R.id.customer_mobile_sch);
        mAddress = findViewById(R.id.address_type_sch);

        //Hooks
        mSelectDate = (TextView) findViewById(R.id.btn_booking_date);
        mSelectTime = (TextView) findViewById(R.id.btn_booking_time);
        mDisplayDate = (EditText) findViewById(R.id.btn_booking_date_display);
        mDisplayTime = (EditText) findViewById(R.id.btn_booking_time_display);
        mServiceType = findViewById(R.id.service_type_sch);
        btn_bookingSchedule = findViewById(R.id.btn_booking_schdule);
        mServiceType.setText(getIntent().getStringExtra("serviceType"));
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);

        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("userInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("dataRetrieve", "onDataChange: Data Retrieve successfully");
                if (snapshot.exists()) {
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    String mobileNumber = snapshot.child("phoneNumber").getValue().toString();
                    String flatno = snapshot.child("flatNo").getValue().toString();
                    String Area = snapshot.child("area").getValue().toString();
                    String Landmark = snapshot.child("landmark").getValue().toString();

                    UserAddress = flatno + " " + Area + " " + Landmark;
                    String userName = firstName + " " + lastName;
                    mName.setText(userName);
                    mMobileNumber.setText(mobileNumber);
                    mAddress.setText(UserAddress);
                    Log.d("dataRetrieve", "onDataChange: Data Retrieve successfully");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(orderSchduleActivity.this, "[Error]: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btn_bookingSchedule.setOnClickListener(v -> {
            goToOrderCompleteActivity();
        });
    }

    private void goToOrderCompleteActivity() {
        if (mDisplayDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(orderSchduleActivity.this, "Select Date", Toast.LENGTH_SHORT).show();

        } else if (mDisplayTime.getText().toString().isEmpty()) {
            Toast.makeText(orderSchduleActivity.this, "Select Time", Toast.LENGTH_SHORT).show();

        } else {
            HashMap<String, Object> scheduleMap = new HashMap<>();
            scheduleMap.put("serviceType", mServiceType.getText().toString());
            scheduleMap.put("Time", mDisplayTime.getText().toString());
            scheduleMap.put("Date", mDisplayDate.getText().toString());
            scheduleMap.put("Status","pending");
            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails")
                    .push().setValue(scheduleMap)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Your Service Scheduled on Time ", Toast.LENGTH_SHORT).show();
                        goToBookingActivity();

                    }).addOnFailureListener(e -> {
                Toast.makeText(this, "[Error]:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void goToBookingActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), BookingActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void CalendarAppears(View view) {
        DialogFragment dialogFragment = new DateFragment();
        dialogFragment.show(getSupportFragmentManager(), "Select Date");
    }

    public void takeDate(int year, int month, int date) {
        String Years = Integer.toString(year);
        String Month = Integer.toString(month);
        String Date = Integer.toString(date);
        mDisplayDate.setVisibility(View.VISIBLE);
        mDisplayDate.setText(Date + "/" + Month + "/" + Years);

    }

    public void TimeAppears(View view) {
        DialogFragment fragment = new TimeFragment();
        fragment.show(getSupportFragmentManager(), "Select Time");
    }

    public void takeTime(int hourOfDay, int minute) {
        String hour = Integer.toString(hourOfDay);
        String min = Integer.toString(minute);

        mDisplayTime.setVisibility(View.VISIBLE);
        mDisplayTime.setText(hour + ":" + min );
    }
}