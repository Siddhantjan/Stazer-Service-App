package stazer.user.androidstazerserviceapp.BookingProcess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import stazer.user.androidstazerserviceapp.R;

public class OrderCategoryActivity extends AppCompatActivity {
    String serviceName;
    @Override
    protected void onStart() {
        super.onStart();
        serviceName = getIntent().getStringExtra("serviceType");
    }

    RelativeLayout installationLayout,servicelayout,repairLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oder_category);
        //hooks
        installationLayout=findViewById(R.id.category_type_1);
        servicelayout = findViewById(R.id.category_type_3);
        repairLayout = findViewById(R.id.category_type_2);

        installationLayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Installation / unInstallation");
            startActivity(categoryIntent);
        });

        servicelayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Service");
            startActivity(categoryIntent);
        });


        repairLayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("serviceType",serviceName);
            categoryIntent.putExtra("categoryType","Repair");
            startActivity(categoryIntent);
        });
    }
}