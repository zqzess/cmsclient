package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.R;
import cms.app.cms.admin.model.ApprovalAdapter;
import cms.app.cms.admin.model.UserApprovalSummary;
import cms.app.net.CMSClient;
import cms.app.net.QueryWaitingApprovalResult;
import cms.app.net.User;

public class UserApprovalListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_user_approval_list);
        getSupportActionBar().hide();


        listView = findViewById(R.id.listview_user_approval);


    }

    @Override
    protected void onStart() {
        super.onStart();

        String retMsg = CMSClient.sendAndReceive("queryApprovalUsers");
        Gson gson = new Gson();
        final QueryWaitingApprovalResult retObj = gson.fromJson(retMsg, QueryWaitingApprovalResult.class);

        ArrayList<UserApprovalSummary> listApproval = new ArrayList<>();
        if (retObj.isSuccess()) {
            for (User u : retObj.getUsers()) {
                listApproval.add(new UserApprovalSummary(u.getType(), u.getUernameString(), u.getName()));
            }
        }
        //listApproval.add(new UserApprovalSummary(0,"1833001","张三"));
        //listApproval.add(new UserApprovalSummary(0,"1833002","李二"));
        ApprovalAdapter adp = new ApprovalAdapter(this, R.layout.listview_item_user_approval, listApproval);
        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<User> users = retObj.getUsers();
                User u = users.get(position);
                int type = u.getType();
                String userid = u.getUernameString();
                String username = u.getName();
                String phone = u.getPhone();
                String classname = u.getClassname();
                Intent intent = new Intent(UserApprovalListActivity.this, UserApprovalDetailActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("userid", userid);
                intent.putExtra("username", username);
                intent.putExtra("phone", phone);
                intent.putExtra("class", classname);
                startActivity(intent);
            }
        });
    }
}
