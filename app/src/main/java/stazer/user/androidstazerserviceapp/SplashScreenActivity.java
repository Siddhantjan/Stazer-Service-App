package stazer.user.androidstazerserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMER=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


    }

    @Override
    protected void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user != null){
            gotoHomeScreen();
        }
        else {
            delaySplashScreen();
        }

        super.onStart();
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