package stazer.user.androidstazerserviceapp.BookingProcess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.R;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class OrderBookingActivity extends AppCompatActivity {

    private Button btn_bookingComplete;
    FirebaseDatabase database;
    DatabaseReference userInfoRef;
    String userName;
    String serviceName;
    String UserAddress;
    private TextView mName, mMobileNumber, mServiceType, mCategoryType, mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_booking);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        serviceName = prefs.getString("serviceName", NULL);
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);


        //hooks
        mServiceType = findViewById(R.id.service_type);
        mServiceType.setText(serviceName);
        mCategoryType = findViewById(R.id.category_type);
        mMobileNumber = findViewById(R.id.customer_mobile);
        mName = findViewById(R.id.customer_name);
        mCategoryType.setText(getIntent().getStringExtra("categoryType"));
        mAddress = findViewById(R.id.address_type);
        btn_bookingComplete = findViewById(R.id.btn_booking_final);
        Date currentTime = Calendar.getInstance().getTime();


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
            HashMap<String, Object> bookingMap = new HashMap<>();
            bookingMap.put("serviceType", mServiceType.getText().toString());
            bookingMap.put("serviceCategory", mCategoryType.getText().toString());
            try {
                bookingMap.put("DateFragment ", currentTime.toString());
            } catch (Exception e) {
                Toast.makeText(OrderBookingActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").push()
                    .setValue(bookingMap)
                    .addOnCompleteListener(task -> {
                        Log.d("Tag", "onComplete: Booking Confirmed saved");
                        Toast.makeText(OrderBookingActivity.this, "you Successfully booked Service.", Toast.LENGTH_SHORT).show();
                    goToHomeActivity();
                    }).addOnFailureListener(e -> {
                        Log.d("error", "onFailure: "+e.toString());
                        Toast.makeText(OrderBookingActivity.this, "[Error]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


    }

    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}