package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cms.app.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnUserApproval = findViewById(R.id.btn_admin_user_approval);
        Button btnUsersearch = findViewById(R.id.btn_admin_main_activity_usersearch);
        btnUserApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UserApprovalListActivity.class);
                startActivity(intent);
            }
        });

        btnUsersearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminSearchActivity.class);
                startActivity(intent);

            }
        });
    }
}
