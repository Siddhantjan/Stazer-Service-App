package stazer.user.androidstazerserviceapp.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.Company.OurVisionActivity;
import stazer.user.androidstazerserviceapp.Education.SchoolSection.SchoolSubjectClassroomActivity;
import stazer.user.androidstazerserviceapp.Education.SkillSection.SkillCoursesListActivity;
import stazer.user.androidstazerserviceapp.R;

public class ProgramSectionActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    RelativeLayout mSchoolClassroom,mSkillClassroom;

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_program_section);

        //Hooks
        mSchoolClassroom = findViewById(R.id.schoolClassroom);
        mSkillClassroom = findViewById(R.id.skillClassroom);


        mSchoolClassroom.setOnClickListener(v -> goToSchoolSection());
        mSkillClassroom.setOnClickListener(v -> goToSkillCoursesSection());
    }

    private void goToSkillCoursesSection() {
        Intent skillCourseIntent = new Intent(getApplicationContext(), SkillCoursesListActivity.class);
        startActivity(skillCourseIntent);
    }

    private void goToSchoolSection() {
        Intent schoolIntent = new Intent(getApplicationContext(), SchoolSubjectClassroomActivity.class);
        startActivity(schoolIntent);
    }
}