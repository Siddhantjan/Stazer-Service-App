package stazer.user.androidstazerserviceapp.BookingProcess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import stazer.user.androidstazerserviceapp.R;

public class OrderBookingActivity extends AppCompatActivity {

    String phoneNumber,serviceName;
    private TextView mName,mMobileNumber,mServiceType,mCategoryType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_booking);

        SharedPreferences prefs =  getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("mobile", NULL);
        serviceName = prefs.getString("serviceName",NULL);

        //hooks
        mServiceType = findViewById(R.id.service_type);
        mServiceType.setText(serviceName);
        mCategoryType = findViewById(R.id.category_type);
        mMobileNumber = findViewById(R.id.customer_mobile);
        mName = findViewById(R.id.customer_name);
        mMobileNumber.setText(phoneNumber);
        mCategoryType.setText(getIntent().getStringExtra("categoryType"));
    }
}