package stazer.user.androidstazerserviceapp.services.acservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;

import stazer.user.androidstazerserviceapp.AllRatesCard.Ac.AcRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.R;

public class AcServiceActivity extends AppCompatActivity {

    int cHour;
    String service_name = "Ac Service";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ac_service);

        //initialize calender
        Calendar calendar = Calendar.getInstance();
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
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
        if (cHour >= Common.COMPANY_START_TIME && cHour < Common.COMPANY_STOP_TIME) {
            Intent bookingIntent = new Intent(AcServiceActivity.this, OrderCategoryActivity.class);
            bookingIntent.putExtra("serviceType",service_name);
            startActivity(bookingIntent);
        }
        else {
            AlertDialog.Builder workingHours = new AlertDialog.Builder(this);
            workingHours.setTitle("Not Available");
            workingHours.setMessage("We Are Not Available At this moment  \n" +
                    "Book Next Day Service in Working Hours\n" + "Working Hours : 8:00 AM to 9:00 Pm\n");
            workingHours.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = workingHours.create();
            dialog.setCancelable(false);
            dialog.show();
        }

    }
}