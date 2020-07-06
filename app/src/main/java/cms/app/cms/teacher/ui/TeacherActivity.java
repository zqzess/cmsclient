package cms.app.cms.teacher.ui;
/**
 * @author ZQZESS
 * @date 2020/6/15-14:47
 * GitHub：
 * email：
 * description：
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cms.app.R;

public class TeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Button btnteacherCourse = findViewById(R.id.btn_teacher_mycourse);

        btnteacherCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, TeacherCourseActivity.class);
                startActivity(intent);
            }
        });
    }

}
