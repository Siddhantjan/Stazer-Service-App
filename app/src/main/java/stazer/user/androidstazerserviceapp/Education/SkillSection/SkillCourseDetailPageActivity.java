package stazer.user.androidstazerserviceapp.Education.SkillSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.Education.EducationBooking.RunningCoursesActivity;
import stazer.user.androidstazerserviceapp.R;

public class SkillCourseDetailPageActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private TextView mHeader,mCourseName;
    private ImageView mDetailsCourse;
    private EditText mStudentName, mOptionalNumber;
    private TextView mName, mMobileNumber, mAddress;
    private Button mConfirmMeeting;
    private String UserAddress,mServiceAddress;
    FirebaseDatabase database;
    DatabaseReference userInfoRef, adminInfoRef;
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_skill_course_detail_page);
        mHeader = findViewById(R.id.header_text);
        mHeader.setText(getIntent().getStringExtra("courseName"));
        mDetailsCourse = findViewById(R.id.detailsCourse);
        SetDetailsOnView();

        //Hooks
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);

        mCourseName = findViewById(R.id.courseType);
        mCourseName.setText(mHeader.getText().toString());
        mName = findViewById(R.id.customer_name_skill);
        mMobileNumber = findViewById(R.id.customer_mobile_skill);
        mAddress = findViewById(R.id.address_type_skill);
        mStudentName = findViewById(R.id.studentNameSkill);
        mOptionalNumber = findViewById(R.id.optionalNumberSkill);
        mConfirmMeeting = findViewById(R.id.btn_booking_final_class_skill);
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
                Toast.makeText(getApplicationContext(), "[Error]: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mConfirmMeeting.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mStudentName.getText().toString())){
                Toast.makeText(this, "Enter Student Name", Toast.LENGTH_SHORT).show();
            }
            else {
                updateAddress();
            }
        });
    }

    private void SetDetailsOnView() {
        if (mHeader.getText().toString().equals("C Programming")){
            mDetailsCourse.setImageResource(R.drawable.c_programming);
        }else if (mHeader.getText().toString().equals("Java Programming")){
            mDetailsCourse.setImageResource(R.drawable.java);
        }else if (mHeader.getText().toString().equals("Python Programming")){
            mDetailsCourse.setImageResource(R.drawable.python);
        }else if (mHeader.getText().toString().equals("Data Structures")){
            mDetailsCourse.setImageResource(R.drawable.datastructures);
        }else if (mHeader.getText().toString().equals("Android App Development")){
            mDetailsCourse.setImageResource(R.drawable.app_development);
        }else if (mHeader.getText().toString().equals("Web Development")){
            mDetailsCourse.setImageResource(R.drawable.web_development);
        }
        else if (mHeader.getText().toString().equals("Nurture Course")){
            mDetailsCourse.setImageResource(R.drawable.nurture);
        }
        else if (mHeader.getText().toString().equals("Advanced Course")){
            mDetailsCourse.setImageResource(R.drawable.advanced);
        }

    }

    private void updateAddress() {
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
    }
    private void updateDataForOrder(){
        String id = userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Education").push().getKey();
        HashMap<String, Object> bookingMap = new HashMap<>();
        try {
            bookingMap.put("ClassCategory","Technical Course");
            bookingMap.put("Subject",mCourseName.getText().toString());
            bookingMap.put("studentName",mStudentName.getText().toString());
            bookingMap.put("id", id);
            bookingMap.put("ServiceAddress", mServiceAddress);

        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Education").child(id).setValue(bookingMap)
                .addOnCompleteListener(task -> {
                    Log.d("Tag", "onComplete: Booking Confirmed saved");
                    AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(this);
                    amountDialogBuilder.setTitle("Service Booked");
                    amountDialogBuilder.setMessage("Your requirement has been booked. \n" +
                            "We Will Contact you in Few Hours.\n" + "Tap OK ");
                    amountDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(this, "you Successfully booked Service.", Toast.LENGTH_SHORT).show();
                        goToViewDetails();
                    });
                    AlertDialog dialog = amountDialogBuilder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }).addOnFailureListener(e -> {
            Log.d("error", "onFailure: " + e.toString());
            Toast.makeText(this, "[Error]" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        //Send Data To Admin App
        HashMap<String, Object> serviceSendAdmin = new HashMap<>();
        serviceSendAdmin.put("UserName", mName.getText().toString());
        serviceSendAdmin.put("UserMobile", mMobileNumber.getText().toString());
        serviceSendAdmin.put("UserAddress", mServiceAddress);
        try {
            serviceSendAdmin.put("ClassCategory","Technical Course");
            serviceSendAdmin.put("Subjects",mCourseName.getText().toString());
            serviceSendAdmin.put("studentName",mStudentName.getText().toString());
            serviceSendAdmin.put("Optional Number",mOptionalNumber.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adminInfoRef.child("Education").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(serviceSendAdmin)
                .addOnCompleteListener(task -> Log.d("sendDataToAdmin", "onComplete: Booking Confirmed saved"))
                .addOnFailureListener(e -> Log.d("sendDataToAdmin", "onFailure: " + e.toString()));

    }


    private void goToViewDetails() {
        Intent courseIntent = new Intent(getApplicationContext(), RunningCoursesActivity.class);
        startActivity(courseIntent);
        finish();
    }
}

