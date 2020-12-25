package stazer.user.androidstazerserviceapp.services.AirCooler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.AllRatesCard.AirCooler.AirCoolerRateCardActivity;
import stazer.user.androidstazerserviceapp.AllRatesCard.ElectricianRateCard.ElectricianRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class AirCoolerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_air_cooler);
        findViewById(R.id.btn_book_airCooler).setOnClickListener(v -> goToAirCoolerBooking());
        findViewById(R.id.scheduleServiceAirCooler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleServiceCooler();
            }
        });

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
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType", "Air Cooler Service");
        startActivity(bookingIntent);
    }
}
