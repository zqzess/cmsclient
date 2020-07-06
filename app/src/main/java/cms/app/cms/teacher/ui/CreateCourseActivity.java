package cms.app.cms.teacher.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

/**
 * @author ZQZESS
 * @date 2020/6/18-0:25
 * GitHub：
 * email：
 * description：
 */
public class CreateCourseActivity extends AppCompatActivity {
    private TextView tvWeek;
    private TextView tvLessonfrom;
    private TextView tvLessonto;

    private String selectText = "";

    private ArrayList<String> lessontoList = new ArrayList<>();
    private ArrayList<String> lessonfromList = new ArrayList<>();
    private ArrayList<String> weekList = new ArrayList<>();

    RadioGroup radioButton;
    RadioButton radioxuanxiu;//选修修radiobutton
    RadioButton radiobixiu;//必修radiobutton

    EditText textViewId;
    EditText textViewName;
    //EditText textViewType;
    EditText textViewCredit;
    EditText textViewBook;
    //EditText textViewTeachername;
    TextView textViewClassroom;
    TextView textViewTime;
    TextView viewdelete;
    TextView textViewClass;

    Button btnChoose;//选择班级
    Button btnTimeTrue;//确认时间选择
    Button btnCourseCreate;//课程创建按钮

    LinearLayout layoutclass;//XML班级模块LinerLayout

    int radiotype = -1;//radiobutton
    String returnedData;//班级选择activity返回值
    DayChange daychange = new DayChange();//日期转换
    Util utilmsg = new Util();//消息弹窗
    int returcount;//班级数量返回值

    DataRepeatCheck dataRepeatCheck = new DataRepeatCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcourse);
        getSupportActionBar().hide();

        textViewId = findViewById(R.id.textview_create_course_id);
        textViewName = findViewById(R.id.textview_create_course_name);
        //textViewType = findViewById(R.id.textview_create_course_type);
        textViewCredit = findViewById(R.id.textview_create_course_credit);
        textViewBook = findViewById(R.id.textview_create_course_book);
        //textViewTeachername = findViewById(R.id.textview_create_course_teachername);
        textViewClassroom = findViewById(R.id.textview_create_course_classroom);
        textViewTime = findViewById(R.id.textview_create_course_time);
        textViewClass = findViewById(R.id.textview_create_course_class);
        viewdelete = findViewById(R.id.viewdelete);

        btnChoose = findViewById(R.id.btn_create_course_choose);
        btnTimeTrue = findViewById(R.id.btn_create_course_timetrue);
        btnCourseCreate = findViewById(R.id.btn_create_course_create);

        layoutclass = findViewById(R.id.layout_create_course_class);
        layoutclass.setVisibility(View.GONE);//班级模块默认不可见

        radioButton = findViewById(R.id.radiobtn_create_course_course);
        radioxuanxiu = findViewById(R.id.radiobtn_create_course_xuanxiu);
        radiobixiu = findViewById(R.id.radiobtn_create_course_bixiu);

        initView();

        initData();

        initListeners();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            //班级选择
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateCourseActivity.this, CheckSelectClassActivity.class);
                startActivityForResult(intent, 1);
                textViewClass.setText(returnedData);
            }
        });

        radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //类型选择
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_create_course_bixiu:
                        radiotype = 0;
                        layoutclass.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radiobtn_create_course_xuanxiu:
                        radiotype = 1;
                        layoutclass.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btnTimeTrue.setOnClickListener(new View.OnClickListener() {
            //时间选择
            @Override
            public void onClick(View v) {
                String tmp = textViewTime.getText().toString();
                if (tmp.length() == 5) {
                    String weekTmp = tvWeek.getText().toString();
                    String lessonfromTmp = tvLessonfrom.getText().toString();
                    String lessontoTmp = tvLessonto.getText().toString();
                    int lessonfromChange = daychange.dayBackChange(lessonfromTmp);
                    int lessontoChange = daychange.dayBackChange(lessontoTmp);
                    int sizeTmp = lessontoChange - lessonfromChange;
                    if (weekTmp.equals("选择星期>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择星期");
                        return;
                    }
                    if (lessonfromTmp.equals("起始课次>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择起始课次");
                        return;
                    }
                    if (lessontoTmp.equals("结束课次>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择结束课次");
                        return;
                    }

                    if (lessontoChange < lessonfromChange) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程截止课次不能小于课程起始课次");
                        return;
                    } else if (sizeTmp == 2 || sizeTmp > 3 || sizeTmp == 0) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程只能是相邻两个或四个课次");
                        return;
                    } else {

                        String timeTmp = weekTmp + lessonfromChange + "-" + lessontoChange;
                        if (tmp.equals(timeTmp)) {
                            utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程时间不能重复");
                        } else {

                            timeTmp = dataRepeatCheck.dateCheck(CreateCourseActivity.this, timeTmp);//日期重复性检查
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
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "一周只能设置三天的课程");
                    /*dlg.setTitle("错误");
                    dlg.setMessage("一周只能设置三天的课程");
                    dlg.show();*/
                        for (int i = 0; i < tmparr.length; i++) {
                            if (i >= 3) {
                                tmparr[2] = dataRepeatCheck.dateCheck(CreateCourseActivity.this, tmp);//日期重复性检查
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
                    int lessonfromChange = daychange.dayBackChange(lessonfromTmp);
                    int lessontoChange = daychange.dayBackChange(lessontoTmp);
                    int sizeTmp = lessontoChange - lessonfromChange;
                    if (weekTmp.equals("选择星期>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择星期");
                        return;
                    }
                    if (lessonfromTmp.equals("起始课次>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择起始课次");
                        return;
                    }
                    if (lessontoTmp.equals("结束课次>")) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择结束课次");
                        return;
                    }

                    if (lessontoChange < lessonfromChange) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程截止课次不能小于课程起始课次");
                        return;
                    } else if (sizeTmp == 2 || sizeTmp > 3 || sizeTmp == 0) {
                        utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程只能是相邻两个或四个课次");
                        return;
                    } else {
                        String timeTmp = weekTmp + lessonfromChange + "-" + lessontoChange;
                        if (tmp != null && !tmp.equals("")) {
                            //日期不为空
                            //timeTmp = tmp + "," + timeTmp;

                            //日期不能重复
                            //String tmp2=textViewTime.getText().toString();
                            boolean SFCZ = false;
                            //Log.d("create","长度"+tmp.length());
                            for (int i = 0; i < tmparr.length; i++) {
                                if (tmparr[i].equals(timeTmp)) {
                                    utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "课程时间不能重复");
                                    SFCZ = true;
                                    break;
                                }
                            }
                            if (!SFCZ) {
                                timeTmp = dataRepeatCheck.dateCheck(CreateCourseActivity.this, timeTmp);//日期重复性检查
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
                            timeTmp = dataRepeatCheck.dateCheck(CreateCourseActivity.this, timeTmp);
                            textViewTime.setText(timeTmp);//最终显示时间
                        }
                    }
                }
            }
        });

        btnCourseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = textViewId.getText().toString();
                String cname = textViewName.getText().toString();
                //String typetmp=textViewType.getText().toString();
                //int ctype=Integer.getInteger(typetmp);

                String credittmp = textViewCredit.getText().toString();


                String cbook = textViewBook.getText().toString();
                //String cteachername = textViewTeachername.getText().toString();
                String cclassroom = textViewClassroom.getText().toString();
                String ctimeTemp = textViewTime.getText().toString();

                if (cid.isEmpty() || cname.isEmpty() || credittmp.isEmpty() || cbook.isEmpty() || cclassroom.isEmpty() || ctimeTemp.isEmpty() || credittmp.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(CreateCourseActivity.this);
                    dlg.setTitle("错误");
                    dlg.setMessage("请将课程信息填写完整");
                    dlg.show();
                    return;
                }
                if (radiotype == 0) {
                    //必修课
                    String classname = textViewClass.getText().toString();
                    if (classname.isEmpty()) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(CreateCourseActivity.this);
                        dlg.setTitle("错误");
                        dlg.setMessage("请先选择班级");
                        dlg.show();
                        return;
                    }
                    int ctype = radiotype;
                    float ccredit = Float.parseFloat(credittmp);
                    String ctime = daychange.timeChange(ctimeTemp);
                    ConfirmDialog confirmDialog = new ConfirmDialog(CreateCourseActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认创建此课程？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            String sendMsg = "createCourse\n" + cid + "\n" + cname + "\n" + ctype + "\n" + ccredit + "\n" + cbook + "\n" + GlobalConfig.currentUser + "\n" + cclassroom + "\n" + ctime + "\n" + classname + "\n" + returcount;
                            String retMsg = CMSClient.sendAndReceive(sendMsg);
                            finish();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();


                } else if (radiotype == 1) {
                    //选修课,无班级
                    int ctype = radiotype;
                    float ccredit = Float.parseFloat(credittmp);
                    String classname = "";
                    String ctime = daychange.timeChange(ctimeTemp);
                    ConfirmDialog confirmDialog = new ConfirmDialog(CreateCourseActivity.this);
                    confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认创建此课程？");
                    confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                        @Override
                        public void ok() {
                            String sendMsg = "createCourse\n" + cid + "\n" + cname + "\n" + ctype + "\n" + ccredit + "\n" + cbook + "\n" + GlobalConfig.currentUser + "\n" + cclassroom + "\n" + ctime + "\n" + classname + "\n" + returcount;
                            String retMsg = CMSClient.sendAndReceive(sendMsg);
                            finish();
                        }

                        @Override
                        public void cancel() {
                            return;
                        }
                    });
                    confirmDialog.show();

                } else if (radiotype == -1) {
                    utilmsg.showErrorMsgDialog(CreateCourseActivity.this, "请先选择类型");
                    return;
                }
            }
        });

        viewdelete.setOnClickListener(new View.OnClickListener() {
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
    }

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
        tvWeek = findViewById(R.id.tv_create_courseweek);
        tvLessonfrom = findViewById(R.id.tv_create_courselessonfrom);
        tvLessonto = findViewById(R.id.tv_create_courselessonto);
    }

    private void initListeners() {
        //findViewById(R.id.tv_week).setOnClickListener(view ->showDialog(tvWeek, weekList, 8));
        findViewById(R.id.tv_create_courseweek).setOnClickListener(view -> {
            showDialog(tvWeek, weekList, 3);
        });
        //findViewById(R.id.tv_lessonfrom).setOnClickListener(view -> showDialog(tvLessonfrom,lessontoList,40));
        findViewById(R.id.tv_create_courselessonfrom).setOnClickListener(view -> {
            showDialog(tvLessonfrom, lessontoList, 3);
        });
        //findViewById(R.id.tv_lessonto).setOnClickListener(view -> showDialog(tvLessonto,lessonfromList,10));
        findViewById(R.id.tv_create_courselessonto).setOnClickListener(view -> {
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
