package stazer.user.androidstazerserviceapp.BookingProcess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.BookingInfo.BookingActivity;
import stazer.user.androidstazerserviceapp.BookingInfo.FinalBookingPaymentActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class OrderBookingActivity extends AppCompatActivity {

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
    private Button btn_bookingComplete;
    FirebaseDatabase database;
    DatabaseReference userInfoRef, adminInfoRef;
    String userName;
    String UserAddress, mServiceAddress,CurrentDate,Time;
    private TextView mName, mMobileNumber, mServiceType, mCategoryType, mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_booking);


        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);


        //hooks
        mServiceType = findViewById(R.id.service_type);
        mServiceType.setText(getIntent().getStringExtra("serviceType"));
        mCategoryType = findViewById(R.id.category_type);
        mMobileNumber = findViewById(R.id.customer_mobile);
        mName = findViewById(R.id.customer_name);
        mCategoryType.setText(getIntent().getStringExtra("categoryType"));
        mAddress = findViewById(R.id.address_type);
        btn_bookingComplete = findViewById(R.id.btn_booking_final);

        Date currentDate = Calendar.getInstance().getTime();
         CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Time = simpleDateFormat.format(calendar.getTime());


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
                    userName = firstName + " " + lastName;
                    mName.setText(userName);
                    mMobileNumber.setText(mobileNumber);
                    mAddress.setText(UserAddress);
                    Log.d("dataRetrieve", "onDataChange: Data Retrieve successfully");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderBookingActivity.this, "[Error]: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_bookingComplete.setOnClickListener(v -> {
            AlertDialog.Builder addressDialogBuilder = new AlertDialog.Builder(this);
            final EditText mAddress = new EditText(this);
            addressDialogBuilder.setTitle("Your Service Address ");
            mAddress.setInputType(InputType.TYPE_CLASS_TEXT);
            mAddress.setHint("Your Service Address");
            mAddress.setText(UserAddress);
            addressDialogBuilder.setView(mAddress);
            addressDialogBuilder.setPositiveButton("CONFIRM", (dialog, which) -> {
                if (!TextUtils.isEmpty(mAddress.getText().toString())) {
                    mServiceAddress = mAddress.getText().toString();
                            updateDataForOrder();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(this, "Please Enter the Service Address", Toast.LENGTH_SHORT).show();
                }
            });
            addressDialogBuilder.setNegativeButton("CANCEL",(dialog, which) -> {
                    dialog.dismiss();
            });
            AlertDialog dialog = addressDialogBuilder.create();
            dialog.setCancelable(false);
            dialog.show();

        });
    }
    private void updateDataForOrder(){
        String id = userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").push().getKey();
        HashMap<String, Object> bookingMap = new HashMap<>();
        bookingMap.put("serviceType", mServiceType.getText().toString());
        bookingMap.put("serviceCategory", mCategoryType.getText().toString());
        bookingMap.put("Amount", "0");
        bookingMap.put("SparePartCost","0");
        try {
            bookingMap.put("id", id);
            bookingMap.put("Status", "pending");
            bookingMap.put("Date", CurrentDate);
            bookingMap.put("Time", Time);
            bookingMap.put("ServiceAddress", mServiceAddress);

        } catch (Exception e) {
            Toast.makeText(OrderBookingActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").child(id).setValue(bookingMap)
                .addOnCompleteListener(task -> {
                    Log.d("Tag", "onComplete: Booking Confirmed saved");
                    AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(this);
                    amountDialogBuilder.setTitle("Service Booked");
                    amountDialogBuilder.setMessage("Your service has been booked. \n" + "We will contact you in few minutes.\n" + "Tap OK to view your service");
                    amountDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(OrderBookingActivity.this, "you Successfully booked Service.", Toast.LENGTH_SHORT).show();
                        goToBookingActivity();
                    });
                    AlertDialog dialog = amountDialogBuilder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }).addOnFailureListener(e -> {
            Log.d("error", "onFailure: " + e.toString());
            Toast.makeText(OrderBookingActivity.this, "[Error]" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        //Send Data To Admin App
        HashMap<String, Object> serviceSendAdmin = new HashMap<>();
        serviceSendAdmin.put("UserName", mName.getText().toString());
        serviceSendAdmin.put("UserMobile", mMobileNumber.getText().toString());
        serviceSendAdmin.put("UserAddress", mServiceAddress);
        serviceSendAdmin.put("serviceType", mServiceType.getText().toString());
        serviceSendAdmin.put("serviceCategory", mCategoryType.getText().toString());
        try {
            serviceSendAdmin.put("Date", CurrentDate);
            serviceSendAdmin.put("Time", Time);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adminInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(serviceSendAdmin)
                .addOnCompleteListener(task -> Log.d("sendDataToAdmin", "onComplete: Booking Confirmed saved"))
                .addOnFailureListener(e -> Log.d("sendDataToAdmin", "onFailure: " + e.toString()));

    }
    private void goToBookingActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), BookingActivity.class);
        startActivity(mainIntent);
        finish();
    }
}