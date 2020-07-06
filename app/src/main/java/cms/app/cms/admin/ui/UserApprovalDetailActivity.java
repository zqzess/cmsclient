package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cms.app.R;
import cms.app.net.CMSClient;

public class UserApprovalDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_approval_detail);
        getSupportActionBar().hide();

        TextView textViewType = findViewById(R.id.list_detil_type);
        TextView textViewUserid = findViewById((R.id.list_detil_userid));
        TextView textViewUsername = findViewById(R.id.list_detil_username);
        TextView textViewPhone = findViewById(R.id.list_detil_phone);
        TextView textViewClass = findViewById(R.id.list_detil_class);
        LinearLayout layoutClass = findViewById(R.id.layoutClass);
        Button btnpass = findViewById(R.id.btn_detail_pass);
        Button btnnoppass = findViewById(R.id.btn_detail_notpass);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        final String userid = intent.getStringExtra("userid");
        String username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        String classname = intent.getStringExtra("class");


        if (type == 0) {
            textViewType.setText("学生");
        } else if (type == 1) {
            textViewType.setText("教师");
            layoutClass.setVisibility(View.GONE);
        }
        textViewUserid.setText(userid);
        textViewUsername.setText(username);
        textViewPhone.setText(phone);
        textViewClass.setText(classname);

        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String retMsg = CMSClient.sendAndReceive("userApproval\n" + userid + "\n1");
                finish();
            }
        });

        btnnoppass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String retMsg = CMSClient.sendAndReceive("userApproval\n" + userid + "\n0");
                finish();
            }
        });

    }
}
