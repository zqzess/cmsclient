package cms.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.hb.dialog.dialog.LoadingDialog;

import cms.app.cms.admin.ui.AdminActivity;
import cms.app.cms.admin.ui.AdminMain;
import cms.app.cms.model.GlobalConfig;
import cms.app.cms.model.Util;
import cms.app.cms.model.VerifyCodeDialog;
import cms.app.cms.student.ui.ClassTable;
import cms.app.cms.student.ui.CourseDetailActivity;
import cms.app.cms.student.ui.StudentActivity;
import cms.app.cms.teacher.ui.TeacherActivity;
import cms.app.cms.teacher.ui.TeacherCourseActivity;
import cms.app.net.CMSClient;

/*
* zqzess
* */
public class MainActivity extends AppCompatActivity {

    CheckBox checkBox;
    SharedPreferences sp = null;
    CheckBox checkBoxshowPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        TextView btnReg = findViewById(R.id.btn_login_Register);
        TextView btnLogin = findViewById(R.id.btn_login);
        TextView btnResetPwd = findViewById(R.id.btn_login_resetpwd);
        final EditText editTextUsername = findViewById(R.id.edit_login_username);
        final EditText editTextPwd = findViewById(R.id.edit_login_Pwd);
        checkBox = findViewById(R.id.checkBoxLogin);
        checkBoxshowPwd = findViewById(R.id.checkbox_showPassword);

        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("checkBoolean", false)) {
            editTextUsername.setText(sp.getString("userid", null));
            editTextUsername.setSelection(sp.getString("userid", null).length());
            editTextPwd.setText(sp.getString("pwd", null));
            editTextPwd.setSelection(sp.getString("pwd", null).length());
            checkBox.setChecked(true);
        }


        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = editTextUsername.getText().toString();
                String password = editTextPwd.getText().toString();
                boolean checkboxLogin = checkBox.isChecked();
                if (checkboxLogin) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", username);
                    editor.putString("pwd", password);
                    editor.putBoolean("checkBoolean", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", null);
                    editor.putString("pwd", null);
                    editor.putBoolean("checkBoolean", false);
                    editor.commit();
                }

            }
        });

        checkBoxshowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    //mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    /**
                     * 第二种
                     */
                    editTextPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editTextPwd.setSelection(editTextPwd.getText().length());
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    //mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    /**
                     * 第二种
                     */
                    editTextPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextPwd.setSelection(editTextPwd.getText().length());
                }
            }
        });
        editTextPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = editTextUsername.getText().toString();
                String password = editTextPwd.getText().toString();
                boolean checkboxLogin = checkBox.isChecked();
                if (checkboxLogin) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", username);
                    editor.putString("pwd", password);
                    editor.putBoolean("checkBoolean", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", null);
                    editor.putString("pwd", null);
                    editor.putBoolean("checkBoolean", false);
                    editor.commit();
                }

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Register";
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("ActivityCode", data);
                startActivity(intent);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPwd.getText().toString();
                /*if(username.isEmpty()||password.isEmpty())
                {
                    Util u=new Util();
                    Util.showErrorMsgDialog(MainActivity.this,"请先输入账号密码");
                    checkBox.setChecked(false);
                }*/
                boolean checkboxLogin = checkBox.isChecked();
                if (checkboxLogin) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", username);
                    editor.putString("pwd", password);
                    editor.putBoolean("checkBoolean", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", null);
                    editor.putString("pwd", null);
                    editor.putBoolean("checkBoolean", false);
                    editor.commit();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPwd.getText().toString();
                boolean checkboxLogin = checkBox.isChecked();
                if (checkboxLogin) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", username);
                    editor.putString("pwd", password);
                    editor.putBoolean("checkBoolean", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", null);
                    editor.putString("pwd", null);
                    editor.putBoolean("checkBoolean", false);
                    editor.commit();
                }

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                if (username.isEmpty()) {
                    dlg.setTitle("错误");
                    dlg.setMessage("请输入用户名!");
                    dlg.show();
                    return;
                }
                if (password.isEmpty()) {
                    dlg.setTitle("错误");
                    dlg.setMessage("请输入密码!");
                    dlg.show();
                    return;
                }

                /*String sendMsg="logon\n"+username+"\n"+password;
                String retMsg= CMSClient.sendAndReceive(sendMsg);*/

                //loading动画加载
                LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
                loadingDialog.setMessage("loading");
                loadingDialog.show();

                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);


                        Bundle b = msg.getData();
                        String retMsg = b.getString("retMsg");
                        String[] arr = retMsg.split("\n");
                        loadingDialog.dismiss();
                        if (arr[0].equals("Y")) {
                            int userType = Integer.parseInt(arr[1]);
                            GlobalConfig.currentUser = username;

                            if (userType == 0) {//学生
                                //Intent intent=new Intent(MainActivity.this, StudentActivity.class);
                                Intent intent = new Intent(MainActivity.this, ClassTable.class);
                                startActivity(intent);
                            } else if (userType == 1) {//教师
                                //Intent intent=new Intent(MainActivity.this, TeacherActivity.class);
                                Intent intent = new Intent(MainActivity.this, TeacherCourseActivity.class);
                                startActivity(intent);
                            } else if (userType == 2) {//管理员
                                Intent intent = new Intent(MainActivity.this, AdminMain.class);
                                startActivity(intent);
                            }

                        } else if (arr[0].equals("N")) {
                            String errMsg = arr[1];
                            //AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                            dlg.setTitle("登录失败");
                            dlg.setMessage(errMsg);
                            dlg.show();
                        }
                    }
                };
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        Message message = new Message();
                        message.what = 1;
                        String sendMsg = "logon\n" + username + "\n" + password;
                        String retMsg = CMSClient.sendAndReceive(sendMsg);
                        Bundle b = new Bundle();// 存放数据
                        b.putString("retMsg", retMsg);
                        message.setData(b);
                        handler.sendMessage(message);
                    }
                }.start();

                    /*String[] arr=retMsg.split("\n");
                    if(arr[0].equals("Y"))
                    {
                        int userType=Integer.parseInt(arr[1]);
                        GlobalConfig.currentUser=username;

                        if(userType==0)
                        {
                            Intent intent=new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(intent);
                        }else if(userType==1)
                        {
                            Intent intent=new Intent(MainActivity.this, TeacherActivity.class);
                            startActivity(intent);
                        }else if(userType==2)
                        {
                            Intent intent=new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }

                    }else if(arr[0].equals("N"))
                    {
                        String errMsg=arr[1];
                        //AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                        dlg.setTitle("登录失败");
                        dlg.setMessage(errMsg);
                        dlg.show();
                    }*/
            }

        });

        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            //重设密码
            @Override
            public void onClick(View v) {
                /*VerifyCodeDialog dlg=new VerifyCodeDialog();
                dlg.Dialog(MainActivity.this);*/
                VerifyCodeDialog.ResetPwd(MainActivity.this);

            }
        });

    }//onCreate()结束


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug", "onstart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug", "onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug", "onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug", "ondestroy");
    }
}
