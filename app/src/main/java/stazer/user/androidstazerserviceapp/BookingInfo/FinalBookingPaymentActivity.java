package stazer.user.androidstazerserviceapp.BookingInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.Model.ServiceCompletedModel;
import stazer.user.androidstazerserviceapp.R;

public class FinalBookingPaymentActivity extends AppCompatActivity {
    private Spinner mFeedbackRating;
    private Button mServiceDone;
    private TextView mServiceType,mStatus;
    private EditText mMechanicName, mFeedbackText;
    FirebaseDatabase database;
    DatabaseReference adminInfoRef, userInfoRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking_payment);
        database = FirebaseDatabase.getInstance();
        adminInfoRef = database.getReference(Common.ADMIN_INFO_REFERENCE);
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);

        //hooks
        mServiceType = findViewById(R.id.serviceBookedName);
        mServiceType.setText(getIntent().getStringExtra("Service"));


        mFeedbackRating = findViewById(R.id.feedbackRating);
        mMechanicName = findViewById(R.id.mechanicName);
        mFeedbackText = findViewById(R.id.feedbackText);

        mServiceDone = findViewById(R.id.serviceDone);

        String[] feedback = {"Excellent", "Best", "Good", "Poor"};
        ArrayAdapter<String> feedbackAdapter = new ArrayAdapter<String>(this, R.layout.spinner_options, feedback);
        mFeedbackRating.setAdapter(feedbackAdapter);

        mServiceDone.setOnClickListener(v -> {
            AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(FinalBookingPaymentActivity.this);
            final EditText mAmount = new EditText(FinalBookingPaymentActivity.this);
            amountDialogBuilder.setTitle("Enter The amount Which You Give to Our Service Man");
            mAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
            mAmount.setHint("Enter Amount");
            amountDialogBuilder.setView(mAmount);
            amountDialogBuilder.setPositiveButton("CONFIRM", (dialog, which) -> {
                ServiceCompletedModel serviceCompletedModel = new ServiceCompletedModel();
                if (!TextUtils.isEmpty(mAmount.getText().toString())) {

                    serviceCompletedModel.setServiceName(mServiceType.getText().toString());
                    serviceCompletedModel.setAmount(mAmount.getText().toString());

                    adminInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrderCompleted")
                            .setValue(serviceCompletedModel)
                            .addOnCompleteListener(task -> {
                                mServiceDone.setEnabled(false);

                                Toast.makeText(FinalBookingPaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                goToHomeActivity();
                            }).addOnFailureListener(e -> Toast.makeText(FinalBookingPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    dialog.dismiss();
                } else {
                    serviceCompletedModel.setServiceName(mServiceType.getText().toString());
                    serviceCompletedModel.setAmount(mAmount.getText().toString());
                    adminInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrderCompleted")
                            .setValue(serviceCompletedModel)
                            .addOnCompleteListener(task -> {
                                mServiceDone.setEnabled(false);
                                Toast.makeText(FinalBookingPaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                goToHomeActivity();
                            }).addOnFailureListener(e -> Toast.makeText(FinalBookingPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                    Toast.makeText(FinalBookingPaymentActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();

                }

            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
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