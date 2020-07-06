package cms.app.cms.student.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.R;
import cms.app.cms.model.CourseInfo;
import cms.app.cms.student.model.AllElectiveCourseAdapter;
import cms.app.cms.student.model.StudentCourseAdapter;
import cms.app.net.CMSClient;
import cms.app.net.QueryStudentAllCoursesResult;

public class SelectCourseActivity extends AppCompatActivity {

    ListView listView;
    EditText editTextsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listview_select_course);
        TextView btnSelectCourse = findViewById(R.id.btn_selectcourse_search);
        editTextsearch = findViewById(R.id.edit_search_class);

        editTextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = editTextsearch.getText().toString();
                SearchRecv(tmp);
            }
        });

        btnSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = editTextsearch.getText().toString();
                if (tmp.isEmpty()) {
                    editTextsearch.setHint("搜索不能为空");
                    return;
                } else {
                    SearchRecv(tmp);
                }

            }
        });

    }

    public void SearchRecv(String searchname) {
        String retMsg = "";
        if (searchname.isEmpty()) {
            retMsg = CMSClient.sendAndReceive("queryallelectivecourse");
            editTextsearch.setHint("搜索不能为空");
        } else {
            retMsg = CMSClient.sendAndReceive("searchevent\n" + searchname);
        }

        Gson gson = new Gson();
        final QueryStudentAllCoursesResult result = gson.fromJson(retMsg, QueryStudentAllCoursesResult.class);
        ArrayList<CourseInfo> courses = new ArrayList<>();
        for (CourseInfo c : result.getCourses()) {
            courses.add(c);
        }
        AllElectiveCourseAdapter adp = new AllElectiveCourseAdapter(SelectCourseActivity.this, R.layout.listview_item_select_course, courses);
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
                //int type=courseInfo.getType();
                float credit = courseInfo.getCredit();
                String book = courseInfo.getBook();
                //String teacherid=courseInfo.getTeacherId();
                String teachername = courseInfo.getTeacherName();
                String classroom = courseInfo.getClassroom();
                String time = courseInfo.getTime();
                Intent intent = new Intent(SelectCourseActivity.this, SelectCourseDetialActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("name", name);
                //intent.putExtra("type",type);
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

    @Override
    protected void onStart() {
        super.onStart();
        String retMsg = CMSClient.sendAndReceive("queryallelectivecourse");
        Gson gson = new Gson();
        final QueryStudentAllCoursesResult result = gson.fromJson(retMsg, QueryStudentAllCoursesResult.class);
        ArrayList<CourseInfo> courses = new ArrayList<>();
        for (CourseInfo c : result.getCourses()) {
            courses.add(c);
        }
        AllElectiveCourseAdapter adp = new AllElectiveCourseAdapter(SelectCourseActivity.this, R.layout.listview_item_select_course, courses);
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
                //int type=courseInfo.getType();
                float credit = courseInfo.getCredit();
                String book = courseInfo.getBook();
                //String teacherid=courseInfo.getTeacherId();
                String teachername = courseInfo.getTeacherName();
                String classroom = courseInfo.getClassroom();
                String time = courseInfo.getTime();
                Intent intent = new Intent(SelectCourseActivity.this, SelectCourseDetialActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("name", name);
                //intent.putExtra("type",type);
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
