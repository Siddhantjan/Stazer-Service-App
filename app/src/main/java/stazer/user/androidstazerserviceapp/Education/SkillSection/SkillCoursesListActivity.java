package stazer.user.androidstazerserviceapp.Education.SkillSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import stazer.user.androidstazerserviceapp.Common.NetworkChangeListener;
import stazer.user.androidstazerserviceapp.R;

public class SkillCoursesListActivity extends AppCompatActivity {
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

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
        setContentView(R.layout.activity_skill_courses_list);
        findViewById(R.id.cProgramming).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "C Programming");
            startActivity(courseIntent);
        });
        findViewById(R.id.javaProgramming).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Java Programming");
            startActivity(courseIntent);
        });
        findViewById(R.id.pythonProgramming).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Python Programming");
            startActivity(courseIntent);
        });

        findViewById(R.id.dataStructures).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Data Structures");
            startActivity(courseIntent);
        });

        findViewById(R.id.appDevelopment).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Android App Development");
            startActivity(courseIntent);
        });
        findViewById(R.id.webDevelopment).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Web Development");
            startActivity(courseIntent);
        });
        findViewById(R.id.nurtureProgrammingCourse).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Nurture Course");
            startActivity(courseIntent);
        });
        findViewById(R.id.advancedCourse).setOnClickListener(v -> {
            Intent courseIntent = new Intent(getApplicationContext(), SkillCourseDetailPageActivity.class);
            courseIntent.putExtra("courseName", "Advanced Course");
            startActivity(courseIntent);
        });

    }
}