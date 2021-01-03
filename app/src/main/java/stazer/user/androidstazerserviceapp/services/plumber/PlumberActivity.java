package stazer.user.androidstazerserviceapp.services.plumber;

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

import java.util.Calendar;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.AllRatesCard.ElectricianRateCard.ElectricianRateCardActivity;
import stazer.user.androidstazerserviceapp.AllRatesCard.PlumberRateCard.PlumberRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.R;

public class PlumberActivity extends AppCompatActivity {
TextView mServiceRate;
    @Override
    protected void onStart() {
        FirebaseDatabase.getInstance().getReference().child("RateCards").child("ServiceRates")
                .child("Plumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String rate = Objects.requireNonNull(snapshot.child("ratePlu").getValue()).toString();
                    mServiceRate.setText("₹ "+rate);
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

        setContentView(R.layout.activity_plumber);
        mServiceRate = findViewById(R.id.pluRate);
        findViewById(R.id.btn_book_plumber).setOnClickListener(v -> gotoPlumberbooking());
        findViewById(R.id.scheduleServicePlumber).setOnClickListener(v -> scheduleServicePlu());
        findViewById(R.id.plumber_RateCard).setOnClickListener(v -> {
            Intent serviceIntent = new Intent(getApplicationContext(), PlumberRateCardActivity.class);
            startActivity(serviceIntent);
        });
    }

    private void scheduleServicePlu() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType","Plumber Service");
        startActivity(serviceIntent);
    }
    private void gotoPlumberbooking() {
        int cHour;
        //initialize calender
        Calendar calendar = Calendar.getInstance();
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (cHour >= Common.COMPANY_START_TIME && cHour < Common.COMPANY_STOP_TIME) {

            Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
            bookingIntent.putExtra("serviceType","Plumber Service");
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