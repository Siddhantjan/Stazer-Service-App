package stazer.user.androidstazerserviceapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;

public class SplashScreenActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    private static int SPLASH_SCREEN_TIMER=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user != null){
          //  FirebaseInstanceId.getInstance()
            //        .getInstanceId()
              //      .addOnFailureListener(e -> Toast.makeText(SplashScreenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
               //     .addOnSuccessListener(instanceIdResult -> {
                   //     Log.d("TOKEN", instanceIdResult.getToken());
                     //   UserUtils.updateToken(SplashScreenActivity.this,instanceIdResult.getToken());
                       // Toast.makeText(this, "Token is :"+instanceIdResult.getToken(), Toast.LENGTH_SHORT).show();
                    //});
            gotoHomeScreen();
        }
        else {
            delaySplashScreen();
        }

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

    private void delaySplashScreen() {
        new Handler().postDelayed(() -> {
            Intent authIntent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
            startActivity(authIntent);
            finish();
        },SPLASH_SCREEN_TIMER);
    }


}