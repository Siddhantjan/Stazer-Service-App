package stazer.user.androidstazerserviceapp.services.CleaningServices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash.CarWashingActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.Sanitization.SanitizationServiceActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.bathroomCleaning.BathroomCleaningActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.carpetCleaning.CarpetCleaningActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.kitchenCleaning.KitchenCleaningActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.sofaCleaning.SofaCleaningActivity;

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
        findViewById(R.id.bathroomCleaningServices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BathroomIntent = new Intent(getApplicationContext(), BathroomCleaningActivity.class);
                startActivity(BathroomIntent);
            }
        });
        findViewById(R.id.kitchenCleaningService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kitchenCleaningIntent = new Intent(getApplicationContext(), KitchenCleaningActivity.class);
                startActivity(kitchenCleaningIntent);
            }
        });
        findViewById(R.id.sanitizationService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sanitizationIntent = new Intent(getApplicationContext(), SanitizationServiceActivity.class);
                startActivity(sanitizationIntent);
            }
        });
        findViewById(R.id.carpetCleaningServices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carpetCleaningIntent = new Intent(getApplicationContext(), CarpetCleaningActivity.class);
                startActivity(carpetCleaningIntent);
            }
        });
        findViewById(R.id.sofaCleaningService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sofaCleaningIntent = new Intent(getApplicationContext(), SofaCleaningActivity.class);
                startActivity(sofaCleaningIntent);
            }
        });
    }
}