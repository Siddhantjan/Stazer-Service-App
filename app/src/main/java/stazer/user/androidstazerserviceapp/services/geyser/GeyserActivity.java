package stazer.user.androidstazerserviceapp.services.geyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.AllRatesCard.Ac.AcRateCardActivity;
import stazer.user.androidstazerserviceapp.AllRatesCard.Geyser.GeyserRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class GeyserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_geyser);
        findViewById(R.id.btn_book_geyser).setOnClickListener(v -> gotoGeyserBooking());
        findViewById(R.id.scheduleServiceGeyser).setOnClickListener(v -> scheduleServiceGeyser());
        findViewById(R.id.geyser_RateCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rateIntent = new Intent(getApplicationContext(), GeyserRateCardActivity.class);
                startActivity(rateIntent);
            }
        });
    }

    private void scheduleServiceGeyser() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType","Geyser Service");
        startActivity(serviceIntent);
    }
    private void gotoGeyserBooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Geyser Service");
        startActivity(bookingIntent);
    }
}