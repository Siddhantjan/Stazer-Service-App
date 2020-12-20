package stazer.user.androidstazerserviceapp.services.electrician;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import stazer.user.androidstazerserviceapp.AllRatesCard.ElectricianRateCard.ElectricianRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class ElectricianActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_electrician);

        findViewById(R.id.btn_book_ele).setOnClickListener(v -> goToElectricianBooking());
        findViewById(R.id.scheduleServiceEle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleServiceEle();
            }
        });

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
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Electrician Service");
        startActivity(bookingIntent);
    }

}