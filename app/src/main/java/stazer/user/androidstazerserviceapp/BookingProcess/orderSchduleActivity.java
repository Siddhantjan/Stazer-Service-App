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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order_schdule);

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

        btn_bookingSchedule.setOnClickListener(v -> {
            if (mDisplayDate.getText().toString().trim().isEmpty()) {
                Toast.makeText(orderSchduleActivity.this, "Select Date", Toast.LENGTH_SHORT).show();

            } else if (mDisplayTime.getText().toString().isEmpty()) {
                Toast.makeText(orderSchduleActivity.this, "Select Time", Toast.LENGTH_SHORT).show();

            } else {
                HashMap<String, Object> scheduleMap = new HashMap<>();
                scheduleMap.put("serviceType", mServiceType.getText().toString());
                scheduleMap.put("serviceTime", mDisplayTime.getText().toString());
                scheduleMap.put("serviceDate", mDisplayDate.getText().toString());
                userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("ScheduleOrdersDetails")
                        .push().setValue(scheduleMap)
                        .addOnCompleteListener(task -> {
                            Toast.makeText(this, "Your Service Scheduled on Time ", Toast.LENGTH_SHORT).show();
                            goToHomeActivity();

                        }).addOnFailureListener(e -> {
                    Toast.makeText(this, "[Error]:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
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
        mDisplayTime.setText(hour + ":" + min);
    }
}