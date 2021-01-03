package stazer.user.androidstazerserviceapp.services.acservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.AllRatesCard.Ac.AcRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.R;

public class AcServiceActivity extends AppCompatActivity {

    TextView mRateSplit,mRateWindow;
    String service_name = "Ac Service";
    @Override
    protected void onStart() {
        FirebaseDatabase.getInstance().getReference().child("RateCards").child("ServiceRates").child("Ac")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String splitRate = Objects.requireNonNull(snapshot.child("AcSplit").getValue()).toString();
                    String windowRate = Objects.requireNonNull(snapshot.child("AcWindow").getValue()).toString();
                    mRateSplit.setText("₹ "+splitRate);
                    mRateWindow.setText("₹"+windowRate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ac_service);

        mRateWindow = findViewById(R.id.acRateWindow);
        mRateSplit = findViewById(R.id.acRateSplit);


        findViewById(R.id.btn_book_ac).setOnClickListener(v -> bookAcService());
        findViewById(R.id.scheduleServiceAc).setOnClickListener(v -> scheduleServiceAc());
        findViewById(R.id.ac_RateCard).setOnClickListener(v -> {
            Intent rateIntent = new Intent(getApplicationContext(), AcRateCardActivity.class);
            startActivity(rateIntent);
        });
    }

    private void scheduleServiceAc() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType",service_name);
        startActivity(serviceIntent);
    }

    private void bookAcService() {
        int cHour;
        //initialize calender
        Calendar calendar = Calendar.getInstance();
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (cHour >= Common.COMPANY_START_TIME && cHour < Common.COMPANY_STOP_TIME) {
            Intent bookingIntent = new Intent(AcServiceActivity.this, OrderCategoryActivity.class);
            bookingIntent.putExtra("serviceType",service_name);
            startActivity(bookingIntent);
        }
        else {
            AlertDialog.Builder workingHours = new AlertDialog.Builder(this);
            workingHours.setTitle("Not Available");
            workingHours.setMessage("We Are Not Available At this moment  \n" +
                    "Book Next Day Service in Working Hours\n" + "Working Hours : 8:00 AM to 9:00 PM\n");
            workingHours.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = workingHours.create();
            dialog.setCancelable(false);
            dialog.show();
        }

    }
}