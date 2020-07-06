package cms.app.cms.student.model;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import cms.app.cms.model.GlobalConfig;
import cms.app.cms.model.Util;
import cms.app.net.CMSClient;

/**
 * @author ZQZESS
 * @date 2020/6/24. 0:29
 * GitHub：
 * email：
 * description：
 */
public class DataRepeatCheck {

    DayChange dayChange = new DayChange();
    Util u = new Util();

    public String dateCheck(Context context, String time) {
        String timereturn = "";
        String timereturntmp;

        int m = 0, n = 5;
        String retMsg = CMSClient.sendAndReceive("queryAllCourseTime\n" + GlobalConfig.currentUser);
        String[] arrString = retMsg.split(",");
        int tmp = arrString.length / 3;
        //int tmp=((retMsg.length()-1)/2+1)/3;
        String[] timearr = new String[tmp];

        int k = 0;
        for (int i = 0; i < tmp; i++) {
            /*for(int j=0;j<arrString.length;j=j+3)
            {
                timearr[i]=arrString[j]+","+arrString[j+1]+","+arrString[j+2];
                //break;
            }*/
            timearr[i] = arrString[k] + "," + arrString[k + 1] + "," + arrString[k + 2];
            k = k + 3;
        }

       /* for(int i=0;i<tmp;i++)
        {
            //算法不对,10算两个字节
            timearr[i]=retMsg.substring(m+6*i,n+6*i);//把日期按3个数字两个逗号分割
        }*/

        if (time.isEmpty()) {//日期是空
           /* timereturntmp=dayChange.timeCheckChange(time);
            boolean SFCZ=false;
            for(int i=0;i<tmp;i++)
            {
                if(timearr[i].equals(timereturntmp))
                {
                    AlertDialog.Builder dlg=new AlertDialog.Builder(context);
                    dlg.setTitle("错误");
                    dlg.setMessage("已经存在相同时间的课程");
                    dlg.show();
                    //u.showErrorMsgDialog(context,"已经存在相同时间的课程");
                    timereturn="";
                    SFCZ=true;
                }
            }
            if(!SFCZ)
            {
                timereturn=time;
            }*/
            timereturn = time;
        } else if (!time.isEmpty()) {
            if (time.length() == 5) {//只有一个日期
                timereturntmp = dayChange.timeCheckChange(time);
                boolean SFCZ = false;
                for (int i = 0; i < tmp; i++) {
                    if (timearr[i].equals(timereturntmp)) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setTitle("错误");
                        dlg.setMessage("已经存在相同时间的课程");
                        dlg.show();
                        //u.showErrorMsgDialog(context,"已经存在相同时间的课程");
                        timereturn = "";
                        SFCZ = true;
                    }
                }
                if (!SFCZ) {
                    timereturn = time;
                    SFCZ = false;
                }
            } else {//多个日期
                timereturntmp = dayChange.timeCheckChange(time);
                String[] arr = timereturntmp.split("-");
                for (int i = 0; i < tmp; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        if (arr[j].equals(timearr[i])) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                            dlg.setTitle("错误");
                            dlg.setMessage("已经存在相同时间的课程");
                            dlg.show();
                            //u.showErrorMsgDialog(context,"已经存在相同时间的课程");
                            arr[j] = "";
                        }
                    }
                }
                for (int i = 0; i < arr.length; i++) {
                    timereturn = timereturn + arr[i] + ",";
                }

                timereturn = timereturn.substring(0, timereturn.length() - 1);
                String[] arrtmp = timereturn.split(",");

                timereturn = dayChange.daychange(arrtmp.length, arrtmp);
            }

        }

        return timereturn;
    }
}
