package stazer.user.androidstazerserviceapp.services.geyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.BookingProcess.OrderCategoryActivity;
import stazer.user.androidstazerserviceapp.R;

public class GeyserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_geyser);
        findViewById(R.id.btn_book_geyser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGeyserBooking();
            }
        });
    }

    private void gotoGeyserBooking() {
        Intent bookingIntent = new Intent(getApplicationContext(), OrderCategoryActivity.class);
        bookingIntent.putExtra("serviceType","Geyser Service");
        startActivity(bookingIntent);
    }
}