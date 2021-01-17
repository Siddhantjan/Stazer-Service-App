package stazer.user.androidstazerserviceapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Objects;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;

public class UserRegistrationActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userInfoRef;
    FirebaseAuth firebaseAuth;
    private Button btnUserRegister;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPhoneNumber;
    private EditText editFlatno;
    private EditText editArea;
    private EditText editLandmark;

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_registration);
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editPhoneNumber = findViewById(R.id.edit_mobile_number);
        btnUserRegister = findViewById(R.id.btn_user_register);
        editFlatno = findViewById(R.id.edit_house_no);
        editArea = findViewById(R.id.edit_Area);
        editLandmark = findViewById(R.id.edit_landmark);
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        userInfoRef = firebaseDatabase.getReference(Common.USER_INFO_REFERENCE);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            checkUserFromFirebase();
        }
    }

    private void checkUserFromFirebase() {
        userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("userInfo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String firstName = Objects.requireNonNull(snapshot.child("firstName").getValue()).toString();
                            String lastName = Objects.requireNonNull(snapshot.child("lastName").getValue()).toString();
                            String mobileNumber = Objects.requireNonNull(snapshot.child("phoneNumber").getValue()).toString();
                            String flatno = Objects.requireNonNull(snapshot.child("flatNo").getValue()).toString();
                            String Area = Objects.requireNonNull(snapshot.child("area").getValue()).toString();
                            String Landmark = Objects.requireNonNull(snapshot.child("landmark").getValue()).toString();
                            editFirstName.setText(firstName);
                            editLastName.setText(lastName);
                            editFlatno.setText(flatno);
                            editArea.setText(Area);
                            editLandmark.setText(Landmark);
                            editPhoneNumber.setText(mobileNumber);
                            goToHomeActivity();
                        } else {
                            if (FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null
                                    && !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()))
                                editPhoneNumber.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            btnUserRegister.setOnClickListener(v -> {
                                if (editFirstName.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (editLastName.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (editPhoneNumber.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (editFlatno.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter House/Flat no", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (editArea.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter Area no", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (editLandmark.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Enter Landmark no", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    HashMap<String, Object> userInfoMap = new HashMap<>();
                                    userInfoMap.put("firstName", editFirstName.getText().toString());
                                    userInfoMap.put("lastName", editLastName.getText().toString());
                                    userInfoMap.put("phoneNumber", editPhoneNumber.getText().toString());
                                    userInfoMap.put("flatNo", editFlatno.getText().toString());
                                    userInfoMap.put("area", editArea.getText().toString());
                                    userInfoMap.put("landmark", editLandmark.getText().toString());

                                    userInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userInfo")
                                            .setValue(userInfoMap)
                                            .addOnFailureListener(e -> Toast.makeText(UserRegistrationActivity.this, "[Error]:" + e.getMessage(), Toast.LENGTH_SHORT).show()).addOnSuccessListener(aVoid -> {
                                        Toast.makeText(UserRegistrationActivity.this, "information is Saved Successfully", Toast.LENGTH_SHORT).show();
                                        goToHomeActivity();
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserRegistrationActivity.this, "[Error]" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeActivity() {
        Intent mainIntent;
        mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}