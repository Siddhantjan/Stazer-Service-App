package stazer.user.androidstazerserviceapp.services.WaterDispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.AllRatesCard.waterDispenser.WaterDispenserRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class WaterDispenserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_water_dispenser);
        findViewById(R.id.btn_book_WaterDispenser).setOnClickListener(v -> goToWaterDispenserBooking());
        findViewById(R.id.scheduleServiceWaterDispenser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleServiceDispenser();
            }
        });

        findViewById(R.id.waterDispenser_RateCard).setOnClickListener(v -> {
            Intent serviceIntent = new Intent(getApplicationContext(), WaterDispenserRateCardActivity.class);
            startActivity(serviceIntent);
        });
    }

    private void scheduleServiceDispenser() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType", "Water Dispenser Service");
        startActivity(serviceIntent);
    }

    private void goToWaterDispenserBooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType", "Water Dispenser Service");
        startActivity(bookingIntent);
    }
}
