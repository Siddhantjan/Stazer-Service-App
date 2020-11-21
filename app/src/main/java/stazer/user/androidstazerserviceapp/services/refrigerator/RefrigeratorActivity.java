package stazer.user.androidstazerserviceapp.services.refrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.R;

public class RefrigeratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_refrigerator);
        findViewById(R.id.btn_book_refrigerator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRefrigeratorbooking();
            }
        });
    }

    private void gotoRefrigeratorbooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Refrigerator Service");
        startActivity(bookingIntent);
    }
}