package stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderBookingActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class CarWashCategoryActivity extends AppCompatActivity {
   private String serviceName;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        serviceName = getIntent().getStringExtra("serviceTypeCar");
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_car_wash_category);

        // assign service name


        findViewById(R.id.ecoWash).setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Economic Wash");
            startActivity(categoryIntent);
        });
        findViewById(R.id.stdWash).setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Standard Wash");
            startActivity(categoryIntent);
        });

        findViewById(R.id.premiumWash).setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Premium Wash");
            startActivity(categoryIntent);
        });

    }
}