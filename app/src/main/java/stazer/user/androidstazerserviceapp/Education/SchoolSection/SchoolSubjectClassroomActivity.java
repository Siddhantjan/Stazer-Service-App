package stazer.user.androidstazerserviceapp.Education.SchoolSection;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.Education.EducationBooking.RunningCoursesActivity;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.R;

public class SchoolSubjectClassroomActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private EditText mStudentName, mOptionalNumber;
    private TextView mName, mMobileNumber, mAddress;
    private Spinner mSelectClass;
    private CheckBox mAllSubject, mSst, mEnglish, mMaths, mScience;
    private Button mConfirmMeeting;
    private String className,UserAddress,mServiceAddress,subjectTxt;
    FirebaseDatabase database;
    DatabaseReference userInfoRef, adminInfoRef;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_school_subject_classroom);

        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);

        //Hooks
        mName = findViewById(R.id.customer_name);
        mMobileNumber = findViewById(R.id.customer_mobile);
        mAddress = findViewById(R.id.address_type);
        mStudentName = findViewById(R.id.studentName);
        mOptionalNumber = findViewById(R.id.optionalNumber);

        mSelectClass = findViewById(R.id.class_type);

        mConfirmMeeting = findViewById(R.id.btn_booking_final_class);

        //CheckBox Hooks
        mAllSubject = findViewById(R.id.allSubject);
        mSst = findViewById(R.id.sst);
        mEnglish = findViewById(R.id.english);
        mMaths = findViewById(R.id.maths);
        mScience = findViewById(R.id.science);


        List<String> classType = new ArrayList<>();
        classType.add(0, "Select Your Class");
        classType.add("1st to 5th class");
        classType.add("6st to 10th class");
        ArrayAdapter<String> ClassSelect;
        ClassSelect = new ArrayAdapter<String>(this, R.layout.spinner_options,classType);
        ClassSelect.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

        mSelectClass.setAdapter(ClassSelect);
        mSelectClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select Your Class")) {
                    mAllSubject.setVisibility(View.GONE);
                    mSst.setVisibility(View.GONE);
                    mEnglish.setVisibility(View.GONE);
                    mMaths.setVisibility(View.GONE);
                    mScience.setVisibility(View.GONE);
                } else {
                    if (parent.getItemAtPosition(position).equals("1st to 5th class")) {
                        className = parent.getItemAtPosition(position).toString();
                        mAllSubject.setVisibility(View.VISIBLE);
                        mSst.setVisibility(View.GONE);
                        mEnglish.setVisibility(View.GONE);
                        mMaths.setVisibility(View.GONE);
                        mScience.setVisibility(View.GONE);
                    } else {
                        className = parent.getItemAtPosition(position).toString();
                        mAllSubject.setVisibility(View.VISIBLE);
                        mSst.setVisibility(View.VISIBLE);
                        mEnglish.setVisibility(View.VISIBLE);
                        mMaths.setVisibility(View.VISIBLE);
                        mScience.setVisibility(View.VISIBLE);
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConfirmMeeting.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mStudentName.getText().toString())){
                Toast.makeText(this, "Enter Student Name", Toast.LENGTH_SHORT).show();
            }
            else if (mSelectClass.getSelectedItemPosition() < 1) {
                Toast.makeText(getApplicationContext(), "Please Select Your Class", Toast.LENGTH_SHORT).show();
            }
            else  if (!mAllSubject.isChecked() && !mSst.isChecked() && !mEnglish.isChecked() && !mMaths.isChecked() && !mScience.isChecked()) {
                Toast.makeText(this, "Select your Subjects", Toast.LENGTH_SHORT).show();
            }
            else {
                StringBuffer subjectText = new StringBuffer();
                subjectText.append("Subject : ");
                if (mAllSubject.isChecked()) {
                        subjectText.append(" " + mAllSubject.getText().toString());
                    }
                    else {
                        if (mSst.isChecked() && !mAllSubject.isChecked()) {
                            subjectText.append(" " + mSst.getText().toString());
                        }
                        if (mScience.isChecked()&& !mAllSubject.isChecked()) {
                            subjectText.append(" " + mScience.getText().toString());
                        }
                        if (mMaths.isChecked()&& !mAllSubject.isChecked()) {
                            subjectText.append(" " + mMaths.getText().toString());
                        }
                        if (mEnglish.isChecked()&& !mAllSubject.isChecked()) {
                            subjectText.append(" " + mEnglish.getText().toString());
                        }
                }
                 subjectTxt = subjectText.toString();
               updateAddress();
            }
        });

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
                Toast.makeText(this, "Subject :"+subjectTxt+"\n"+mServiceAddress, Toast.LENGTH_SHORT).show();
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
            bookingMap.put("Subject",subjectTxt);
            bookingMap.put("ClassCategory",className);
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
                            "We Will Contact you in Few Hours.\n" + "Tap OK to View your Your Requirement");
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
            serviceSendAdmin.put("Subjects",subjectTxt);
            serviceSendAdmin.put("ClassCategory",className);
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
    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
    private void goToViewDetails() {
        Intent courseIntent = new Intent(getApplicationContext(), RunningCoursesActivity.class);
        startActivity(courseIntent);
        finish();
    }

}