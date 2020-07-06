package cms.app.cms.admin.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hb.dialog.dialog.ConfirmDialog;

import cms.app.R;
import cms.app.cms.model.GlobalConfig;
import cms.app.cms.model.SelectClassActivity;
import cms.app.cms.model.Util;
import cms.app.net.CMSClient;

/**
 * @author ZQZESS
 * @date 2020/6/25-1:40
 * GitHub：
 * email：
 * description：
 */

public class AdminSearchDetailActivity extends AppCompatActivity {
    TextView textViewType;
    TextView textViewUserid;
    EditText textViewUsername;
    EditText textViewPhone;
    TextView textViewClass;
    TextView textViewState;
    TextView viewEdit;
    TextView viewDelete;
    EditText textViewPassword;

    LinearLayout layoutclass;
    LinearLayout layoutType1;
    LinearLayout layoutType2;
    LinearLayout layoutclassbutton;
    LinearLayout layoutState1;
    LinearLayout layoutState2;

    RadioGroup typeGroup;
    RadioGroup stateGroup;

    RadioButton radioButtonStudent;
    RadioButton radioButtonTeacher;
    RadioButton radioButtonState0;
    RadioButton radioButtonState1;
    RadioButton radioButtonState2;

    Button btnClassChoose;

    int type;//身份类型
    int typetmp;//临时变量,保存原始身份类型
    int state;//状态
    int radiotype = -1;
    int radiostate = -1;
    String returnedData = null;//班级选择返回值
    String userid;//用户id

    Util u = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_search_detail);

        textViewType = findViewById(R.id.textview_admin_search_detail_type);
        textViewUserid = findViewById(R.id.textview_admin_search_detail_userid);
        textViewUsername = findViewById(R.id.textview_admin_search_detail_username);
        textViewPhone = findViewById(R.id.textview_admin_search_detail_phone);
        textViewClass = findViewById(R.id.textview_admin_search_detail_class);
        textViewPassword = findViewById(R.id.textview_admin_search_detail_pwd);
        textViewState = findViewById(R.id.textview_admin_search_detail_State);
        viewEdit = findViewById(R.id.view_admin_search_detail_edit);
        viewDelete = findViewById(R.id.view_admin_search_detail_drop);

        layoutclass = findViewById(R.id.layout_admin_search_detail_Class);
        layoutType1 = findViewById(R.id.layout_admin_search_detail_type1);
        layoutType2 = findViewById(R.id.layout_admin_search_detail_type2);

        layoutclassbutton = findViewById(R.id.layout_admin_search_detail_chooseclass);
        layoutState1 = findViewById(R.id.layout_admin_search_detail_State1);
        layoutState2 = findViewById(R.id.layout_admin_search_detail_State2);

        typeGroup = findViewById(R.id.radioGroup_admin_search_detail_typegroup);
        stateGroup = findViewById(R.id.radioGroup_admin_search_detail_stategroup);

        radioButtonStudent = findViewById(R.id.radioButton_admin_search_detail_type0);
        radioButtonTeacher = findViewById(R.id.radioButton_admin_search_detail_type1);
        radioButtonState0 = findViewById(R.id.radioButton_admin_search_detail_state0);
        radioButtonState1 = findViewById(R.id.radioButton_admin_search_detail_state1);
        radioButtonState2 = findViewById(R.id.radioButton_admin_search_detail_state2);
        //layoutType2.setVisibility(View.GONE);

        btnClassChoose = findViewById(R.id.btn_admin_search_detail_chooseclass);

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        typetmp = type;
        userid = intent.getStringExtra("userid");
        String username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        String classname = intent.getStringExtra("class");
        String password = intent.getStringExtra("password");
        state = intent.getIntExtra("state", 0);

        if (type == 0) {
            textViewType.setText("学生");
        } else if (type == 1) {
            textViewType.setText("教师");
            layoutclass.setVisibility(View.GONE);
        }
        textViewUserid.setText(userid);
        textViewUsername.setText(username);
        textViewPhone.setText(phone);
        textViewClass.setText(classname);
        textViewPassword.setText(password);
        if (state == 0) {
            textViewState.setText("未审核");
        } else if (state == 1) {
            textViewState.setText("审核通过");
        } else if (state == 2) {
            textViewState.setText("审核不通过");
        }

        editeDisable();

        viewEdit.setOnClickListener(new View.OnClickListener() {
            //编辑按钮
            @Override
            public void onClick(View v) {
                String editTmp = viewEdit.getText().toString();
                if (editTmp.equals("编辑")) {//编辑
                    viewEdit.setText("保存");
                    editEnable();
                    if (type == 0) {//类型学生
                        radioButtonStudent.setChecked(true);
                        radiotype = type;
                    } else {//类型学生
                        radioButtonTeacher.setChecked(true);
                        radiotype = type;
                    }
                    if (state == 0) {//未审核
                        radioButtonState0.setChecked(true);
                        radiostate = state;
                    } else if (state == 1) {//通过审核
                        radioButtonState1.setChecked(true);
                        radiostate = state;
                    } else if (state == 2) {//未通过审核
                        radioButtonState1.setChecked(true);
                        radiostate = state;
                    }

                } else {//保存,更新数据库

                    //弹窗确认
                    ConfirmDialog confirmDialog = new ConfirmDialog(AdminSearchDetailActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认更新此用户信息？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            viewEdit.setText("编辑");
                            editeDisable();
                            GetAndSendEvent();
                            finish();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();

                }
            }
        });

        viewDelete.setOnClickListener(new View.OnClickListener() {
            //删除按钮
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(AdminSearchDetailActivity.this);
                    dlg.setTitle("警告");
                    dlg.setMessage("教师账号无法删除");
                    dlg.show();
                    return;
                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(AdminSearchDetailActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认删除此用户信息？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            AdminDeleteUser();
                            finish();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();
                }

            }
        });

        btnClassChoose.setOnClickListener(new View.OnClickListener() {
            //班级选择按钮
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSearchDetailActivity.this, SelectClassActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_admin_search_detail_type0:
                        radiotype = 0;
                        break;
                    case R.id.radioButton_admin_search_detail_type1:
                        radiotype = 1;
                        break;

                }
            }
        });

        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton_admin_search_detail_state0:
                        radiostate = 0;
                        break;
                    case R.id.radioButton_admin_search_detail_state1:
                        radiostate = 1;
                        break;
                    case R.id.radioButton_admin_search_detail_state2:
                        radiostate = 2;
                        break;
                }
            }
        });
    }//onCreate()结束

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnedData = data.getStringExtra("name");
                    textViewClass.setText(returnedData);
                    Log.d("name", returnedData);
                }
                break;
            default:
        }
    }

    private void editeDisable() {
        /*textViewUserid.setFocusable(false);
        textViewUserid.setFocusableInTouchMode(false);
        textViewUserid.setBackgroundResource(R.drawable.bg_null);*/

        //设置文本框不可编辑
        textViewUsername.setFocusable(false);
        textViewUsername.setFocusableInTouchMode(false);
        textViewUsername.setBackgroundResource(R.drawable.bg_null);//设置背景效果无

        textViewPhone.setFocusable(false);
        textViewPhone.setFocusableInTouchMode(false);
        textViewPhone.setBackgroundResource(R.drawable.bg_null);

        textViewPassword.setFocusable(false);
        textViewPassword.setFocusableInTouchMode(false);
        textViewPassword.setBackgroundResource(R.drawable.bg_null);

        //layoutType1.setVisibility(View.VISIBLE);
        //layoutType2.setVisibility(View.GONE);

        layoutState1.setVisibility(View.VISIBLE);
        layoutState2.setVisibility(View.GONE);

        layoutclassbutton.setVisibility(View.GONE);

    }

    private void editEnable() {
        /*textViewUserid.setFocusableInTouchMode(true);
        textViewUserid.setFocusable(true);
        //textViewUserid.requestFocus();
        textViewUserid.setBackgroundResource(R.drawable.bg_edittext);*/

        textViewUsername.setFocusableInTouchMode(true);
        textViewUsername.setFocusable(true);
        //textViewUsername.requestFocus();
        textViewUsername.setBackgroundResource(R.drawable.bg_edittext);

        textViewPhone.setFocusableInTouchMode(true);
        textViewPhone.setFocusable(true);
        //textViewPhone.requestFocus();
        textViewPhone.setBackgroundResource(R.drawable.bg_edittext);

        textViewPassword.setFocusable(true);
        textViewPassword.setFocusableInTouchMode(true);
        textViewPassword.setBackgroundResource(R.drawable.bg_edittext);

        //layoutType1.setVisibility(View.GONE);
        //layoutType2.setVisibility(View.VISIBLE);

        layoutState1.setVisibility(View.GONE);
        layoutState2.setVisibility(View.VISIBLE);

        layoutclassbutton.setVisibility(View.VISIBLE);
    }

    public void GetAndSendEvent() {
        String username = textViewUsername.getText().toString();
        String phone = textViewPhone.getText().toString();
        String classname = textViewClass.getText().toString();
        String password = textViewPassword.getText().toString();
        if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            u.showErrorMsgDialog(AdminSearchDetailActivity.this, "请把信息输入完整");
            return;
        } else {
            if (typetmp == 0)//学生,有班级
            {
                if (classname.isEmpty()) {
                    u.showErrorMsgDialog(AdminSearchDetailActivity.this, "请先选择班级");
                    return;
                } else {
                    String sendMsg = "AdminupdateUserInfo\n" + userid + "\n" + radiotype + "\n" + username + "\n" + phone + "\n" + classname + "\n" + password + "\n" + radiostate;
                    String retMsg = CMSClient.sendAndReceive(sendMsg);
                }
            } else if (typetmp == 1)//教师,无班级
            {
                classname = "";
                String sendMsg = "AdminupdateUserInfo\n" + userid + "\n" + radiotype + "\n" + username + "\n" + phone + "\n" + classname + "\n" + password + "\n" + radiostate;
                String retMsg = CMSClient.sendAndReceive(sendMsg);

            }
        }

    }

    public void AdminDeleteUser() {
        String sedMsg = "AdminDeleteUser\n" + userid;
        String retMsg = CMSClient.sendAndReceive(sedMsg);
    }
}
