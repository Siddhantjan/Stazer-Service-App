package stazer.user.androidstazerserviceapp.Education.EducationBooking;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import stazer.user.androidstazerserviceapp.AllRatesCard.Ac.AcRateCardAdapter;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.MainActivity;
import stazer.user.androidstazerserviceapp.Model.CoursesBookingDetailsModel;
import stazer.user.androidstazerserviceapp.Model.RateModel;
import stazer.user.androidstazerserviceapp.R;

public class RunningCoursesActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    RecyclerView recView;
    RunningCourseAdapter runningCourseAdapter;
    FirebaseDatabase database;
    DatabaseReference userInfoRef;


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
        runningCourseAdapter.startListening();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        runningCourseAdapter.startListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_running_courses);
        recView = findViewById(R.id.course_booking);
        database = FirebaseDatabase.getInstance();
        userInfoRef = database.getReference(Common.USER_INFO_REFERENCE);

        recView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<CoursesBookingDetailsModel> options =
                new FirebaseRecyclerOptions.Builder<CoursesBookingDetailsModel>()
                        .setQuery(userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Education"), CoursesBookingDetailsModel.class)
                        .build();
        runningCourseAdapter = new RunningCourseAdapter(options);
        recView.setAdapter(runningCourseAdapter);
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