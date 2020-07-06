package cms.app.cms.model;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Util {
    public static void showErrorMsgDialog(Context context, String msg) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("错误");
        dlg.setMessage(msg);
        dlg.show();
    }
}
