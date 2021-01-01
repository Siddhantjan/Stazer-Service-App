package stazer.user.androidstazerserviceapp.AllRatesCard.Ac;

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

public class AcRateCardActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    RecyclerView recView;
    AcRateCardAdapter acRateCardAdapter;


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        acRateCardAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        acRateCardAdapter.startListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ac_rate_card);
        recView = findViewById(R.id.acRateCardView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RateModel> options =
                new FirebaseRecyclerOptions.Builder<RateModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RateCards").child("Ac Service"), RateModel.class)
                        .build();
        acRateCardAdapter = new AcRateCardAdapter(options);
        recView.setAdapter(acRateCardAdapter);
    }

}