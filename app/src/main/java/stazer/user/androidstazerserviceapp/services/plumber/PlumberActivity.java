package stazer.user.androidstazerserviceapp.services.plumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;

public class PlumberActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_plumber);

        findViewById(R.id.btn_book_plumber).setOnClickListener(v -> gotoPlumberbooking());
        findViewById(R.id.scheduleServicePlumber).setOnClickListener(v -> scheduleServicePlu());
    }

    private void scheduleServicePlu() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType","Plumber Service");
        startActivity(serviceIntent);
    }
    private void gotoPlumberbooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Plumber Service");
        startActivity(bookingIntent);
    }
}