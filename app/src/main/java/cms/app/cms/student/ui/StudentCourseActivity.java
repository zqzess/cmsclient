package cms.app.cms.student.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.cms.model.CourseInfo;
import cms.app.cms.model.GlobalConfig;
import cms.app.R;
import cms.app.cms.student.model.StudentCourseAdapter;
import cms.app.net.CMSClient;
import cms.app.net.QueryStudentAllCoursesResult;

public class StudentCourseActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview_student_course);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String retMsg = CMSClient.sendAndReceive("query_student_all_courses\n" + GlobalConfig.currentUser);
        Gson gson = new Gson();
        final QueryStudentAllCoursesResult result = gson.fromJson(retMsg, QueryStudentAllCoursesResult.class);
        ArrayList<CourseInfo> courses = new ArrayList<>();
        for (CourseInfo c : result.getCourses()) {
            courses.add(c);
        }
        StudentCourseAdapter adp = new StudentCourseAdapter(StudentCourseActivity.this, R.layout.listview_item_student_course, courses);

        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<CourseInfo> courses = new ArrayList<>();
                for (CourseInfo c : result.getCourses()) {
                    courses.add(c);
                }
                CourseInfo courseInfo = courses.get(position);
                String cid = courseInfo.getId();
                String name = courseInfo.getName();
                int type = courseInfo.getType();
                float credit = courseInfo.getCredit();
                String book = courseInfo.getBook();
                //String teacherid=courseInfo.getTeacherId();
                String teachername = courseInfo.getTeacherName();
                String classroom = courseInfo.getClassroom();
                String time = courseInfo.getTime();
                Intent intent = new Intent(StudentCourseActivity.this, CourseDetailActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("credit", credit);
                intent.putExtra("book", book);
                //intent.putExtra("teacherid",teacherid);
                intent.putExtra("teachername", teachername);
                intent.putExtra("classroom", classroom);
                intent.putExtra("time", time);
                startActivity(intent);

            }
        });
    }
}
