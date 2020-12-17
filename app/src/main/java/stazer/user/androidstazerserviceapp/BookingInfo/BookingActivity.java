package stazer.user.androidstazerserviceapp.BookingInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.HeplerClasses.ShowBookingInfo.BookingInfoRecyclerViewAdapter;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.Model.FetchBookingDetailsModel;
import stazer.user.androidstazerserviceapp.R;

public class BookingActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    List<FetchBookingDetailsModel> fetchBookingDetailsModels;
    RecyclerView recyclerViewBooking;
    FirebaseDatabase database;
    BookingInfoRecyclerViewAdapter bookingInfoRecyclerViewAdapter;
    DatabaseReference userInfoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_booking);
        recyclerViewBooking = findViewById(R.id.previous_booking);
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);
        viewPreviousBookings();
    }

    private void viewPreviousBookings() {
        recyclerViewBooking.setHasFixedSize(true);
        recyclerViewBooking.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        fetchBookingDetailsModels = new ArrayList<>();
        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("OrdersDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds:snapshot.getChildren()){
                        FetchBookingDetailsModel detailsModel = ds.getValue(FetchBookingDetailsModel.class);
                        fetchBookingDetailsModels.add(detailsModel);
                    }
                    bookingInfoRecyclerViewAdapter = new BookingInfoRecyclerViewAdapter(fetchBookingDetailsModels,getApplicationContext());
                    recyclerViewBooking.setAdapter(bookingInfoRecyclerViewAdapter);
                }
                else
                {
                    Toast.makeText(BookingActivity.this, "You Don't Have Any Bookings", Toast.LENGTH_SHORT).show();
                    goToHomeActivity();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void goToHomeActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToHomeActivity();
        super.onBackPressed();
    }
}