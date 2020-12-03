package stazer.user.androidstazerserviceapp.BookingInfo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.R;

public class FinalBookingPaymentActivity extends AppCompatActivity {
    private Spinner mFeedbackRating;
    private Button mServiceDone;
    private TextView mServiceType,mServiceStatus,mServiceCategory,mServiceDate,mServiceTime,mServiceAmount;
    private EditText mMechanicName, mFeedbackText;
    FirebaseDatabase database;
    DatabaseReference adminInfoRef, userInfoRef;
    private String cServiceType, cServiceStatus,cServiceCategory,cServiceDate,cServiceTime,cServiceAmount;

    @Override
    protected void onStart() {
        if (!mServiceStatus.getText().toString().equals("Completed")){
            mServiceDone.setEnabled(true);
        }
        else {
            mServiceDone.setEnabled(false);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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




        mFeedbackRating = findViewById(R.id.feedbackRating);
        mMechanicName = findViewById(R.id.mechanicName);
        mFeedbackText = findViewById(R.id.feedbackText);
        mServiceDone = findViewById(R.id.serviceDone);
        mServiceAmount = findViewById(R.id.serviceAmount);



        Bundle bundle = getIntent().getExtras();
        if ( bundle != null){
            //getData
            cServiceType = bundle.getString("Service");
            cServiceCategory = bundle.getString("Category");
            cServiceStatus = bundle.getString("Status");
            cServiceDate = bundle.getString("Date");
            cServiceTime = bundle.getString("Time");
            cServiceAmount = bundle.getString("Amount");

            //SetData
            mServiceType.setText(cServiceType);
            mServiceCategory.setText(cServiceCategory);
            mServiceStatus.setText(cServiceStatus);
            mServiceDate.setText(cServiceDate);
            mServiceTime.setText(cServiceTime);
            mServiceAmount.setText(cServiceAmount);


        }

        String[] feedback = {"Excellent", "Best", "Good", "Poor"};
        ArrayAdapter<String> feedbackAdapter = new ArrayAdapter<String>(this, R.layout.spinner_options, feedback);
        mFeedbackRating.setAdapter(feedbackAdapter);

        mServiceDone.setOnClickListener(v -> {
            mServiceStatus.setText("Completed");

            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()){
                            ds.getRef().child("Status").setValue("Completed");
                        }
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
            amountDialogBuilder.setPositiveButton("CONFIRM", (dialog, which) -> {
                if (!TextUtils.isEmpty(mAmount.getText().toString())) {
                   // DatabaseReference myRef = database.getReference(String.valueOf());
                   //Query query1 = myRef.orderByChild("Amount").equalTo(mAmount.getText().toString());
                    userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds:snapshot.getChildren()){
                                ds.getRef().child("Amount").setValue(mAmount.getText().toString());
                                Toast.makeText(getApplicationContext(), "Thanks For Booking.... ", Toast.LENGTH_SHORT).show();

                                goToHomeActivity();
                            }
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

            }).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = amountDialogBuilder.create();
            dialog.show();


        });

    }
    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}