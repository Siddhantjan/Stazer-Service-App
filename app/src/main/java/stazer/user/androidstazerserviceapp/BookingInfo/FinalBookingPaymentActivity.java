package stazer.user.androidstazerserviceapp.BookingInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.R;

public class FinalBookingPaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private Spinner mFeedbackRating;
    private Button mServiceDone, mFeedbackDone, mServiceCancel;
    private TextView mServiceType, mSpareCost, mServiceStatus, mServiceCategory, mServiceDate, mServiceTime, mServiceAmount, mServiceAddress;
    private EditText mMechanicName, mFeedbackText;
    FirebaseDatabase database;
    DatabaseReference adminInfoRef, userInfoRef;
    private String Rating;
    private String cServiceType, cServiceStatus, cServiceCategory, cServiceDate, cServiceTime, cServiceAmount, cID, cServiceAddress;
    private CheckBox mExperience, mTiming, mCost, mBehaviour;
    private String reason, cSpareCost;

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        if (mServiceStatus.getText().toString().equals("Completed")) {
            mServiceDone.setEnabled(false);
            mServiceCancel.setEnabled(false);
        } else if (mServiceStatus.getText().toString().equals("Canceled")) {
            mServiceDone.setEnabled(false);
            mServiceCancel.setEnabled(false);
        } else {
            mServiceDone.setEnabled(true);
            mServiceCancel.setEnabled(true);
        }
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
        setContentView(R.layout.activity_final_booking_payment);
        database = FirebaseDatabase.getInstance();
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);


        //hooks
        mServiceType = findViewById(R.id.serviceBookedName);
        mServiceCategory = findViewById(R.id.serviceCategory);
        mServiceStatus = findViewById(R.id.servicingStatus);
        mServiceDate = findViewById(R.id.servicingDate);
        mServiceTime = findViewById(R.id.servicingTime);
        mServiceAddress = findViewById(R.id.serviceAddress);

        mExperience = findViewById(R.id.ch1);
        mBehaviour = findViewById(R.id.ch2);
        mCost = findViewById(R.id.ch3);
        mTiming = findViewById(R.id.ch4);

        mFeedbackDone = findViewById(R.id.sendFeedback);
        mFeedbackRating = findViewById(R.id.feedbackRating);
        mMechanicName = findViewById(R.id.mechanicName);

        mServiceDone = findViewById(R.id.serviceDone);
        mServiceAmount = findViewById(R.id.serviceAmount);
        mServiceCancel = findViewById(R.id.serviceCancel);
        mSpareCost = findViewById(R.id.sparePartAmount);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //getData
            cID = bundle.getString("id");
            cServiceType = bundle.getString("Service");
            cServiceCategory = bundle.getString("Category");
            cServiceStatus = bundle.getString("Status");
            cServiceDate = bundle.getString("Date");
            cServiceTime = bundle.getString("Time");
            cServiceAmount = bundle.getString("Amount");
            cServiceAddress = bundle.getString("Address");
            cSpareCost = bundle.getString("SparePartCost");

            //SetData
            mServiceType.setText(cServiceType);
            mServiceCategory.setText(cServiceCategory);
            mServiceStatus.setText(cServiceStatus);
            mServiceDate.setText(cServiceDate);
            mServiceTime.setText(cServiceTime);
            mServiceAmount.setText(cServiceAmount);
            mServiceAddress.setText(cServiceAddress);
            mSpareCost.setText(cSpareCost);


        }

        String[] feedback = {"Select", "Excellent", "Best", "Good", "Poor"};
        ArrayAdapter<String> feedbackAdapter = new ArrayAdapter<String>(this, R.layout.spinner_options, feedback);
        mFeedbackRating.setAdapter(feedbackAdapter);
        mFeedbackRating.setOnItemSelectedListener(this);

        mServiceDone.setOnClickListener(v -> {
            mServiceStatus.setText("Completed");

            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").child(cID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            snapshot.getRef().child("Status").setValue("Completed");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(FinalBookingPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(FinalBookingPaymentActivity.this);
            final EditText mAmount = new EditText(FinalBookingPaymentActivity.this);
            amountDialogBuilder.setTitle("Enter The amount Which You Give to Our Service Man");
            mAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
            mAmount.setHint("Enter Amount");
            amountDialogBuilder.setView(mAmount);
            amountDialogBuilder.setPositiveButton("NEXT", (dialog, which) -> {
                if (!TextUtils.isEmpty(mAmount.getText().toString())) {
                    userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").child(cID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            snapshot.getRef().child("Amount").setValue(mAmount.getText().toString());
                            goToOpenDialog();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(FinalBookingPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.dismiss();
                }
                else {
                    Toast.makeText(this, "Thanks For Using Us..", Toast.LENGTH_SHORT).show();
                    goToHomeActivity();
                }
            });
            AlertDialog dialog = amountDialogBuilder.create();
            dialog.setCancelable(false);
            dialog.show();

        });

        mFeedbackDone.setOnClickListener(v -> {

            if (mMechanicName.getText().toString().trim().isEmpty()) {
                Toast.makeText(FinalBookingPaymentActivity.this, "Enter Mechanic Name", Toast.LENGTH_SHORT).show();
            } else if (mFeedbackRating.getSelectedItemPosition() < 1) {
                Toast.makeText(FinalBookingPaymentActivity.this, "Please Select Rating", Toast.LENGTH_SHORT).show();
            } else {
                StringBuffer feedBackText = new StringBuffer();
                if (!mTiming.isChecked() && !mExperience.isChecked() && !mCost.isChecked() && !mBehaviour.isChecked()) {
                    feedBackText.append("");
                } else {
                    feedBackText.append("Service man is : ");
                    if (mExperience.isChecked()) {
                        feedBackText.append(" " + mExperience.getText().toString());
                    }
                    if (mBehaviour.isChecked()) {
                        feedBackText.append("," + mBehaviour.getText().toString());
                    }
                    if (mCost.isChecked()) {
                        feedBackText.append("," + mCost.getText().toString());
                    }
                    if (mTiming.isChecked()) {
                        feedBackText.append("," + mTiming.getText().toString());
                    }
                }
                String feedbackTxt = feedBackText.toString();

                HashMap<String, Object> feedbackMap = new HashMap<>();
                feedbackMap.put("rating", Rating);
                try {
                    feedbackMap.put("feedback", feedbackTxt);

                } catch (Exception e) {
                    Toast.makeText(FinalBookingPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                String mName = mMechanicName.getText().toString();

                userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("FeedbackDetails")
                        .child(mName).push().setValue(feedbackMap)
                        .addOnCompleteListener(task -> Toast.makeText(FinalBookingPaymentActivity.this, "Thanks for Feedback", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(FinalBookingPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

            }
        });

        mServiceCancel.setOnClickListener(v -> {
            showOptionDialog();
        });


    }

    private void goToOpenDialog() {
        AlertDialog.Builder spareBuilder = new AlertDialog.Builder(FinalBookingPaymentActivity.this);
        final EditText mSpareAmount = new EditText(FinalBookingPaymentActivity.this);
        spareBuilder.setTitle("Enter The Spare Part Cost");
        mSpareAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        mSpareAmount.setHint("Enter Spare Cost");
        spareBuilder.setView(mSpareAmount);
        spareBuilder.setPositiveButton("CONFIRM", (dialog, which) -> {
            if (!TextUtils.isEmpty(mSpareAmount.getText().toString())) {
                userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .child("OrdersDetails").child(cID)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().child("SparePartCost").setValue(mSpareAmount.getText().toString());
                                Toast.makeText(getApplicationContext(), "Thanks For Using App.... ", Toast.LENGTH_SHORT).show();
                                goToHomeActivity();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(FinalBookingPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.dismiss();
            } else {
                Toast.makeText(this, "Thanks For Booking.... ", Toast.LENGTH_SHORT).show();
                goToHomeActivity();
            }

        });
        AlertDialog dialog = spareBuilder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showOptionDialog() {
        String[] CancelReasons = {"Service man Can't able to Fix problem", "Service man not reach location", "Service man requested to cancel",
                "Service man delayed to reach location", "Spare Part Cost is too high", "Service man taking too high Cost"};
        AlertDialog.Builder cancelDialogBuilder = new AlertDialog.Builder(this);
        cancelDialogBuilder.setTitle("Cancel Service");
        cancelDialogBuilder.setSingleChoiceItems(CancelReasons, 0, (dialog, which) -> reason = CancelReasons[which]);
        cancelDialogBuilder.setPositiveButton("Proceed", (dialog, which) -> {
            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails")
                    .child(cID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().child("Status").setValue("Canceled");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FinalBookingPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails")
                    .child(cID).child("CancelReason").setValue(reason);
            Toast.makeText(getApplicationContext(), "Thanks For Using App.... " + "\n" + "Sorry For Inconvenience" + "\n" + "Next Time we Take Care of it", Toast.LENGTH_SHORT).show();
            goToHomeActivity();
        });
        cancelDialogBuilder.setNegativeButton("Back", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = cancelDialogBuilder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Rating = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}