package cms.app.cms.student.ui;
/**
 * @author ZQZESS
 * @date 2020/6/16-21:35
 * GitHub：
 * email：
 * description：
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cms.app.cms.model.BorderTextView;
import cms.app.cms.model.CourseInfo;
import cms.app.cms.model.GlobalConfig;
import cms.app.R;
import cms.app.net.CMSClient;
import cms.app.net.QueryStudentAllCoursesResult;

public class ClassTable extends AppCompatActivity {

    protected int aveWidth;//课程格子平均宽度
    protected int screenWidth;//屏幕宽度
    protected int gridHeight = 80;//格子高度
    ImageView imageView;
    TextView textView;
    TextView viewclass;
    TextView viewaddclass;
    LinearLayout Btntablerefresh;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table2);
        getSupportActionBar().hide();

        View menuView = findViewById(R.id.Btn_table_Menu);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        textView = findViewById(R.id.view_userid);
        textView.setText("你好!   " + GlobalConfig.currentUser);
        viewclass = findViewById(R.id.btnview_class);
        viewaddclass = findViewById(R.id.btnview_addclass);
        Btntablerefresh = findViewById(R.id.Btn_table_refresh);
        relativeLayout = findViewById(R.id.relative_table_list);

        Btntablerefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for(int i=0;i<80;i++)
                {
                    TextView tx=new TextView(ClassTable.this);

                }*/
                relativeLayout.removeAllViews();
                ClassTable.this.invalidateOptionsMenu();
                OnInitTable();
                RecvInfo();


            }
        });

        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(findViewById(R.id.left_layout));
                //drawerLayout.openDrawer(findViewById(R.layout.left_layout));

            }
        });
        viewclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTable.this, StudentCourseActivity.class);
                startActivity(intent);
            }
        });
        viewaddclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTable.this, SelectCourseActivity.class);
                startActivity(intent);
            }
        });
        OnInitTable();
        RecvInfo();
        //RandomColor();
    }

    private int RandomColor() {
//随机色，区分课程
        /*int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                R.drawable.main_course3, R.drawable.main_course4,
                R.drawable.main_course5};

        int index=(int)(Math.random()*background.length);
        int rand = background[index];
        int colorIndex = (rand);*/

        /*int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                R.drawable.main_course3, R.drawable.main_course4,
                R.drawable.main_course5,0xff00ffff};*/
        int[] background = {0xff8BC34A, 0xff00ffff, 0xffff00ff, 0xffff0000, 0xffffff00};
        Random random = new Random();
        int rand = random.nextInt(6);


        /*Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);*/

        return rand;
    }

    private void RecvInfo() {
        //从json解析课程时间
        String retMsg = CMSClient.sendAndReceive("query_student_all_courses\n" + GlobalConfig.currentUser);
        Gson gson = new Gson();
        QueryStudentAllCoursesResult result = gson.fromJson(retMsg, QueryStudentAllCoursesResult.class);
        ArrayList<CourseInfo> courses = new ArrayList<>();
        for (CourseInfo c : result.getCourses()) {
            courses.add(c);
        }
        JSONObject json = null;
        try {
            json = new JSONObject(retMsg);

            //JSONObject temp = json.getJSONObject("courses");
            //当你获得JSONArray 类型的时候，然后要写for循环遍历获取里面每一个Object
            JSONArray data = json.getJSONArray("courses");
            for (int i = 0; i < data.length(); i++) {
                JSONObject value = data.getJSONObject(i);
                //获取到time值
                String classtime = value.getString("time");
                String coursename = value.getString("name");
                String classroom = value.getString("classroom");
                Log.d("debug", classtime);
                Log.d("debug", coursename);
                Log.d("debug", classroom);
                int length = classtime.length();
                Log.d("debug", String.valueOf(length));
                String[] arr = classtime.split(",");
                   /* if(length==11)
                    {
                        SetClassView(arr[0],arr[1],arr[2],coursename,classroom);
                        SetClassView(arr[3],arr[4],arr[5],coursename,classroom);
                    }else if(length==5)
                    {
                        SetClassView(arr[0],arr[1],arr[2],coursename,classroom);
                    }else if(length==18)
                    {
                        SetClassView(arr[0],arr[1],arr[2],coursename,classroom);
                        SetClassView(arr[3],arr[4],arr[5],coursename,classroom);
                        SetClassView(arr[6],arr[7],arr[8],coursename,classroom);
                    }*/
                if (arr.length == 6) {
                    SetClassView(arr[0], arr[1], arr[2], coursename, classroom);
                    SetClassView(arr[3], arr[4], arr[5], coursename, classroom);
                } else if (arr.length == 3) {
                    SetClassView(arr[0], arr[1], arr[2], coursename, classroom);
                } else if (arr.length == 9) {
                    SetClassView(arr[0], arr[1], arr[2], coursename, classroom);
                    SetClassView(arr[3], arr[4], arr[5], coursename, classroom);
                    SetClassView(arr[6], arr[7], arr[8], coursename, classroom);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetClassView(String week, String lessonfrom, String lessonto, String coursename, String classroom) {
        /*int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                R.drawable.main_course3, R.drawable.main_course4,
                R.drawable.main_course5};*/

        int[] background = {0xff00ff00, 0xff00ffff, 0xffff00ff, 0xffff0000, 0xffffff00, 0xffff9800};

        int weekint = Integer.parseInt(week);
        int lsfrom = Integer.parseInt(lessonfrom);
        int lsto = Integer.parseInt(lessonto);
        RelativeLayout table_layout = findViewById(R.id.relative_table_list);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = dm.heightPixels;
        gridHeight = height / 10;

        //设置课表界面，动态生成8 * 10个textview
        for (int i = 1; i <= 10; i++) {

            for (int j = 1; j <= 8; j++) {
                //BorderTextView tx = new BorderTextView(this);
                TextView tx = new TextView(this);
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 43 / 42 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //TextView empty=findViewById(R.id.textview_table_empty);
                //如果是第一列，需要设置课的序号（1 到 10）

                if (j == weekint + 1) {
                    int temp = lsto - lsfrom;//判断课程是两节还是四节连上
                    //两节连上
                    if (temp == 1) {
                        if (i == lsfrom) {

                            rp = new RelativeLayout.LayoutParams(
                                    aveWidth * 43 / 42 + 1,
                                    gridHeight * 2);
                            //tx.setId((i - 1) * 8 + j);
                            rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);//周,在定位tx的右边 需要第二个参数为 定位tx的ID
                            rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                            tx.setText(coursename + "\n@" + classroom);
                            int colorIndex = RandomColor();
                            tx.setBackgroundColor(background[colorIndex]);
                            tx.getBackground().setAlpha(200);
                        }
                    } else if (temp == 3) {
                        //四节连上
                        if (i == lsfrom) {

                            rp = new RelativeLayout.LayoutParams(
                                    aveWidth * 43 / 42 + 1,
                                    gridHeight * 4);
                            rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);
                            rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);//课次,在定位tx的下边 需要第二个参数为 定位tx的ID
                            tx.setText(coursename + "\n@" + classroom);
                            int colorIndex = RandomColor();
                            tx.setBackgroundColor(background[colorIndex]);
                            tx.getBackground().setAlpha(200);
                        }
                    }
                }

                tx.setLayoutParams(rp);
                table_layout.addView(tx);

            }
        }
    }

    private void OnInitTable() {
        //
        //表格初始化,子控件动态布局
        RelativeLayout table_layout = findViewById(R.id.relative_table_list);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = dm.heightPixels;
        gridHeight = height / 10;

        //设置课表界面，动态生成8 * 10个textview
        for (int i = 1; i <= 10; i++) {

            for (int j = 1; j <= 8; j++) {
                BorderTextView tx = new BorderTextView(this);
                tx.setId((i - 1) * 8 + j);//定位
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 43 / 42 + 1,
                        gridHeight);
                tx.setGravity(Gravity.CENTER);
                TextView empty = findViewById(R.id.textview_table_empty);
                //如果是第一列，需要设置课的序号（1 到 10）
                if (j == 1) {
                    // tx.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_table_first_colum));
                    tx.setText(String.valueOf(i));
                    tx.setBackgroundColor(0xffF8F8FF);
                    tx.getBackground().setAlpha(250);
                    rp.width = aveWidth * 3 / 4;
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                } else {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8 + j - 1);//本元素的上边缘和tx的的上边缘对齐 ,第二个参数为tx的ID
                    tx.setText("");
                }

                tx.setLayoutParams(rp);
                table_layout.addView(tx);
            }
        }
    }


}
