package stazer.user.androidstazerserviceapp.HomeAppliance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.R;
import stazer.user.androidstazerserviceapp.services.AirCooler.AirCoolerActivity;
import stazer.user.androidstazerserviceapp.services.WashingMachine.WashingMachineActivity;
import stazer.user.androidstazerserviceapp.services.WaterDispenser.WaterDispenserActivity;
import stazer.user.androidstazerserviceapp.services.acservice.AcServiceActivity;
import stazer.user.androidstazerserviceapp.services.carpenter.CarpenterActivity;
import stazer.user.androidstazerserviceapp.services.electrician.ElectricianActivity;
import stazer.user.androidstazerserviceapp.services.geyser.GeyserActivity;
import stazer.user.androidstazerserviceapp.services.plumber.PlumberActivity;
import stazer.user.androidstazerserviceapp.services.refrigerator.RefrigeratorActivity;
import stazer.user.androidstazerserviceapp.services.roservice.RoServiceActivity;

public class HomeApplianceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_appliance);
        findViewById(R.id.electrician_service_appliance).setOnClickListener(v -> ViewElectricianActivity());
        findViewById(R.id.plumber_service_appliance).setOnClickListener(v -> ViewPlumberActivity());
        findViewById(R.id.ac_service_appliance).setOnClickListener(v -> ViewAcServiceActivity());

        findViewById(R.id.ro_service_appliance).setOnClickListener(v -> ViewRoServiceActivity());
        findViewById(R.id.refrigerator_service_appliance).setOnClickListener(v -> ViewRefrigeratorActivity());
        findViewById(R.id.carpenter_service_appliance).setOnClickListener(v -> ViewCarpenterActivity());
        findViewById(R.id.geyser_service_appliance).setOnClickListener(v -> ViewGeyserActivity());
        findViewById(R.id.painter_service_appliance).setOnClickListener(v -> {
            Toast.makeText(this, "Not Created", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.cooler_service_appliance).setOnClickListener(v -> {
            Intent coolerIntent = new Intent(getApplicationContext(), AirCoolerActivity.class);
            startActivity(coolerIntent);
        });
        findViewById(R.id.washingMachineView).setOnClickListener(v -> {
            Intent washingMachineIntent = new Intent(getApplicationContext(), WashingMachineActivity.class);
            startActivity(washingMachineIntent);
        });
        findViewById(R.id.waterDispenserView).setOnClickListener(v -> {
            Intent waterDispenserIntent = new Intent(getApplicationContext(), WaterDispenserActivity.class);
            startActivity(waterDispenserIntent);
        });

    }
    private void ViewGeyserActivity() {
        Intent geyserIntent = new Intent(getApplicationContext(), GeyserActivity.class);
        startActivity(geyserIntent);
    }

    private void ViewRoServiceActivity() {
        Intent roServiceIntent = new Intent(getApplicationContext(), RoServiceActivity.class);
        startActivity(roServiceIntent);
    }

    private void ViewCarpenterActivity() {
        Intent carpenterIntent = new Intent(getApplicationContext(), CarpenterActivity.class);
        startActivity(carpenterIntent);
    }


    private void ViewRefrigeratorActivity() {
        Intent refrigeratorIntent = new Intent(getApplicationContext(), RefrigeratorActivity.class);
        startActivity(refrigeratorIntent);
    }

    private void ViewAcServiceActivity() {
        Intent acServiceIntent = new Intent(getApplicationContext(), AcServiceActivity.class);
        startActivity(acServiceIntent);
    }

    private void ViewPlumberActivity() {
        Intent plumberIntent = new Intent(getApplicationContext(), PlumberActivity.class);
        startActivity(plumberIntent);
    }

    private void ViewElectricianActivity() {
        Intent electricianIntent = new Intent(getApplicationContext(), ElectricianActivity.class);
        startActivity(electricianIntent);
    }
}