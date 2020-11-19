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
import stazer.user.androidstazerserviceapp.Model.UserModel;
import stazer.user.androidstazerserviceapp.services.electrician.ElectricianActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userInfoRef;
    FirebaseAuth firebaseAuth;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Connect Layout to electrician Activity
      findViewById(R.id.electrician_servicePop).setOnClickListener(v -> {
          ViewElectricianActivity();
      });
        findViewById(R.id.electrician_service_main).setOnClickListener(v -> {
            ViewElectricianActivity();
        });


        //Logout
        btn_logout= findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            gotoLoginPage();
        });
    }

    private void ViewElectricianActivity() {
        Intent electricianIntent = new Intent(getApplicationContext(), ElectricianActivity.class);
        startActivity(electricianIntent);
    }

    @Override
    protected void onStart() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userInfoRef = firebaseDatabase.getReference(Common.USER_INFO_REFERENCE);

        if ( user != null){
            checkUserFromFirebase();
        }
        else {
            gotoLoginPage();
        }
        super.onStart();
    }

    private void checkUserFromFirebase() {
        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(MainActivity.this, "Welcome!"+ Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent registerIntent = new Intent(getApplicationContext(),UserRegistrationActivity.class);
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
        Intent authIntent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(authIntent);
        finish();
    }
    
}