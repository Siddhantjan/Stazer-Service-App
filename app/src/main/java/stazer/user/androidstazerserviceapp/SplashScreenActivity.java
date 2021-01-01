package stazer.user.androidstazerserviceapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;

public class SplashScreenActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private static int SPLASH_SCREEN_TIMER=1500;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userInfoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            firebaseDatabase = FirebaseDatabase.getInstance();
            userInfoRef = firebaseDatabase.getReference(Common.USER_INFO_REFERENCE);
            if (user != null) {
                Log.d("User", "onCreate: exists");
                checkUserFromFirebase();
            } else {
                Log.d("User", "onCreate: not exists");
                gotoRegisterScreen();
            }
        },SPLASH_SCREEN_TIMER);


    }

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

    private void gotoHomeScreen() {
        Intent mainIntent;
        mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(mainIntent);
    }

    //Internal Checking Fun
    private void checkUserFromFirebase() {
        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("userInfo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.d("User", "onDataChange: User Present");
                            gotoHomeScreen();
                        } else {
                            Log.d("User", "onDataChange: not registerd");
                            Toast.makeText(SplashScreenActivity.this, "Your Details Not Found Please Register yourself", Toast.LENGTH_SHORT).show();
                            Intent registerIntent = new Intent(getApplicationContext(), UserRegistrationActivity.class);
                            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(registerIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SplashScreenActivity.this, "[Error]" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void gotoRegisterScreen() {
        Log.d("User", "gotoRegisterScreen: login screen");
            Intent authIntent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
            startActivity(authIntent);
            finish();
    }


}