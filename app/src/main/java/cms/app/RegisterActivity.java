package cms.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hb.dialog.dialog.ConfirmDialog;

import cms.app.cms.model.SelectClassActivity;
import cms.app.cms.model.Util;
import cms.app.net.CMSClient;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton radioButtonStudent;
    RadioButton radioButtonTeacher;
    Button btnRegister;
    Button btnChoose;
    View layoutClass;
    TextView textViewClass;
    TextView viewPwdcheck2;
    TextView viewPwdcheckfalse2;
    TextView viewtitle;
    EditText editTextUsername;
    EditText editTextUserId;
    EditText editTextPhone;
    EditText editTextPwd;
    EditText editTextVerifyPwd;
    String returnedData = null;
    int type = -1;
    String data = "";//决定这个页面是注册还是管理员创建用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        data = intent.getStringExtra("ActivityCode");

        radioButtonStudent = findViewById(R.id.radioButton_register_Student);
        radioButtonTeacher = findViewById(R.id.radioButton_register_Teacher);
        btnRegister = findViewById(R.id.btn_reg_register);
        btnChoose = findViewById(R.id.btn_register_Choose);
        layoutClass = findViewById(R.id.layout_reg_class);

        textViewClass = findViewById(R.id.textView_register_Class);
        viewPwdcheck2 = findViewById(R.id.view_regist_pwd_check2);
        viewPwdcheckfalse2 = findViewById(R.id.view_regist_pwd_checkflase2);
        viewtitle = findViewById(R.id.register_title);

        if (data.equals("AdminCreateUser")) {
            viewtitle.setText("创建用户");
            btnRegister.setText("创建");
        }

        editTextUsername = findViewById(R.id.edit_register_UserName);
        editTextUserId = findViewById(R.id.edit_register_UserId);
        editTextPhone = findViewById(R.id.edit_register_Phone);
        editTextPwd = findViewById(R.id.edit_register_Pwd);
        editTextVerifyPwd = findViewById(R.id.edit_register_VerifyPwd);

        RadioGroup radioGroupType = findViewById(R.id.radioGroup_register_Type);

        btnChoose.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        radioButtonStudent.setOnClickListener(this);
        radioButtonTeacher.setOnClickListener(this);
        //final EditText editTextClass=findViewById(R.id.edit_register_Class);

        //单选框与班级栏动态显示
        /*radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.radioButton_register_Student:
                        //editTextClass.setEnabled(true);
                        btnChoose.setEnabled(true);
                        break;
                    case R.id.radioButton_register_Teacher:
                        //editTextClass.setEnabled(false);
                        btnChoose.setEnabled(false);
                        break;
                }
            }
        });*/

        editTextVerifyPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd1 = editTextPwd.getText().toString();
                String pwd2 = editTextVerifyPwd.getText().toString();
                if (pwd2.isEmpty()) {
                    viewPwdcheckfalse2.setVisibility(View.GONE);
                    viewPwdcheck2.setVisibility(View.GONE);
                }
                if (pwd2.equals(pwd1)) {
                    viewPwdcheckfalse2.setVisibility(View.GONE);
                    viewPwdcheck2.setVisibility(View.VISIBLE);
                } else {
                    viewPwdcheck2.setVisibility(View.GONE);
                    viewPwdcheckfalse2.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //String pwd1=editTextPwd.getText().toString();
                String pwd2 = editTextVerifyPwd.getText().toString();
                if (pwd2.isEmpty()) {
                    viewPwdcheckfalse2.setVisibility(View.GONE);
                    viewPwdcheck2.setVisibility(View.GONE);
                }

            }
        });

    }

    //接收传回的值
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_Choose:
                Log.d("cmsdebug", "user click class choose button");//调试，打印日志
                Intent intent = new Intent(this, SelectClassActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_reg_register:
                Log.d("cmsdebug", "user click register button");
                if (data.equals("Register")) {
                    registerUser();
                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(RegisterActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认创建该用户？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            createrUser();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();
                }

                break;
            case R.id.radioButton_register_Student:
                //editTextClass.setEnabled(true);
                Log.d("cmsdebug", "user click radioButtonStudent");
                //btnChoose.setEnabled(true);
                layoutClass.setVisibility(View.VISIBLE);
                type = 0;
                break;
            case R.id.radioButton_register_Teacher:
                //editTextClass.setEnabled(false);
                Log.d("cmsdebug", "user click radioButtonTeacher");
                //btnChoose.setEnabled(false);
                layoutClass.setVisibility(View.GONE);
                type = 1;
                break;
            default:
                break;
        }
    }


    private void registerUser() {
        if (type == -1) {
            Util.showErrorMsgDialog(this, "未选择用户类型");
            return;
        }
        String userId = editTextUserId.getText().toString();
        if (userId.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入学号或工号");
            return;
        }
        String username = editTextUsername.getText().toString();
        if (username.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入姓名");
            return;
        }
        String phone = editTextPhone.getText().toString();
        if (phone.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入手机号码");
            return;
        }
        if (type == 0) {
            if (returnedData == null) {
                Util.showErrorMsgDialog(this, "请选择班级");
                return;
            }
        }
        String Pwd = editTextPwd.getText().toString();
        String VerifyPwd = editTextVerifyPwd.getText().toString();
        if (Pwd.isEmpty()) {
            Util.showErrorMsgDialog(this, "请输入密码");
            return;
        } else if (VerifyPwd.isEmpty()) {
            Util.showErrorMsgDialog(this, "请确认密码");
            return;
        } else {
            if (!Pwd.equals(VerifyPwd)) {
                Util.showErrorMsgDialog(this, "两次密码输入不一致");
                return;
            }
        }

        String sendMsg = "register_user\n" + type + "\n" + userId + "\n" + username + "\n" + phone + "\n" + returnedData + "\n" + Pwd;
        String retMsg = CMSClient.sendAndReceive(sendMsg);

        String[] arr = retMsg.split("\n");
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        if (arr[0].equals("Y")) {
            dlg.setMessage(arr[1]);

            dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
        } else if (arr[0].equals("N")) {
            dlg.setTitle("错误");
            dlg.setMessage(arr[1]);
        }
        dlg.show();
    }

    private void createrUser() {
        if (type == -1) {
            Util.showErrorMsgDialog(this, "未选择用户类型");
            return;
        }
        String userId = editTextUserId.getText().toString();
        if (userId.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入学号或工号");
            return;
        }
        String username = editTextUsername.getText().toString();
        if (username.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入姓名");
            return;
        }
        String phone = editTextPhone.getText().toString();
        if (phone.isEmpty()) {
            Util.showErrorMsgDialog(this, "未输入手机号码");
            return;
        }
        if (type == 0) {
            if (returnedData == null) {
                Util.showErrorMsgDialog(this, "请选择班级");
                return;
            }
        }
        String Pwd = editTextPwd.getText().toString();
        String VerifyPwd = editTextVerifyPwd.getText().toString();
        if (Pwd.isEmpty()) {
            Util.showErrorMsgDialog(this, "请输入密码");
            return;
        } else if (VerifyPwd.isEmpty()) {
            Util.showErrorMsgDialog(this, "请确认密码");
            return;
        } else {
            if (!Pwd.equals(VerifyPwd)) {
                Util.showErrorMsgDialog(this, "两次密码输入不一致");
                return;
            }
        }

        String sendMsg = "register_user\n" + type + "\n" + userId + "\n" + username + "\n" + phone + "\n" + returnedData + "\n" + Pwd;
        String retMsg = CMSClient.sendAndReceive(sendMsg);
        String[] arr = retMsg.split("\n");
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        if (arr[0].equals("Y")) {
            dlg.setMessage("创建成功，该用户待审核");

            dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
        } else if (arr[0].equals("N")) {
            dlg.setTitle("错误");
            dlg.setMessage(arr[1]);
        }
        dlg.show();
    }
}
