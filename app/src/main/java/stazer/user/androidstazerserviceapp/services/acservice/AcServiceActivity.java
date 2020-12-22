package stazer.user.androidstazerserviceapp.services.acservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.AllRatesCard.Ac.AcRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class AcServiceActivity extends AppCompatActivity {

    String service_name = "Ac Service";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ac_service);
        findViewById(R.id.btn_book_ac).setOnClickListener(v -> bookAcService());
        findViewById(R.id.scheduleServiceAc).setOnClickListener(v -> scheduleServiceAc());
        findViewById(R.id.ac_RateCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rateIntent = new Intent(getApplicationContext(), AcRateCardActivity.class);
                startActivity(rateIntent);
            }
        });
    }

    private void scheduleServiceAc() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType",service_name);
        startActivity(serviceIntent);
    }

    private void bookAcService() {
        Intent bookingIntent = new Intent(AcServiceActivity.this, OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType",service_name);
        startActivity(bookingIntent);
    }
}