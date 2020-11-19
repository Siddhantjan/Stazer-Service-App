package stazer.user.androidstazerserviceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.HomeAppliance.HomeApplianceActivity;
import stazer.user.androidstazerserviceapp.Model.UserModel;
import stazer.user.androidstazerserviceapp.services.acservice.AcServiceActivity;
import stazer.user.androidstazerserviceapp.services.carpenter.CarpenterActivity;
import stazer.user.androidstazerserviceapp.services.electrician.ElectricianActivity;
import stazer.user.androidstazerserviceapp.services.geyser.GeyserActivity;
import stazer.user.androidstazerserviceapp.services.plumber.PlumberActivity;
import stazer.user.androidstazerserviceapp.services.refrigerator.RefrigeratorActivity;
import stazer.user.androidstazerserviceapp.services.roservice.RoServiceActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userInfoRef;
    FirebaseAuth firebaseAuth;
    private Button btn_logout;

    @Override
    protected void onStart() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userInfoRef = firebaseDatabase.getReference(Common.USER_INFO_REFERENCE);

        if (user != null) {
            checkUserFromFirebase();
        } else {
            gotoLoginPage();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Popular Service
        /* ---------------------------------------------------------- Start ----------------------------------*/
        //Connect Layout to electrician Activity
        findViewById(R.id.popCard1).setOnClickListener(v -> ViewElectricianActivity());
        //Connect Layout to Plumber Activity
        findViewById(R.id.popCard2).setOnClickListener(v -> ViewPlumberActivity());
        //Connect Layout to Air Conditioner Activity
        findViewById(R.id.popCard3).setOnClickListener(v -> ViewAcServiceActivity());
        //Connect Layout to Refrigerator Activity
        findViewById(R.id.popCard4).setOnClickListener(v -> ViewRefrigeratorActivity());
        /* --------------------------------- Close ----------------------------------*/


        //Main Services
        /* --------------------------------- Start ----------------------------------*/
        //Connect Layout to Home Appliance Services
        findViewById(R.id.homeAppliance_main).setOnClickListener(v -> ViewHomeApplianceActivity());
        //Connect Layout to electrician Activity
        findViewById(R.id.electrician_service_main).setOnClickListener(v -> ViewElectricianActivity());
        //Connect Layout to Plumber Activity
        findViewById(R.id.plumber_service_main).setOnClickListener(v -> ViewPlumberActivity());
        //Connect Layout to Ac Service Activity
        findViewById(R.id.ac_service_main).setOnClickListener(v -> ViewAcServiceActivity());
        //Connect Layout to Refrigerator Activity
        findViewById(R.id.refrigerator_service_main).setOnClickListener(v -> ViewRefrigeratorActivity());
        //Connect Layout to Carpenter Activity
        findViewById(R.id.carpenter_service_main).setOnClickListener(v -> ViewCarpenterActivity());
        //Connect Layout to RO Service Activity
        findViewById(R.id.ro_service_main).setOnClickListener(v -> ViewRoServiceActivity());
        //Connect Layout to Geyser Activity
        findViewById(R.id.geyser_service_main).setOnClickListener(v -> ViewGeyserActivity());
        /* --------------------------------- Close ----------------------------------*/


        //Tender Services
        /* --------------------------------- start ----------------------------------*/
        findViewById(R.id.hotel_restaurants_tender);
        findViewById(R.id.mall_tender);
        findViewById(R.id.flats_societies_tender);
        findViewById(R.id.hostel_institute_tender);
        /* --------------------------------- Close ----------------------------------*/


        //Logout
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            gotoLoginPage();
        });
    }

    private void ViewGeyserActivity() {
        Intent geyserIntent = new Intent(getApplicationContext(), GeyserActivity.class);
        startActivity(geyserIntent);
    }

    private void ViewRoServiceActivity() {
        Intent roServiceIntent = new Intent(getApplicationContext(), RoServiceActivity.class);
        startActivity(roServiceIntent);
    }

    private void ViewCarpenterActivity() {
        Intent carpenterIntent = new Intent(getApplicationContext(), CarpenterActivity.class);
        startActivity(carpenterIntent);
    }

    private void ViewHomeApplianceActivity() {
        Intent homeApplianceIntent = new Intent(getApplicationContext(), HomeApplianceActivity.class);
        startActivity(homeApplianceIntent);
    }

    private void ViewRefrigeratorActivity() {
        Intent refrigeratorIntent = new Intent(getApplicationContext(), RefrigeratorActivity.class);
        startActivity(refrigeratorIntent);
    }

    private void ViewAcServiceActivity() {
        Intent acServiceIntent = new Intent(getApplicationContext(), AcServiceActivity.class);
        startActivity(acServiceIntent);
    }

    private void ViewPlumberActivity() {
        Intent plumberIntent = new Intent(getApplicationContext(), PlumberActivity.class);
        startActivity(plumberIntent);
    }

    private void ViewElectricianActivity() {
        Intent electricianIntent = new Intent(getApplicationContext(), ElectricianActivity.class);
        startActivity(electricianIntent);
    }


    private void checkUserFromFirebase() {
        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(MainActivity.this, "Welcome!" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent registerIntent = new Intent(getApplicationContext(), UserRegistrationActivity.class);
                            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(registerIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "[Error]" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void gotoLoginPage() {
        Intent authIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(authIntent);
        finish();
    }

}