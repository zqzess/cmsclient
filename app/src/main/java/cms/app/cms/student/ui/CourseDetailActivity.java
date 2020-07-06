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
import cms.app.cms.model.VerifyCode;
import cms.app.cms.model.VerifyCodeDialog;
import cms.app.cms.student.model.DayChange;
import cms.app.net.CMSClient;

public class CourseDetailActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetail);
        getSupportActionBar().hide();

        TextView textViewId = findViewById(R.id.textview_course_id);
        TextView textViewName = findViewById(R.id.textview_course_name);
        TextView textViewType = findViewById(R.id.textview_course_type);
        TextView textViewCredit = findViewById(R.id.textview_course_credit);
        TextView textViewBook = findViewById(R.id.textview_course_book);
        //TextView textViewTeacherid=findViewById(R.id.textview_course_teacher);
        TextView textViewTeachername = findViewById(R.id.textview_course_teacher);
        TextView textViewClassroom = findViewById(R.id.textview_course_classroom);
        TextView textViewTime = findViewById(R.id.textview_course_time);
        LinearLayout layoutcourse = findViewById(R.id.layoutcourse);
        Button btnCanelChoose = findViewById(R.id.btn_canel_choose);

        Intent intent = getIntent();
        id = intent.getStringExtra("cid");
        String name = intent.getStringExtra("name");
        int type = intent.getIntExtra("type", 0);
        float credit = intent.getFloatExtra("credit", 0);
        String book = intent.getStringExtra("book");
        String teachername = intent.getStringExtra("teachername");
        String classroom = intent.getStringExtra("classroom");
        String timetmp = intent.getStringExtra("time");

        String[] arr = timetmp.split(",");//数字日期以逗号分割
        DayChange a = new DayChange();
        int length = arr.length;
        String time = a.daychange(length, arr);

        if (type == 0) {
            textViewType.setText("必修");
            layoutcourse.setVisibility(View.GONE);
        } else if (type == 1) {
            textViewType.setText("选修");

        }

        textViewId.setText(id);
        textViewName.setText(name);
        String tmp = Float.toString(credit);
        textViewCredit.setText(tmp);
        textViewBook.setText(book);
        textViewTeachername.setText(teachername);
        textViewClassroom.setText(classroom);
        textViewTime.setText(time);

        btnCanelChoose.setOnClickListener(new View.OnClickListener() {
            //int tmp;
            @Override
            public void onClick(View v) {
                int flagtmp = GlobalConfig.flag;
                if (flagtmp > 2) {
                    VerifyCodeDialog dlg = new VerifyCodeDialog();
                    dlg.Dialog(CourseDetailActivity.this);
                    //GlobalConfig.flag=0;
                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(CourseDetailActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认取消此选修课？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            String retMsg = CMSClient.sendAndReceive("cancelClass\n" + GlobalConfig.currentUser + "\n" + id);
                            int flagtmp = GlobalConfig.flag;
                            flagtmp = flagtmp + 1;
                            GlobalConfig.flag = flagtmp;
                            finish();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();
                }
                /*AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CourseDetailActivity.this);
                    myAlertDialog.setTitle("确认取消此选修课吗？");
                    myAlertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    myAlertDialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String retMsg= CMSClient.sendAndReceive("cancelClass\n"+ GlobalConfig.currentUser+"\n"+id);
                            finish();
                        }
                    });
                    myAlertDialog.show();*/


                //String retMsg= CMSClient.sendAndReceive("cancelClass\n"+ GlobalConfig.currentUser+"\n"+id);
                //finish();
            }
        });
    }
}
