package stazer.user.androidstazerserviceapp.BookingProcess;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.BookingInfo.BookingActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class orderSchduleActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private TextView mSelectTime, mSelectDate;
    private TextView mDisplayTime, mDisplayDate;
    FirebaseDatabase database;
    DatabaseReference userInfoRef, adminInfoRef;
    private TextView mServiceType, mServiceCategory;
    private Button btn_bookingSchedule;
    private TextView mName, mMobileNumber, mAddress;
    private String UserAddress, mServiceAddress;
    private int cYear, cMonth, cDay, sYear, sMonth, sDay;
    private int cHour, cMinute, sHour, sMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_schdule);

        //initialize calender
        Calendar calendar = Calendar.getInstance();

        //get Current Year
        cYear = calendar.get(Calendar.YEAR);
        //get Current Month
        cMonth = calendar.get(Calendar.MONTH);
        // get Current Day
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        // Get Current Minutes
        cMinute = calendar.get(Calendar.MINUTE);

        //these are invisible hooks
        mName = findViewById(R.id.customer_name_sch);
        mMobileNumber = findViewById(R.id.customer_mobile_sch);
        mAddress = findViewById(R.id.address_type_sch);
        //Hooks for Date and Time
        mSelectDate = (TextView) findViewById(R.id.btn_booking_date);
        mSelectTime = (TextView) findViewById(R.id.btn_booking_time);
        mDisplayDate = (TextView) findViewById(R.id.btn_booking_date_display);
        mDisplayTime = (TextView) findViewById(R.id.btn_booking_time_display);

        mSelectDate.setOnClickListener(v -> {
            //Initialize date Picker Dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(orderSchduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    sYear = year;
                    sMonth = month;
                    sDay = dayOfMonth;

                    String sDate = sDay + "-" + sMonth + "-" + sYear;
                    mDisplayDate.setText(sDate);

                }
            }, cYear, cMonth, cDay);
            // Displayed previous selected Date
            datePickerDialog.updateDate(sYear, sMonth, sDay);
            //Disabled past Date
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            // Disabled future Date
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 604800000L); // 7 * 24 * 60 * 60 * 1000
            //show Date Picker Dialog
            datePickerDialog.show();
        });

        mSelectTime.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mDisplayDate.getText().toString())) {
                Toast.makeText(this, "Please Select Date First", Toast.LENGTH_SHORT).show();
            } else {
                //Initialize Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        orderSchduleActivity.this, (view, hourOfDay, minute) -> {
                    sHour = hourOfDay;
                    sMinute = minute;

                    //Initialize Calender
                    Calendar calendar1 = Calendar.getInstance();

                    String sDate = mDisplayDate.getText().toString().trim();
                    //Split Date
                    String[] strings = sDate.split("-");
                    //Get day on Calender
                    sDay = Integer.parseInt(strings[0]);
                    //set day on Calender
                    calendar1.set(Calendar.DAY_OF_MONTH, sDay);
                    //set Hour on Calender
                    calendar1.set(Calendar.HOUR_OF_DAY, sHour);
                    //set Minute on Calender
                    calendar1.set(Calendar.MINUTE, sMinute);
                    if (calendar1.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                        mDisplayTime.setText(DateFormat.format("hh:mm aa", calendar1));
                    } else {
                        Toast.makeText(this, "You Selected Past Time Please Select Correct Time", Toast.LENGTH_SHORT).show();
                    }


                }, cHour, cMinute, false);
                //show Dialog
                timePickerDialog.show();
            }
        });

        mServiceType = findViewById(R.id.service_type_sch);
        mServiceCategory = findViewById(R.id.serviceCategorySch);
        btn_bookingSchedule = findViewById(R.id.btn_booking_schdule);

        mServiceType.setText(getIntent().getStringExtra("serviceType"));
        mServiceCategory.setText(getIntent().getStringExtra("categoryType"));
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);


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
            AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(this);
            final EditText mAddress = new EditText(this);
            amountDialogBuilder.setTitle("Your Service Address ");
            mAddress.setInputType(InputType.TYPE_CLASS_TEXT);
            mAddress.setHint("Your Service Address");
            mAddress.setWidth(200);
            mAddress.setText(UserAddress);
            amountDialogBuilder.setView(mAddress);
            amountDialogBuilder.setPositiveButton("CONFIRM", (dialog, which) -> {
                if (!TextUtils.isEmpty(mAddress.getText().toString())) {
                    mServiceAddress = mAddress.getText().toString();
                    goToUpdateOrderSchedule();
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = amountDialogBuilder.create();
            dialog.setCancelable(false);
            dialog.show();
        }

    }


    private void goToUpdateOrderSchedule() {
        String id = userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails")
                .push().getKey();
        HashMap<String, Object> scheduleMap = new HashMap<>();
        scheduleMap.put("serviceType", mServiceType.getText().toString());
        scheduleMap.put("serviceCategory", mServiceCategory.getText().toString());
        scheduleMap.put("Time", mDisplayTime.getText().toString());
        scheduleMap.put("Date", mDisplayDate.getText().toString());
        scheduleMap.put("Status", "pending");
        scheduleMap.put("Amount", "0");
        try {
            scheduleMap.put("id", id);
            scheduleMap.put("ServiceAddress", mServiceAddress);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails")
                .child(id).setValue(scheduleMap)
                .addOnCompleteListener(task -> {
                    AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(this);
                    amountDialogBuilder.setTitle("Service Booked");
                    amountDialogBuilder.setMessage("Your service has been booked. \n" + "We Will Contact you in 10 minutes.\n" + "Tap OK to View your Service");
                    amountDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(orderSchduleActivity.this, "you Successfully booked Service.", Toast.LENGTH_SHORT).show();
                        goToBookingActivity();
                    });
                    AlertDialog dialog = amountDialogBuilder.create();
                    dialog.setCancelable(false);
                    dialog.show();

                }).addOnFailureListener(e -> {
            Toast.makeText(this, "[Error]:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        HashMap<String, Object> serviceScheduleMap = new HashMap<>();
        serviceScheduleMap.put("UserName", mName.getText().toString());
        serviceScheduleMap.put("UserMobile", mMobileNumber.getText().toString());
        serviceScheduleMap.put("UserAddress", mServiceAddress);
        serviceScheduleMap.put("serviceType", mServiceType.getText().toString());
        serviceScheduleMap.put("Time", mDisplayTime.getText().toString());
        serviceScheduleMap.put("Date", mDisplayDate.getText().toString());
        serviceScheduleMap.put("serviceCategory", mServiceCategory.getText().toString());

        adminInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(serviceScheduleMap)
                .addOnCompleteListener(task -> Log.d("sendDataToAdminSchedule", "onComplete: Booking Confirmed saved"))
                .addOnFailureListener(e -> Log.d("sendDataToAdminSchedule", "onFailure: " + e.toString()));


    }

    private void goToBookingActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), BookingActivity.class);
        startActivity(mainIntent);
        finish();
    }

}