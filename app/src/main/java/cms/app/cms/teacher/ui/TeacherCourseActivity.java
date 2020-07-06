package cms.app.cms.teacher.ui;
/**
 * @author ZQZESS
 * @date 2020/6/17-00:15
 * GitHub：
 * email：
 * description：
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.R;
import cms.app.cms.model.CourseInfo;
import cms.app.cms.model.GlobalConfig;
import cms.app.cms.student.model.StudentCourseAdapter;
import cms.app.cms.student.ui.CourseDetailActivity;
import cms.app.cms.student.ui.StudentCourseActivity;
import cms.app.cms.teacher.model.TeacherCourseAdapter;
import cms.app.net.CMSClient;
import cms.app.net.QueryStudentAllCoursesResult;

public class TeacherCourseActivity extends AppCompatActivity {
    ListView listView;
    Button createCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview_teacher_course);
        createCourse = findViewById(R.id.btn_teachercourse_createclass);

        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherCourseActivity.this, CreateCourseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String retMsg = CMSClient.sendAndReceive("query_teacher_all_courses\n" + GlobalConfig.currentUser);
        Gson gson = new Gson();
        final QueryStudentAllCoursesResult result = gson.fromJson(retMsg, QueryStudentAllCoursesResult.class);
        ArrayList<CourseInfo> courses = new ArrayList<>();
        for (CourseInfo c : result.getCourses()) {
            courses.add(c);
        }
        TeacherCourseAdapter adp = new TeacherCourseAdapter(TeacherCourseActivity.this, R.layout.list_item_teacher_couorse, courses);
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
                String teachername = courseInfo.getTeacherName();
                String classroom = courseInfo.getClassroom();
                String time = courseInfo.getTime();
                Intent intent = new Intent(TeacherCourseActivity.this, TeacherCourseDetailActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("credit", credit);
                intent.putExtra("book", book);
                intent.putExtra("teachername", teachername);
                intent.putExtra("classroom", classroom);
                intent.putExtra("time", time);
                startActivity(intent);

            }
        });
    }
}
