package stazer.user.androidstazerserviceapp.services.carpenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.acservice.AcServiceActivity;

public class CarpenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carpenter);
        findViewById(R.id.btn_book_carpenter).setOnClickListener(v -> goTOCarpernterbook());
        findViewById(R.id.scheduleServiceCarpenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleServiceCarpenter();
            }
        });
    }

    private void scheduleServiceCarpenter() {
        Intent serviceIntent = new Intent(getApplicationContext(), orderSchduleActivity.class);
        serviceIntent.putExtra("serviceType","Carpenter Service");
        startActivity(serviceIntent);
    }

    private void goTOCarpernterbook() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Carpenter Service");
        startActivity(bookingIntent);
    }
}