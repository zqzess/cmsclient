package cms.app.cms.teacher.ui;
/**
 * @author ZQZESS
 * @date 2020/6/17-1:28
 * GitHub：
 * email：
 * description：
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hb.dialog.dialog.ConfirmDialog;

import java.util.ArrayList;

import cms.app.R;
import cms.app.cms.model.GlobalConfig;
import cms.app.cms.model.Util;
import cms.app.cms.student.model.DataRepeatCheck;
import cms.app.cms.student.model.DayChange;
import cms.app.cms.student.ui.CourseDetailActivity;
import cms.app.cms.teacher.model.WheelView;
import cms.app.net.CMSClient;

public class TeacherCourseDetailActivity extends AppCompatActivity {
    private TextView tvWeek;
    private TextView tvLessonfrom;
    private TextView tvLessonto;

    private String selectText = "";

    private ArrayList<String> lessontoList = new ArrayList<>();
    private ArrayList<String> lessonfromList = new ArrayList<>();
    private ArrayList<String> weekList = new ArrayList<>();


    String id;//课程号
    EditText textViewId;
    EditText textViewName;
    EditText textViewType;
    EditText textViewCredit;
    EditText textViewBook;
    TextView textViewTeachername;
    EditText textViewClassroom;
    TextView textViewTime;

    TextView textViewClass;
    TextView timeDelete;

    RadioGroup radioButton;
    RadioButton radioxuanxiu;//选修修radiobutton
    RadioButton radiobixiu;//必修radiobutton

    Button btnTeacherEdit;//编辑
    Button btnTeacherDelete;//删除
    Button btnStudentchoose;//查看选修学生
    Button btnChoose;//选择班级
    Button btnTimeTrue;//确认时间选择

    LinearLayout layoutclass;//XML班级模块LinerLayout
    LinearLayout layoutbtn;//XML查看选修学生模块LinerLayout
    LinearLayout layoutTimeChoose;//xml时间滑动选择模块LinerLayout,默认不可见
    LinearLayout layoutTextViewTime;//xml时间模块LinerLayout
    LinearLayout layoutbtnChoose;//XML班级选择按钮LinerLayout
    LinearLayout layoutRadiobtn;
    LinearLayout layouttype;//XML类型模块LinerLayout,内含radiogroup
    LinearLayout layoutteachername;


    int type;//课程类型
    int radiotype = -1;//radiobutton
    String returnedData;//班级选择activity返回值
    int returcount;//班级数量返回值
    DayChange daychange = new DayChange();//日期转换
    Util utilmsg = new Util();//消息弹窗

    DataRepeatCheck dataRepeatCheck = new DataRepeatCheck();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returnedData = data.getStringExtra("name");
                    returcount = data.getIntExtra("count", 0);
                    textViewClass.setText(returnedData);
                    Log.d("name", returnedData);
                    Log.d("count", "班级数量" + returcount);
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercourse_detial);
        getSupportActionBar().hide();

        textViewId = findViewById(R.id.textview_teachercourse_id);
        textViewName = findViewById(R.id.textview_teachercourse_name);
        textViewType = findViewById(R.id.textview_teachercourse_type);
        textViewCredit = findViewById(R.id.textview_teachercourse_credit);
        textViewBook = findViewById(R.id.textview_teachercourse_book);
        textViewTeachername = findViewById(R.id.textview_teachercourse_teachername);
        textViewClassroom = findViewById(R.id.textview_teachercourse_classroom);
        textViewTime = findViewById(R.id.textview_teachercourse_time);
        textViewClass = findViewById(R.id.textview_teachercourse_class);
        timeDelete = findViewById(R.id.view_teachercourse_delete);

        btnTeacherEdit = findViewById(R.id.btn_teacher_course_cancel);
        btnTeacherDelete = findViewById(R.id.btn_teacher_course_delete);
        btnStudentchoose = findViewById(R.id.btn_teacher_course_student);
        btnChoose = findViewById(R.id.btn_teacher_course_choose);
        btnTimeTrue = findViewById(R.id.btn_teacher_course_timetrue);

        layoutclass = findViewById(R.id.layout_teacher_course_class);
        layoutbtn = findViewById(R.id.layout_teacher_course_btn);
        layoutbtnChoose = findViewById(R.id.layout_teacher_course_choosebtn);
        layoutRadiobtn = findViewById(R.id.layout_teacher_course_radiobtn);
        layouttype = findViewById(R.id.layout_teacher_course_type);
        layoutTimeChoose = findViewById(R.id.layout_teacher_course_timechoose);
        layoutTextViewTime = findViewById(R.id.layout_teacher_course_textviewtime);
        layoutteachername = findViewById(R.id.layout_teacher_course_teachername);

        radioButton = findViewById(R.id.radiobtn_teacher_course);
        radioxuanxiu = findViewById(R.id.radiobtn_teacher_course_xuanxiu);
        radiobixiu = findViewById(R.id.radiobtn_teacher_course_bixiu);


        //获取上一个Activity传回的值
        Intent intent = getIntent();
        id = intent.getStringExtra("cid");
        String name = intent.getStringExtra("name");
        type = intent.getIntExtra("type", 0);
        float credit = intent.getFloatExtra("credit", 0);
        String book = intent.getStringExtra("book");
        String teachername = intent.getStringExtra("teachername");
        String classroom = intent.getStringExtra("classroom");
        String timetmp = intent.getStringExtra("time");

        //日期转换
        String[] arr = timetmp.split(",");
        DayChange a = new DayChange();
        int length = arr.length;
        String time = a.daychange(length, arr);

        String retMsg = CMSClient.sendAndReceive("querycourseClass\n" + id);//查询班级


        textViewId.setText(id);
        textViewName.setText(name);
        if (type == 0) {
            textViewType.setText("必修");
            layoutbtn.setVisibility(View.GONE);

        } else if (type == 1) {
            textViewType.setText("选修");
            layoutclass.setVisibility(View.GONE);
        }
        String tmp = Float.toString(credit);
        textViewCredit.setText(tmp);
        textViewBook.setText(book);
        textViewTeachername.setText(teachername);
        textViewClassroom.setText(classroom);
        textViewTeachername.setEnabled(false);
        textViewTime.setText(time);
        textViewClass.setText(retMsg);
        textViewClass.setEnabled(false);

        layoutbtnChoose.setVisibility(View.GONE);
        layoutRadiobtn.setVisibility(View.GONE);
        layoutTimeChoose.setVisibility(View.GONE);


        initView();

        initData();

        initListeners();

        btnStudentchoose.setOnClickListener(new View.OnClickListener() {
            //把课程号传给WhoChooseCourseActivity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherCourseDetailActivity.this, WhoChooseCourse.class);
                intent.putExtra("cid", id);
                startActivity(intent);
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            //班级选择按钮
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherCourseDetailActivity.this, CheckSelectClassActivity.class);
                startActivityForResult(intent, 1);
                textViewClass.setText(returnedData);
            }
        });


        timeDelete.setOnClickListener(new View.OnClickListener() {
            //时间删除按钮
            @Override
            public void onClick(View v) {
                String timetmp = textViewTime.getText().toString();
                if (timetmp.isEmpty()) {
                    return;
                } else if (timetmp.length() == 5) {
                    String tmp = "";
                    textViewTime.setText(tmp);
                } else if (timetmp.length() > 5) {
                    //String[] arrtmp=timetmp.split(",");
                    String tmp = timetmp.substring(0, timetmp.length() - 6);
                    textViewTime.setText(tmp);
                }
            }
        });
        radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //类型选择
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_teacher_course_bixiu:
                        radiotype = 0;
                        layoutclass.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radiobtn_teacher_course_xuanxiu:
                        radiotype = 1;
                        layoutclass.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btnTeacherEdit.setOnClickListener(new View.OnClickListener() {
            //编辑按钮
            @Override
            public void onClick(View v) {
                String tmp = btnTeacherEdit.getText().toString();
                //类型radio自动选择
                if (type == 1) {
                    //选修课，type=1
                    if (tmp.equals("编辑")) {
                        btnTeacherEdit.setText("保存");
                        editSetEnable();
                        radioxuanxiu.setChecked(true);
                        radiotype = 1;
                    } else if (tmp.equals("保存")) {

                        editGetTextSend(radiotype);
                        //btnTeacherEdit.setText("编辑");
                        //editSetDisable();
                    }
                } else if (type == 0) {
                    if (tmp.equals("编辑")) {
                        btnTeacherEdit.setText("保存");
                        editSetEnable();
                        //layoutbtnChoose.setVisibility(View.VISIBLE);
                        radiobixiu.setChecked(true);
                        radiotype = 0;
                    } else if (tmp.equals("保存")) {

                        editGetTextSend(radiotype);
                        //btnTeacherEdit.setText("编辑");
                        //editSetDisable();
                        //layoutbtnChoose.setVisibility(View.GONE);
                    }
                }
            }
        });

        btnTeacherDelete.setOnClickListener(new View.OnClickListener() {
            //删除按钮
            @Override
            public void onClick(View v) {
                ConfirmDialog confirmDialog = new ConfirmDialog(TeacherCourseDetailActivity.this);
                confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认删除此选修课？");
                confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                    @Override
                    public void ok() {
                        String sendMsg = "TeacherDeleteCourse\n" + id + "\n" + type;
                        String retMsg = CMSClient.sendAndReceive(sendMsg);
                        finish();
                    }

                    @Override
                    public void cancel() {
                        return;
                    }
                });
                confirmDialog.show();
            }
        });
        btnTimeTrue.setOnClickListener(new View.OnClickListener() {
            //时间选择按钮
            @Override
            public void onClick(View v) {
                String tmp = textViewTime.getText().toString();
                if (tmp.length() == 5) {
                    String weekTmp = tvWeek.getText().toString();
                    String lessonfromTmp = tvLessonfrom.getText().toString();
                    String lessontoTmp = tvLessonto.getText().toString();
                    int lessonfromChange = daychange.dayBackChange(lessonfromTmp);//日期转换
                    int lessontoChange = daychange.dayBackChange(lessontoTmp);//日期转换
                    int sizeTmp = lessontoChange - lessonfromChange;
                    if (weekTmp.equals("选择星期>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择星期");
                        return;
                    }
                    if (lessonfromTmp.equals("起始课次>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择起始课次");
                        return;
                    }
                    if (lessontoTmp.equals("结束课次>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择结束课次");
                        return;
                    }

                    if (lessontoChange < lessonfromChange) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程截止课次不能小于课程起始课次");
                        return;
                    } else if (sizeTmp == 2 || sizeTmp > 3 || sizeTmp == 0) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程只能是相邻两个或四个课次");
                        return;
                    } else {

                        String timeTmp = weekTmp + lessonfromChange + "-" + lessontoChange;
                        if (tmp.equals(timeTmp)) {
                            utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程时间不能重复");
                        } else {

                            timeTmp = dataRepeatCheck.dateCheck(TeacherCourseDetailActivity.this, timeTmp);//日期重复性检查
                            if (timeTmp.isEmpty()) {
                                textViewTime.setText(tmp);
                            } else {
                                timeTmp = tmp + "," + timeTmp;
                                textViewTime.setText(timeTmp);//最终显示的时间
                            }
                        }
                    }
                } else {
                    String[] tmparr = tmp.split(",");
                    if (tmparr.length > 2) {//限制一周只能设置三天课程
                        //AlertDialog.Builder dlg=new AlertDialog.Builder(TeacherCourseDetailActivity.this);
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "一周只能设置三天的课程");
                    /*dlg.setTitle("错误");
                    dlg.setMessage("一周只能设置三天的课程");
                    dlg.show();*/
                        for (int i = 0; i < tmparr.length; i++) {
                            if (i >= 3) {


                                tmparr[2] = dataRepeatCheck.dateCheck(TeacherCourseDetailActivity.this, tmp);//日期重复性检查
                                if (tmparr[2].isEmpty()) {
                                    tmp = tmparr[0] + "," + tmparr[1];
                                    textViewTime.setText(tmp);//最终显示的时间
                                } else {
                                    tmp = tmparr[0] + "," + tmparr[1] + "," + tmparr[2];
                                    textViewTime.setText(tmp);//最终显示的时间
                                }


                            }
                        }
                        return;
                    }

                    String weekTmp = tvWeek.getText().toString();
                    String lessonfromTmp = tvLessonfrom.getText().toString();
                    String lessontoTmp = tvLessonto.getText().toString();
                    int lessonfromChange = daychange.dayBackChange(lessonfromTmp);//日期转换
                    int lessontoChange = daychange.dayBackChange(lessontoTmp);//日期转换
                    int sizeTmp = lessontoChange - lessonfromChange;

                    if (weekTmp.equals("选择星期>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择星期");
                        return;
                    }
                    if (lessonfromTmp.equals("起始课次>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择起始课次");
                        return;
                    }
                    if (lessontoTmp.equals("结束课次>")) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "请先选择结束课次");
                        return;
                    }

                    if (lessontoChange < lessonfromChange) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程截止课次不能小于课程起始课次");
                        return;
                    } else if (sizeTmp == 2 || sizeTmp > 3 || sizeTmp == 0) {
                        utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程只能是相邻两个或四个课次");
                        return;
                    } else {
                        String timeTmp = weekTmp;
                        timeTmp = timeTmp + lessonfromChange;
                        timeTmp = timeTmp + "-";
                        timeTmp = timeTmp + lessontoChange;
                        if (tmp != null && !tmp.equals("")) {
                            //日期不为空
                            //timeTmp = tmp + "," + timeTmp;

                            //日期不能重复
                            //String tmp2=textViewTime.getText().toString();
                            boolean SFCZ = false;
                            //Log.d("create","长度"+tmp.length());
                            for (int i = 0; i < tmparr.length; i++) {
                                if (tmparr[i].equals(timeTmp)) {
                                    utilmsg.showErrorMsgDialog(TeacherCourseDetailActivity.this, "课程时间不能重复");
                                    SFCZ = true;
                                    break;
                                }
                            }
                            if (!SFCZ) {

                                timeTmp = dataRepeatCheck.dateCheck(TeacherCourseDetailActivity.this, timeTmp);//日期重复性检查
                                if (timeTmp.isEmpty()) {
                                    textViewTime.setText(tmp);//最终显示的时间

                                } else {
                                    timeTmp = tmp + "," + timeTmp;
                                    textViewTime.setText(timeTmp);//最终显示的时间
                                    SFCZ = false;
                                }


                            }
                            //textViewTime.setText(timeTmp);
                        } else if (tmp.equals("") || tmp == null) {
                            //日期为空
                            timeTmp = dataRepeatCheck.dateCheck(TeacherCourseDetailActivity.this, timeTmp);
                            textViewTime.setText(timeTmp);//最终显示的时间
                        }
                    }
                }
            }
        });
        //AlertDialog.Builder dlg=new AlertDialog.Builder(TeacherCourseDetailActivity.this);
        /*textViewTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });*/
    }


    private void editSetEnable() {
        //编辑框可用
        //textViewId.setEnabled(true);
        textViewName.setEnabled(true);
        textViewType.setEnabled(true);
        textViewCredit.setEnabled(true);
        textViewBook.setEnabled(true);
        textViewTeachername.setEnabled(true);
        textViewClassroom.setEnabled(true);
        textViewTime.setEnabled(true);
        layoutbtnChoose.setVisibility(View.VISIBLE);
        layouttype.setVisibility(View.GONE);
        layoutRadiobtn.setVisibility(View.VISIBLE);
        textViewClass.setEnabled(true);
        layoutTimeChoose.setVisibility(View.VISIBLE);
        layoutbtn.setVisibility(View.GONE);
        //layoutTextViewTime.setVisibility(View.GONE);
        layoutteachername.setVisibility(View.GONE);
    }

    private void editSetDisable() {
        //编辑框不可用
        //textViewId.setEnabled(false);
        textViewName.setEnabled(false);
        textViewType.setEnabled(false);
        textViewCredit.setEnabled(false);
        textViewBook.setEnabled(false);
        textViewTeachername.setEnabled(false);
        textViewClassroom.setEnabled(false);
        textViewTime.setEnabled(false);
        layoutbtnChoose.setVisibility(View.GONE);
        layoutRadiobtn.setVisibility(View.GONE);
        layouttype.setVisibility(View.VISIBLE);
        layoutTimeChoose.setVisibility(View.GONE);
        //layoutTextViewTime.setVisibility(View.VISIBLE);
        textViewClass.setEnabled(false);
        layoutteachername.setVisibility(View.VISIBLE);
    }

    private void editGetTextSend(int type) {
        //保存更新数据库
        String cid = textViewId.getText().toString();
        String cname = textViewName.getText().toString();
        //String typetmp=textViewType.getText().toString();
        //int ctype=Integer.getInteger(typetmp);
        int ctype = radiotype;
        String credittmp = textViewCredit.getText().toString();
        //float ccredit = Float.parseFloat(credittmp);
        String cbook = textViewBook.getText().toString();
        String cteachername = textViewTeachername.getText().toString();
        String cclassroom = textViewClassroom.getText().toString();
        String ctimeTmp = textViewTime.getText().toString();

        if (cname.isEmpty() || credittmp.isEmpty() || cbook.isEmpty() && cteachername.isEmpty() || cclassroom.isEmpty() || ctimeTmp.isEmpty() || credittmp.isEmpty() || cclassroom.isEmpty()) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(TeacherCourseDetailActivity.this);
            dlg.setTitle("错误");
            dlg.setMessage("请将课程信息填写完整");
            dlg.show();
            return;
        }
        if (type == 0) {
            String classname = textViewClass.getText().toString();
            if (classname.isEmpty()) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(TeacherCourseDetailActivity.this);
                dlg.setTitle("错误");
                dlg.setMessage("请先选择班级");
                dlg.show();
                btnTeacherEdit.setText("保存");
                return;

            } else {
                float ccredit = Float.parseFloat(credittmp);
                String ctime = daychange.timeChange(ctimeTmp);

                ConfirmDialog confirmDialog = new ConfirmDialog(TeacherCourseDetailActivity.this);
                confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认修改此选修课？");
                confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                    @Override
                    public void ok() {
                        String sendMsg = "editCourseInfo\n" + cid + "\n" + cname + "\n" + ctype + "\n" + ccredit + "\n" + cbook + "\n" + GlobalConfig.currentUser + "\n" + cclassroom + "\n" + ctime + "\n" + classname + "\n" + returcount;
                        String retMsg = CMSClient.sendAndReceive(sendMsg);
                        editSetDisable();
                        btnTeacherEdit.setText("编辑");
                        finish();
                    }

                    @Override
                    public void cancel() {
                        btnTeacherEdit.setText("保存");
                        return;
                    }
                });
                confirmDialog.show();


            }
        } else if (type == 1) {
            //选修课,无班级
            float ccredit = Float.parseFloat(credittmp);
            String classname = "";
            String ctime = daychange.timeChange(ctimeTmp);

            ConfirmDialog confirmDialog = new ConfirmDialog(TeacherCourseDetailActivity.this);
            confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认修改此选修课？");
            confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                @Override
                public void ok() {
                    String sendMsg = "editCourseInfo\n" + cid + "\n" + cname + "\n" + ctype + "\n" + ccredit + "\n" + cbook + "\n" + GlobalConfig.currentUser + "\n" + cclassroom + "\n" + ctime + "\n" + classname + "\n" + returcount;
                    String retMsg = CMSClient.sendAndReceive(sendMsg);
                    editSetDisable();
                    btnTeacherEdit.setText("编辑");
                    finish();
                }

                @Override
                public void cancel() {
                    btnTeacherEdit.setText("保存");
                    return;
                }
            });
            confirmDialog.show();


        }

    }

    private void initData() {
        // 填充列表
        weekList.clear();
        lessontoList.clear();
        lessonfromList.clear();
        String[] weekarr = {"一", "二", "三", "四", "五", "六", "日"};
        for (int i = 0; i < 7; i++) {
            //weekList.add(String.format("周%d",i));
            weekList.add("周" + weekarr[i]);
        }
        for (int i = 1; i <= 10; i++) {
            lessontoList.add(String.format("第%d", i));//整数类型十进制
        }
        for (int i = 1; i <= 10; i++) {
            lessonfromList.add(String.format("第%d", i));
        }
    }

    private void initView() {
        tvWeek = findViewById(R.id.tv_week);
        tvLessonfrom = findViewById(R.id.tv_lessonfrom);
        tvLessonto = findViewById(R.id.tv_lessonto);
    }

    private void initListeners() {
        //findViewById(R.id.tv_week).setOnClickListener(view ->showDialog(tvWeek, weekList, 8));
        findViewById(R.id.tv_week).setOnClickListener(view -> {
            showDialog(tvWeek, weekList, 3);
        });
        //findViewById(R.id.tv_lessonfrom).setOnClickListener(view -> showDialog(tvLessonfrom,lessontoList,40));
        findViewById(R.id.tv_lessonfrom).setOnClickListener(view -> {
            showDialog(tvLessonfrom, lessontoList, 3);
        });
        //findViewById(R.id.tv_lessonto).setOnClickListener(view -> showDialog(tvLessonto,lessonfromList,10));
        findViewById(R.id.tv_lessonto).setOnClickListener(view -> {
            showDialog(tvLessonto, lessonfromList, 4);
        });
    }

    private void showDialog(TextView textView, ArrayList<String> list, int selected) {
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }

    private void showChoiceDialog(ArrayList<String> dataList, final TextView textView, int selected,
                                  WheelView.OnWheelViewListener listener) {
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheelview, null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            textView.setText(selectText);
                            textView.setTextColor(this.getResources().getColor(R.color.dialog_black));
                        })
                .setNegativeButton("取消", null).create();
        alertDialog.show();
        int greenid = Color.parseColor("#98FB98");
        //int green = this.getResources().getColor(Color.green());
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(greenid);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(greenid);
    }


}
