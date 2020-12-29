package stazer.user.androidstazerserviceapp.services.AirCooler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;

import stazer.user.androidstazerserviceapp.AllRatesCard.AirCooler.AirCoolerRateCardActivity;
import stazer.user.androidstazerserviceapp.AllRatesCard.ElectricianRateCard.ElectricianRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.R;

public class AirCoolerActivity extends AppCompatActivity {
    int cHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_air_cooler);

        //initialize calender
        Calendar calendar = Calendar.getInstance();

        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);

        findViewById(R.id.btn_book_airCooler).setOnClickListener(v -> goToAirCoolerBooking());
        findViewById(R.id.scheduleServiceAirCooler).setOnClickListener(v -> scheduleServiceCooler());

        findViewById(R.id.airCooler_RateCard).setOnClickListener(v -> {
            Intent serviceIntent = new Intent(getApplicationContext(), AirCoolerRateCardActivity.class);
            startActivity(serviceIntent);
        });
    }

    private void scheduleServiceCooler() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType", "Air Cooler Service");
        startActivity(serviceIntent);
    }

    private void goToAirCoolerBooking() {
        if (cHour >= Common.COMPANY_START_TIME && cHour < Common.COMPANY_STOP_TIME) {
            Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
            bookingIntent.putExtra("serviceType", "Air Cooler Service");
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
