package stazer.user.androidstazerserviceapp.AllRatesCard.Geyser;

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

public class GeyserRateCardActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    RecyclerView recView;
    GeyserRateCardAdapter geyserRateCardAdapter;

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        geyserRateCardAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        geyserRateCardAdapter.startListening();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_geyser_rate_card);

        recView = findViewById(R.id.geyserRateCardView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RateModel> options =
                new FirebaseRecyclerOptions.Builder<RateModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RateCards").child("Geyser"), RateModel.class)
                        .build();
        geyserRateCardAdapter = new GeyserRateCardAdapter(options);
        recView.setAdapter(geyserRateCardAdapter);
    }
}