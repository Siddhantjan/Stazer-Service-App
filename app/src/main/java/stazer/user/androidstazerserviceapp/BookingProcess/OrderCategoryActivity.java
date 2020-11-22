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

    RelativeLayout installationLayout,servicelayout,repairLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oder_category);
       // assign service name
        String serviceName = getIntent().getStringExtra("serviceType");
        // save Service name
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serviceName", serviceName);
        editor.apply();

        //hooks
        installationLayout=findViewById(R.id.category_type_1);
        servicelayout = findViewById(R.id.category_type_3);
        repairLayout = findViewById(R.id.category_type_2);

        installationLayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("categoryType","Installation / unInstallation");
            startActivity(categoryIntent);
        });

        servicelayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("categoryType","Service");
            startActivity(categoryIntent);
        });


        repairLayout.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), OrderBookingActivity.class);
            categoryIntent.putExtra("categoryType","Repair");
            startActivity(categoryIntent);
        });
    }
}