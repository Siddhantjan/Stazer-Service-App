package stazer.user.androidstazerserviceapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.BookingInfo.BookingActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.OrderBookingActivity;
import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;
import stazer.user.androidstazerserviceapp.Common.Common;
import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.Company.AboutUsActivity;
import stazer.user.androidstazerserviceapp.Company.OurVisionActivity;
import stazer.user.androidstazerserviceapp.Company.TermsAndConditionActivity;
import stazer.user.androidstazerserviceapp.Education.EducationBooking.RunningCoursesActivity;
import stazer.user.androidstazerserviceapp.Education.ProgramSectionActivity;
import stazer.user.androidstazerserviceapp.HeplerClasses.homeScreenAds.homeScreenAdsRecyclerViewAdapter;
import stazer.user.androidstazerserviceapp.HeplerClasses.homeScreenAds.homeScreenAdsRecyclerViewHelperClass;
import stazer.user.androidstazerserviceapp.HomeAppliance.HomeApplianceActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.AllCleaningServicesActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.CarWash.CarWashingActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.Sanitization.SanitizationServiceActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.bathroomCleaning.BathroomCleaningActivity;
import stazer.user.androidstazerserviceapp.services.CleaningServices.kitchenCleaning.KitchenCleaningActivity;
import stazer.user.androidstazerserviceapp.services.Construction.ConstructionWorkActivity;
import stazer.user.androidstazerserviceapp.services.acservice.AcServiceActivity;
import stazer.user.androidstazerserviceapp.services.carpenter.CarpenterActivity;
import stazer.user.androidstazerserviceapp.services.electrician.ElectricianActivity;
import stazer.user.androidstazerserviceapp.services.geyser.GeyserActivity;
import stazer.user.androidstazerserviceapp.services.plumber.PlumberActivity;
import stazer.user.androidstazerserviceapp.services.refrigerator.RefrigeratorActivity;
import stazer.user.androidstazerserviceapp.services.roservice.RoServiceActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    //Variables
    static final float END_SCALE = 0.7f;
    FirebaseAuth firebaseAuth;
    private ImageView menuOpenIcon;
    //Home Ads RecyclerView
    RecyclerView homeScreenAdsLayout;
    RecyclerView.Adapter homeScreenAdsLayoutAdapter;
    LinearLayout contentView;

    //HomeDrawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseReference userInfoRef;

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        firebaseAuth = FirebaseAuth.getInstance();
        userInfoRef  = FirebaseDatabase.getInstance().getReference(Common.USER_INFO_REFERENCE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userInfoRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("userInfo")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String firstName = snapshot.child("firstName").getValue().toString();
                                String lastName = snapshot.child("lastName").getValue().toString();
                                String mobileNumber = snapshot.child("phoneNumber").getValue().toString();
                                String userName = firstName + " " + lastName;
                                View headerView = navigationView.getHeaderView(0);
                                TextView txt_name = (TextView) headerView.findViewById(R.id.yourName);
                                TextView txt_number = (TextView) headerView.findViewById(R.id.yourMobile);
                                txt_name.setText("Welcome! "+ userName);
                                txt_number.setText(mobileNumber);
                                Log.d("TAG", "onDataChange: User Present");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "[Error]" + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

            //checkUserFromFirebase();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        menuOpenIcon = findViewById(R.id.menu_openIcon);
        contentView = findViewById(R.id.content);
        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationDrawer();

        //Education
        findViewById(R.id.education).setOnClickListener(v -> {
            Intent educationIntent = new Intent(getApplicationContext(), ProgramSectionActivity.class);
            startActivity(educationIntent);
        });

        // Popular Service
        /* ---------------------------------------------------------- Start ----------------------------------*/
        //Connect Layout to electrician Activity
        findViewById(R.id.popCard1).setOnClickListener(v -> ViewElectricianActivity());
        //Connect Layout to Plumber Activity
        findViewById(R.id.popCard2).setOnClickListener(v -> ViewPlumberActivity());
        //Connect Layout to Air Conditioner Activity
        findViewById(R.id.popCard3).setOnClickListener(v -> ViewAcServiceActivity());
        //Connect Layout to Refrigerator Activity
        findViewById(R.id.popCard4).setOnClickListener(v -> ViewRefrigeratorActivity());
        /* --------------------------------- Close ----------------------------------*/


        //Main Services
        /* --------------------------------- Start ----------------------------------*/
        //Connect Layout to Home Appliance Services
        findViewById(R.id.homeAppliance_main).setOnClickListener(v -> ViewHomeApplianceActivity());
        //Connect Layout to electrician Activity
        findViewById(R.id.electrician_service_main).setOnClickListener(v -> ViewElectricianActivity());
        //Connect Layout to Plumber Activity
        findViewById(R.id.plumber_service_main).setOnClickListener(v -> ViewPlumberActivity());
        //Connect Layout to Ac Service Activity
        findViewById(R.id.ac_service_main).setOnClickListener(v -> ViewAcServiceActivity());
        //Connect Layout to Refrigerator Activity
        findViewById(R.id.refrigerator_service_main).setOnClickListener(v -> ViewRefrigeratorActivity());
        //Connect Layout to Carpenter Activity
        findViewById(R.id.carpenter_service_main).setOnClickListener(v -> ViewCarpenterActivity());
        //Connect Layout to RO Service Activity
        findViewById(R.id.ro_service_main).setOnClickListener(v -> ViewRoServiceActivity());
        //Connect Layout to Geyser Activity
        findViewById(R.id.geyser_service_main).setOnClickListener(v -> ViewGeyserActivity());
        /* --------------------------------- Close ----------------------------------*/
        //RecyclerView layout for ads
        /* --------------------------------- Start ----------------------------------*/
        homeScreenAdsLayout = findViewById(R.id.home_screen_ads_recyclerView);
        homeScreenAdsLayout();

        /* --------------------------------- Close ----------------------------------*/


        //Cleaning Services
        /* --------------------------------- start ----------------------------------*/
        findViewById(R.id.carWash).setOnClickListener(v -> {
            Intent carWashIntent = new Intent(getApplicationContext(), CarWashingActivity.class);
            startActivity(carWashIntent);
        });
        findViewById(R.id.bathroomCleaning).setOnClickListener(v -> {
            Intent BathroomCleaningIntent = new Intent(getApplicationContext(), BathroomCleaningActivity.class);
            startActivity(BathroomCleaningIntent);
        });
        findViewById(R.id.homeSanitization).setOnClickListener(v -> {
            Intent sanitizationIntent = new Intent(getApplicationContext(), SanitizationServiceActivity.class);
            startActivity(sanitizationIntent);
        });
        findViewById(R.id.kitchenCleaning).setOnClickListener(v -> {
            Intent kitchenCleaningIntent = new Intent(getApplicationContext(), KitchenCleaningActivity.class);
            startActivity(kitchenCleaningIntent);
        });
        findViewById(R.id.allCleaningServiceShow).setOnClickListener(v -> {
            Intent cleaningIntent = new Intent(getApplicationContext(), AllCleaningServicesActivity.class);
            startActivity(cleaningIntent);
        });
        /* --------------------------------- Close ----------------------------------*/

        //Major Work
        /* --------------------------------- Start ----------------------------------*/
        findViewById(R.id.constructionService).setOnClickListener(v -> {
            Intent constructionIntent = new Intent(getApplicationContext(), ConstructionWorkActivity.class);
            startActivity(constructionIntent);
        });
        /* --------------------------------- Close ----------------------------------*/

    }

    // Navigation Drawer functions
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuOpenIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorCardBlue));
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent exitIntent = new Intent(Intent.ACTION_MAIN);
                exitIntent.addCategory(Intent.CATEGORY_HOME);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exitIntent);
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, " Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                gotoHomePage();
                break;
            case R.id.nav_booking:
                gotoBookingPage();
                break;
            case R.id.nav_logout:
                logoutUser();
                break;
            case R.id.nav_aboutCompany:
                gotoAboutCompanyPage();
                break;
            case R.id.nav_terms:
                gotoTermsPage();
                break;
            case R.id.nav_values:
                gotoValuesPage();
                break;
            case R.id.nav_share:
                openShareAppActivity();
                break;
            case R.id.nav_rate_us:
                openActivityforRateUs();
                break;
            case R.id.nav_contact:
                openWhatasppActivity();
                break;
            case R.id.nav_Education:
                openCourseActivity();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openCourseActivity() {
        Intent eduIntent = new Intent(getApplicationContext(), RunningCoursesActivity.class);
        startActivity(eduIntent);
    }

    private void openWhatasppActivity() {
        int cHour;
        //initialize calender
        Calendar calendar = Calendar.getInstance();
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (cHour >= Common.USER_FEEDBACK_START_TIME && cHour < Common.USER_FEEDBACK_STOP_TIME) {
            Uri uri = Uri.parse("smsto:" + Common.COMPANY_CONTACT_NUMBER);
            Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
            whatsappIntent.setPackage("com.whatsapp");
            startActivity(whatsappIntent);
        }
        else{
            AlertDialog.Builder workingHours = new AlertDialog.Builder(this);
            workingHours.setTitle("Not Available");
            workingHours.setMessage("We Are Not Available At this moment  \n" +
                    "Send your Complain or Feedback in Between : 7:00 AM to 9:00 PM\n");
            workingHours.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = workingHours.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    private void openActivityforRateUs() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            Log.d("RateAct", "openActivityForRateUs: Play Store Opened");
            startActivity(rateIntent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open [Error]: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openShareAppActivity() {
        try {
            Log.d("ShareAct", "openShareAppActivity: Share done Successfully");
            ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();
            String apkPath = applicationInfo.sourceDir;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/vnd.android.package-archive");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(shareIntent, "ShareVia"));
        } catch (Exception e) {
            Toast.makeText(this, "[Error]: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //Menu  Functions
    private void gotoValuesPage() {
        Intent missionIntent = new Intent(getApplicationContext(), OurVisionActivity.class);
        startActivity(missionIntent);
    }


    private void gotoTermsPage() {
        Intent termsIntent = new Intent(getApplicationContext(), TermsAndConditionActivity.class);
        startActivity(termsIntent);
    }

    private void gotoAboutCompanyPage() {
        Intent aboutIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
        startActivity(aboutIntent);
    }

    private void gotoHomePage() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void gotoBookingPage() {
        Intent bookingIntent = new Intent(getApplicationContext(), BookingActivity.class);
        startActivity(bookingIntent);
    }

    private void logoutUser() {
        firebaseAuth.signOut();
        gotoLoginPage();
    }


    //layout function RecyclerView
    private void homeScreenAdsLayout() {
        homeScreenAdsLayout.setHasFixedSize(true);
        homeScreenAdsLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<homeScreenAdsRecyclerViewHelperClass> adsList = new ArrayList<>();
        adsList.add(new homeScreenAdsRecyclerViewHelperClass(R.drawable.ac, "Super Offer on Ac Cleaning", "Ac Split : ₹499/-", "Ac window : ₹449/-"));
        adsList.add(new homeScreenAdsRecyclerViewHelperClass(R.drawable.refrigerator, "Super Offer on Refrigerator Service", "Single Door : ₹399/-", "MultiDoor : ₹449/-"));
        adsList.add(new homeScreenAdsRecyclerViewHelperClass(R.drawable.washinemachine, "Super Offer on Washing Machine Service", "Normal : ₹349/-", "Automatic : ₹449/-"));

        homeScreenAdsLayoutAdapter = new homeScreenAdsRecyclerViewAdapter(adsList);
        homeScreenAdsLayout.setAdapter(homeScreenAdsLayoutAdapter);

    }

    //layout function
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

    private void ViewHomeApplianceActivity() {
        Intent homeApplianceIntent = new Intent(getApplicationContext(), HomeApplianceActivity.class);
        startActivity(homeApplianceIntent);
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

    // when You Logout
    private void gotoLoginPage() {
        Intent authIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(authIntent);
        finish();
    }


}