package stazer.user.androidstazerserviceapp.services.CleaningServices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash.CarWashingActivity;

public class AllCleaningServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_cleaning_services);
        findViewById(R.id.casWashingService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carWashIntent = new Intent(getApplicationContext(), CarWashingActivity.class);
                startActivity(carWashIntent);
            }
        });
    }
}