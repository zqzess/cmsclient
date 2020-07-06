package cms.app.cms.student.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cms.app.R;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getSupportActionBar().hide();
        Button btnMyCourses = findViewById(R.id.btn_student_mycourses);
        Button btnMyTable = findViewById(R.id.btn_student_mytable);
        Button btnselectcourse = findViewById(R.id.btn_selectcourse);
        btnMyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, StudentCourseActivity.class);
                startActivity(intent);
            }
        });

        btnMyTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, ClassTable.class);
                startActivity(intent);
            }
        });

        btnselectcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, SelectCourseActivity.class);
                startActivity(intent);
            }
        });
    }
}
