package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cms.app.R;
import cms.app.RegisterActivity;
import cms.app.cms.admin.model.ApprovalAdapter;
import cms.app.cms.admin.model.UserApprovalSummary;
import cms.app.net.CMSClient;
import cms.app.net.QueryWaitingApprovalResult;
import cms.app.net.User;

/**
 * @author ZQZESS
 * @date 2020/6/24-15:40
 * GitHub：
 * email：
 * description：
 */

public class AdminSearchActivity extends AppCompatActivity {
    EditText searcheEditText;
    TextView viewDelete;
    ListView listView;
    TextView viewAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_search);

        searcheEditText = findViewById(R.id.edit_admin_search_searchedit);
        viewDelete = findViewById(R.id.view_admin_searh_delete);
        viewAdd = findViewById(R.id.view_admin_searh_add);
        listView = findViewById(R.id.listview_admin_search);
        viewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTmp = searcheEditText.getText().toString();
                if (editTmp.isEmpty()) {
                    searcheEditText.setText(editTmp);
                } else {
                    editTmp = editTmp.substring(0, editTmp.length() - 1);
                    searcheEditText.setText(editTmp);
                    searcheEditText.setSelection(editTmp.length());
                }
            }
        });

        searcheEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editTmp = searcheEditText.getText().toString();
                if (editTmp.equals("学生")) {
                    String sendMsg = "AdminquerySearchType\n0";
                    AdminSearchEvent(sendMsg);
                } else if (editTmp.equals("教师") || editTmp.equals("老师")) {
                    String sendMsg = "AdminquerySearchType\n1";
                    AdminSearchEvent(sendMsg);
                } else if (editTmp.isEmpty()) {
                    searcheEditText.setHint("请输入要查询的内容");
                    String sendMsg = "AdminqueryAllUsers";
                    AdminSearchEvent(sendMsg);
                } else {
                    String sendMsg = "AdminquerySearcrchEvent\n" + editTmp;
                    AdminSearchEvent(sendMsg);
                }
            }
        });

        viewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "AdminCreateUser";
                Intent intent = new Intent(AdminSearchActivity.this, RegisterActivity.class);
                intent.putExtra("ActivityCode", data);
                startActivity(intent);
            }
        });
    }//onCreate()结束

    @Override
    protected void onStart() {
        super.onStart();

        /*String sendMsg="AdminqueryAllUsers";
        //String sendMsg="queryApprovalUsers";
        AdminSearchEvent(sendMsg);*/

        String editTmp = searcheEditText.getText().toString();
        if (editTmp.equals("学生")) {
            String sendMsg = "AdminquerySearchType\n0";
            AdminSearchEvent(sendMsg);
        } else if (editTmp.equals("教师") || editTmp.equals(" 老师")) {
            String sendMsg = "AdminquerySearchType\n1";
            AdminSearchEvent(sendMsg);
        } else if (editTmp.isEmpty()) {
            searcheEditText.setHint("请输入要查询的内容");
            String sendMsg = "AdminqueryAllUsers";
            AdminSearchEvent(sendMsg);
        } else {
            String sendMsg = "AdminquerySearcrchEvent\n" + editTmp;
            AdminSearchEvent(sendMsg);
        }

    }

    public void AdminSearchEvent(String sendMsg) {
        String retMsg = CMSClient.sendAndReceive(sendMsg);
        Gson gson = new Gson();
        final QueryWaitingApprovalResult retObj = gson.fromJson(retMsg, QueryWaitingApprovalResult.class);
        ArrayList<UserApprovalSummary> listApproval = new ArrayList<>();
        if (retObj.isSuccess()) {
            for (User u : retObj.getUsers()) {
                listApproval.add(new UserApprovalSummary(u.getType(), u.getUernameString(), u.getName()));
            }
        }
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
                String password = u.getPassword();
                int state = u.getState();
                Intent intent = new Intent(AdminSearchActivity.this, AdminSearchDetailActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("userid", userid);
                intent.putExtra("username", username);
                intent.putExtra("phone", phone);
                intent.putExtra("class", classname);
                intent.putExtra("password", password);
                intent.putExtra("state", state);
                startActivity(intent);
            }
        });
    }
}
