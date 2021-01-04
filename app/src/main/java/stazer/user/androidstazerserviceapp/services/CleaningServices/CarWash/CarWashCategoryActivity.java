package stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderBookingActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class CarWashCategoryActivity extends AppCompatActivity {
   private String serviceName;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextView mEcoWash,mStdWash,mPremiumWash;
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        serviceName = getIntent().getStringExtra("serviceTypeCar");
        String nameOfCar = getIntent().getStringExtra("ServiceName");
        FirebaseDatabase.getInstance().getReference().child("RateCards").child("CarWashingRates")
                .child(nameOfCar)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String  EcoWash  = Objects.requireNonNull(snapshot.child("EcoWash").getValue()).toString();
                            String  StdWash = Objects.requireNonNull(snapshot.child("StdWash").getValue()).toString();
                            String  PremiumWash = Objects.requireNonNull(snapshot.child("PremiumWash").getValue()).toString();
                            mEcoWash.setText("Price :"+"₹"+EcoWash);
                            mStdWash.setText("Price :"+"₹"+StdWash);
                            mPremiumWash.setText("Price :"+"₹"+PremiumWash);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CarWashCategoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        super.onStart();

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
        mEcoWash = findViewById(R.id.priceEco);
        mStdWash= findViewById(R.id.priceStandard);
        mPremiumWash = findViewById(R.id.pricePremium);


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