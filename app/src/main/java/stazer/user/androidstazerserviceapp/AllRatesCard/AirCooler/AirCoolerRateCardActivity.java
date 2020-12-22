package stazer.user.androidstazerserviceapp.AllRatesCard.AirCooler;

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

public class AirCoolerRateCardActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    RecyclerView recView;
    AirCoolerAdapter airCoolerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_air_cooler_rate_card);
        recView = findViewById(R.id.airCoolerRateCardView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RateModel> options =
                new FirebaseRecyclerOptions.Builder<RateModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RateCards").child("Air Cooler"), RateModel.class)
                        .build();
        airCoolerAdapter = new AirCoolerAdapter(options);
        recView.setAdapter(airCoolerAdapter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        airCoolerAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        airCoolerAdapter.startListening();
    }
}