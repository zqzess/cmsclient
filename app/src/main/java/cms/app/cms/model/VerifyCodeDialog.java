package cms.app.cms.model;

import android.app.Activity;

import android.content.Context;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.hb.dialog.myDialog.MyAlertInputDialog;

import cms.app.R;
import cms.app.cms.admin.ui.AdminManagerClassFragment;
import cms.app.cms.student.ui.CourseDetailActivity;
import cms.app.net.CMSClient;

/**
 * @author ZQZESS
 * @date 2020/6/23. 19:47
 * GitHub：
 * email：
 * description：
 */
public class VerifyCodeDialog {
    //Activity context;
    static String code;
    //View.OnClickListener mClickListener;


    public void Dialog(Context context) {

        EditText VerifyCode;
        //TextView viewRefresh;
        TextView viewshowMessage;
        //Button btnSumbit;
        ImageView imageView;


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请先输入验证码");
        builder.setCancelable(true);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(context).inflate(R.layout.verifycode, null);
        // 设置定义的布局文件作为弹出框的Content
        builder.setView(view);
        //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        VerifyCode = view.findViewById(R.id.edittext_verifycode);
        imageView = view.findViewById(R.id.verifycodepic);
        viewshowMessage = view.findViewById(R.id.view_showmessage);

        imageView.setImageBitmap(cms.app.cms.model.VerifyCode.getInstance().createBitmap());
        code = cms.app.cms.model.VerifyCode.getInstance().getCode().toLowerCase();//转小写

        view.findViewById(R.id.btn_verifycode_submit).setOnClickListener(new View.OnClickListener() {
            //提交按钮
            @Override
            public void onClick(View v) {
                String codetmp = VerifyCode.getText().toString().toLowerCase();
                if (!codetmp.equals(code)) {
                    viewshowMessage.setText("验证码不正确");
                    return;
                } else {
                    //Log.d("code","返回值"+result);
                    //GlobalConfig.result = 1;
                    GlobalConfig.flag = 0;
                    dialog.dismiss();
                }
            }
        });

        view.findViewById(R.id.view_verifycode_refresh).setOnClickListener(new View.OnClickListener() {
            //刷新按钮
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(cms.app.cms.model.VerifyCode.getInstance().createBitmap());
                code = cms.app.cms.model.VerifyCode.getInstance().getCode().toLowerCase();
            }
        });

        VerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String codetmp = VerifyCode.getText().toString().toLowerCase();
                if (codetmp.equals(code)) {

                    //GlobalConfig.result = 1;
                    //Log.d("code","返回值"+result);
                    GlobalConfig.flag = 0;
                    viewshowMessage.setText("");
                    dialog.dismiss();
                }
            }

        });

        //result = GlobalConfig.result;
        //Log.d("code", "最终返回值" + result);
        //return result;
    }


    public static void ResetPwd(Context context) {//忘记密码
        EditText VerifyCode;
        EditText editUserid;
        EditText editPwd;
        TextView viewCheck;
        TextView viewCheckFalse;
        //TextView viewRefresh;
        TextView viewshowMessage;
        //Button btnSumbit;
        ImageView imageView;


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请先输入验证码");
        builder.setCancelable(true);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(context).inflate(R.layout.resetpwd, null);
        // 设置定义的布局文件作为弹出框的Content
        builder.setView(view);
        //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        VerifyCode = view.findViewById(R.id.reset_edittext_verifycode);
        imageView = view.findViewById(R.id.reset_verifycodepic);
        viewshowMessage = view.findViewById(R.id.reset_view_showmessage);
        editUserid = view.findViewById(R.id.reset_edit_userid);
        editPwd = view.findViewById(R.id.reset_edit_pwd);
        viewCheck = view.findViewById(R.id.reset_view_check);
        viewCheckFalse = view.findViewById(R.id.reset_view_checkfalse);


        imageView.setImageBitmap(cms.app.cms.model.VerifyCode.getInstance().createBitmap());
        code = cms.app.cms.model.VerifyCode.getInstance().getCode().toLowerCase();//转小写

        view.findViewById(R.id.reset_btn_verifycode_submit).setOnClickListener(new View.OnClickListener() {
            //提交按钮
            @Override
            public void onClick(View v) {

                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(context).builder()
                        .setTitle("请输入")
                        .setEditText("请输入要修改的密码");

                String codetmp = VerifyCode.getText().toString().toLowerCase();
                String useridTmp = editUserid.getText().toString();
                if (useridTmp.isEmpty()) {
                    viewshowMessage.setText("请先输入账号");
                    return;
                }
                if (!codetmp.equals(code)) {
                    viewshowMessage.setText("验证码不正确");
                    return;
                } else {
                    viewshowMessage.setText("");
                    String sendMsg = "ResetPwdVerify\n" + useridTmp;
                    String retMsg = CMSClient.sendAndReceive(sendMsg);
                    if (retMsg.equals("Y")) {
                        viewshowMessage.setText("");
                        //另一种写法
                        /*String newPwd=editPwd.getText().toString();
                        if(newPwd.isEmpty())
                        {
                            viewshowMessage.setText("请输入要修改的密码");
                        }else
                        {
                            viewshowMessage.setText("");
                            CMSClient.sendAndReceive("updatePwd\n"+useridTmp+"\n"+newPwd);
                        }*/

                        //弹窗输入密码
                        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String newpwd = myAlertInputDialog.getResult();
                                Log.d("newpwd:", newpwd);
                                CMSClient.sendAndReceive("updatePwd\n" + useridTmp + "\n" + newpwd);
                                myAlertInputDialog.dismiss();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //return;
                                myAlertInputDialog.dismiss();
                            }
                        });

                    } else if (retMsg.equals("N")) {
                        viewshowMessage.setText("该账户不存在");
                    }

                    dialog.dismiss();

                    myAlertInputDialog.show();
                }
            }
        });

        view.findViewById(R.id.reset_view_verifycode_refresh).setOnClickListener(new View.OnClickListener() {
            //刷新按钮
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(cms.app.cms.model.VerifyCode.getInstance().createBitmap());
                code = cms.app.cms.model.VerifyCode.getInstance().getCode().toLowerCase();
            }
        });

        editUserid.addTextChangedListener(new TextWatcher() {
            //实时动态显示
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String useridTmp = editUserid.getText().toString();
                if (useridTmp.isEmpty()) {
                    viewCheck.setVisibility(View.GONE);
                    viewCheckFalse.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String useridTmp = editUserid.getText().toString();
                String sendMsg = "ResetPwdVerify\n" + useridTmp;
                String retMsg = CMSClient.sendAndReceive(sendMsg);
                if (retMsg.equals("Y")) {
                    viewCheck.setVisibility(View.VISIBLE);
                    viewCheckFalse.setVisibility(View.GONE);
                } else if (retMsg.equals("N")) {
                    viewCheckFalse.setVisibility(View.VISIBLE);
                    viewCheck.setVisibility(View.GONE);
                }

            }
        });
    }
}
