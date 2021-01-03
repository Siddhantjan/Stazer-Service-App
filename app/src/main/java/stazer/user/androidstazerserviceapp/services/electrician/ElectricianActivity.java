package stazer.user.androidstazerserviceapp.services.electrician;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.AllRatesCard.ElectricianRateCard.ElectricianRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.R;

public class ElectricianActivity extends AppCompatActivity {
TextView mEleRate;

    @Override
    protected void onStart() {
        FirebaseDatabase.getInstance().getReference().child("RateCards").child("ServiceRates")
                .child("Electrician").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String rate = Objects.requireNonNull(snapshot.child("rateEle").getValue()).toString();
                    mEleRate.setText("₹ "+rate);
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_electrician);

        mEleRate = findViewById(R.id.eleRate);
        findViewById(R.id.btn_book_ele).setOnClickListener(v -> goToElectricianBooking());
        findViewById(R.id.scheduleServiceEle).setOnClickListener(v -> scheduleServiceEle());

        findViewById(R.id.electrician_RateCard).setOnClickListener(v -> {
            Intent serviceIntent = new Intent(getApplicationContext(), ElectricianRateCardActivity.class);
            startActivity(serviceIntent);
        });
    }

    private void scheduleServiceEle() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType","Electrician Service");
        startActivity(serviceIntent);
    }

    private void goToElectricianBooking() {
        int cHour;
        //initialize calender
        Calendar calendar = Calendar.getInstance();
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (cHour >= Common.COMPANY_START_TIME && cHour < Common.COMPANY_STOP_TIME) {
            Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
            bookingIntent.putExtra("serviceType", "Electrician Service");
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