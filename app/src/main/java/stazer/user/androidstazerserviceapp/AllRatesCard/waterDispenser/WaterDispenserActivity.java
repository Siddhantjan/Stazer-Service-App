package stazer.user.androidstazerserviceapp.AllRatesCard.waterDispenser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.Model.RateModel;
import stazer.user.androidstazerserviceapp.R;

public class WaterDispenserActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    RecyclerView recView;
    WaterDispenserRateCardAdapter waterDispenserRateCardAdapter;
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        waterDispenserRateCardAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        waterDispenserRateCardAdapter.startListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_water_dispenser);
        recView = findViewById(R.id.waterDispenserRateCardView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RateModel> options =
                new FirebaseRecyclerOptions.Builder<RateModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RateCards").child("Water Dispenser"), RateModel.class)
                        .build();
        waterDispenserRateCardAdapter = new WaterDispenserRateCardAdapter(options);
        recView.setAdapter(waterDispenserRateCardAdapter);
    }
}