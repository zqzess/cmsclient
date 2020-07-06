package cms.app.cms.student.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.dialog.dialog.ConfirmDialog;

import cms.app.R;
import cms.app.cms.model.GlobalConfig;
import cms.app.cms.student.model.DayChange;
import cms.app.net.CMSClient;

public class SelectCourseDetialActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcousedetial);
        getSupportActionBar().hide();

        TextView textViewId = findViewById(R.id.textview_selectcourse_id);
        TextView textViewName = findViewById(R.id.textview_selectcourse_name);
        //TextView textViewType = findViewById(R.id.textview_course_type);
        TextView textViewCredit = findViewById(R.id.textview_selectcourse_credit);
        TextView textViewBook = findViewById(R.id.textview_selectcourse_book);
        //TextView textViewTeacherid=findViewById(R.id.textview_course_teacher);
        TextView textViewTeachername = findViewById(R.id.textview_selectcourse_teacher);
        TextView textViewClassroom = findViewById(R.id.textview_selectcourse_classroom);
        TextView textViewTime = findViewById(R.id.textview_selectcourse_time);
        Button btnselectChoose = findViewById(R.id.btn_coursechoose);

        Intent intent = getIntent();
        id = intent.getStringExtra("cid");
        String name = intent.getStringExtra("name");
        //int type = intent.getIntExtra("type", 0);
        float credit = intent.getFloatExtra("credit", 0);
        String book = intent.getStringExtra("book");
        String teachername = intent.getStringExtra("teachername");
        String classroom = intent.getStringExtra("classroom");
        String timetmp = intent.getStringExtra("time");

        String[] arr = timetmp.split(",");
        DayChange a = new DayChange();
        int length = arr.length;
        String time = a.daychange(length, arr);
        /*if(length==6)
        {
            String weekday=arr[0];
            String weekday2=DayChange(weekday);
            time=weekday2+arr[1]+"-"+arr[2];
            weekday=arr[3];
            weekday2=DayChange(weekday);
            time=time+weekday2+arr[4]+"-"+arr[5];

        }else if(length==3)
        {
            String weekday=arr[0];
            String weekday2=DayChange(weekday);
            time=weekday2+arr[1]+"-"+arr[2];
        }*/


        textViewId.setText(id);
        textViewName.setText(name);
        String tmp = Float.toString(credit);
        textViewCredit.setText(tmp);
        textViewBook.setText(book);
        textViewTeachername.setText(teachername);
        textViewClassroom.setText(classroom);
        textViewTime.setText(time);

        btnselectChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(SelectCourseDetialActivity.this);
                String retMsg = CMSClient.sendAndReceive("selectcourse\n" + id + "\n" + GlobalConfig.currentUser);
                if (retMsg.equals("N")) {
                    myAlertDialog.setTitle("请勿重复选课！");
                    myAlertDialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    myAlertDialog.setTitle("选课成功！");
                    myAlertDialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }
                myAlertDialog.show();
            }
        });
    }

}
