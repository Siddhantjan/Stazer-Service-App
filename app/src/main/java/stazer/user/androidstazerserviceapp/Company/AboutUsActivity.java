package stazer.user.androidstazerserviceapp.Company;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import stazer.user.androidstazerserviceapp.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);

        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("STAZER (Formerly Stazer Services) was launched in Dec 2020.It is the largest Home Services platform in Kota.\n" +
                        "        Stazer provide a diverse variety of services at your doorstep to make your lives easier and more efficient.\n" +
                        "        The platform helps customers book reliable home services like Electrician, Plumber, Carpenter, Air conditioner, Purifier, Painter,\n" +
                        "        Also complete constructions work, Our skilled workers are equipped with a solution to almost all your daily problems.\n" +
                        "        Stazer vision is to empower millions of service professionals across the world to deliver service at home like never seen before.")
                .addItem(adsElement.setTitle("Version 1.0"))
                .addGroup("CONNECT WITH US!")
                .addEmail("contact@stazerservices.com")
                .addWebsite("http://www.stazerservices.com/")
                .addYoutube("UCVql9H0RkiRyLtBRKF2Ga_A")   //Enter your youtube link here (replace with my channel link)
                //.addPlayStore("stazer.user.androidstazerserviceapp")   //Replace all this with your package name
                .addInstagram("stazer_service")    //Your instagram id
                .addFacebook("stazer.service")
                .addTwitter("stazer_service")
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }
    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by STAZER", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
         copyright.setIconDrawable(R.drawable.logo);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(v -> Toast.makeText(AboutUsActivity.this,copyrightString,Toast.LENGTH_SHORT).show());
        return copyright;
    }
}