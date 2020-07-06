package cms.app.cms.teacher.ui;
/**
 * @author ZQZESS
 * @date 2020/6/17-18:47
 * GitHub：
 * email：
 * description：
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.R;
import cms.app.cms.teacher.model.UserAdapter;
import cms.app.cms.teacher.model.UserSummary;
import cms.app.net.CMSClient;
import cms.app.net.QueryWaitingApprovalResult;
import cms.app.net.User;

public class WhoChooseCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whochoosecourse);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String id = intent.getStringExtra("cid");

        ListView listView = findViewById(R.id.listview_whochoosecourse);
        String retMsg = CMSClient.sendAndReceive("queryStudentChoose\n" + id);
        Gson gson = new Gson();
        final QueryWaitingApprovalResult retObj = gson.fromJson(retMsg, QueryWaitingApprovalResult.class);

        ArrayList<UserSummary> listApproval = new ArrayList<>();
        if (retObj.isSuccess()) {
            for (User u : retObj.getUsers()) {
                listApproval.add(new UserSummary(u.getUernameString(), u.getName()));
            }
        }
        UserAdapter adp = new UserAdapter(this, R.layout.list_item_whochoosecourse, listApproval);
        listView.setAdapter(adp);
    }
}
