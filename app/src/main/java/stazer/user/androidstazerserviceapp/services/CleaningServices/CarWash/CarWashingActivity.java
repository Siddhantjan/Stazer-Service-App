package stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import stazer.user.androidstazerserviceapp.R;

public class CarWashingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_car_washing);
        findViewById(R.id.hatchbackCar).setOnClickListener(v -> {
            Intent carWashServiceIntent = new Intent(getApplicationContext(), CarWashCategoryActivity.class);
            carWashServiceIntent.putExtra("serviceTypeCar","HatchBack Car Wash Service");
            carWashServiceIntent.putExtra("ServiceName","HatchBack");
            startActivity(carWashServiceIntent);
        });
        findViewById(R.id.sedanCar).setOnClickListener(v -> {
            Intent carWashServiceIntent = new Intent(getApplicationContext(), CarWashCategoryActivity.class);
            carWashServiceIntent.putExtra("serviceTypeCar","Sedan Car Wash Service");
            carWashServiceIntent.putExtra("ServiceName","Sedan");
            startActivity(carWashServiceIntent);
        });

        findViewById(R.id.seaterXUV_5).setOnClickListener(v -> {
            Intent carWashServiceIntent = new Intent(getApplicationContext(), CarWashCategoryActivity.class);
            carWashServiceIntent.putExtra("serviceTypeCar","5 Seater SUV Car Wash Service");
            carWashServiceIntent.putExtra("ServiceName","5_seater_SUV");
            startActivity(carWashServiceIntent);
        });
        findViewById(R.id.seaterXUV_7).setOnClickListener(v -> {
            Intent carWashServiceIntent = new Intent(getApplicationContext(), CarWashCategoryActivity.class);
            carWashServiceIntent.putExtra("serviceTypeCar","7 Seater SUV Car Wash Service");
            carWashServiceIntent.putExtra("ServiceName","7_seater_SUV");

            startActivity(carWashServiceIntent);
        });

        findViewById(R.id.luxury).setOnClickListener(v -> {
            Intent carWashServiceIntent = new Intent(getApplicationContext(), CarWashCategoryActivity.class);
            carWashServiceIntent.putExtra("serviceTypeCar","Luxury Car Wash Service");
            carWashServiceIntent.putExtra("ServiceName","Luxury");

            startActivity(carWashServiceIntent);
        });
    }
}