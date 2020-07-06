package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.dialog.myDialog.MyAlertInputDialog;

import cms.app.R;
import cms.app.net.CMSClient;

public class AdminManagerClassDetailActivity extends AppCompatActivity {
    EditText editeClassName;
    TextView textViewCount;
    TextView textViewCourse;
    Button btneditClassname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager_class_detail);
        getSupportActionBar().hide();
        editeClassName = findViewById(R.id.edit_admin_manager_class_classname);
        textViewCount = findViewById(R.id.textview_admin_manager_class_count);
        textViewCourse = findViewById(R.id.textview_admin_manager_class_course);

        editeClassName.setFocusable(false);
        editeClassName.setFocusableInTouchMode(false);
        editeClassName.setBackgroundResource(R.drawable.bg_null);

        btneditClassname = findViewById(R.id.btn_admin_manager_class_detail_editclassname);

        /*editeClassName.setFocusableInTouchMode(true);
        editeClassName.setFocusable(true);
        editeClassName.setBackgroundResource(R.drawable.bg_edittext);*/


        Intent intent = getIntent();
        String classname = intent.getStringExtra("classname");
        editeClassName.setText(classname);

        String retMsg = CMSClient.sendAndReceive("selecClassInfo\n" + classname);
        if (retMsg.isEmpty()) {
            textViewCount.setText("暂无信息");
            textViewCourse.setText("暂无信息");
        } else {
            String[] arrTmp = retMsg.split("-");
            textViewCount.setText(arrTmp[0]);
            textViewCourse.setText(arrTmp[1]);
        }


        btneditClassname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(AdminManagerClassDetailActivity.this).builder()
                        .setTitle("请输入")
                        .setEditText("请输入修改后的班级名称");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String classnameTmp = myAlertInputDialog.getResult();
                        Log.d("classnameTmp:", classnameTmp);
                        CMSClient.sendAndReceive("updateClassName\n" + classname + "\n" + classnameTmp);
                        myAlertInputDialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //return;
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();
            }

        });
    }
}
