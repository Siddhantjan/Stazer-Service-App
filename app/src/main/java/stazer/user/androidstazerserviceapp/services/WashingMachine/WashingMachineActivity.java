package stazer.user.androidstazerserviceapp.services.WashingMachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.AllRatesCard.WashingMachine.WashineMachineRateCardActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class WashingMachineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wahing_machine);
        findViewById(R.id.btn_book_WashingMachine).setOnClickListener(v -> goToWashingMachineBooking());
        findViewById(R.id.scheduleServiceWashingMachine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleServiceWashingMachine();
            }
        });

        findViewById(R.id.washingMachine_RateCard).setOnClickListener(v -> {
            Intent serviceIntent = new Intent(getApplicationContext(), WashineMachineRateCardActivity.class);
            startActivity(serviceIntent);
        });
    }

    private void scheduleServiceWashingMachine() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType", "Washing Machine Service");
        startActivity(serviceIntent);
    }

    private void goToWashingMachineBooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType", "Washing Machine Service");
        startActivity(bookingIntent);
    }
}
