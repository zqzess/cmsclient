package cms.app.cms.teacher.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import cms.app.R;
import cms.app.RegisterActivity;
import cms.app.cms.model.SelectClassActivity;
import cms.app.net.CMSClient;

public class CheckSelectClassActivity extends AppCompatActivity {

    String itemname = "";
    String classname = "";
    TextView lastTextView = null;
    int flag = 0;
    int flag2 = 0;
    String[] arr = new String[20];
    int counttemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheack_selectclass);
        getSupportActionBar().hide();

        String retMsg = CMSClient.sendAndReceive("queryClass");
        String[] allClasses = retMsg.split("\n");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckSelectClassActivity.this, android.R.layout.simple_list_item_1, allClasses);
        final ListView listView = (ListView) findViewById(R.id.list_teacher_course_view);
        Button btnselectChoose = findViewById(R.id.btn_teacher_course_classchoose);
        listView.setAdapter(adapter);//将构建好的适配器对象传进去
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final int[] selectid = new int[10];


        final int tmp = 0;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tempname;
                int count = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition() + 1;
                counttemp = count;
                int a = (int) id;
                String b = a + ",";
                Log.d("selectcourse", b);
                int lengthtmp = selectid.length;
                boolean SFCZ = false;
                String length = lengthtmp + ",";
                Log.d("selectcourse", length);
                TextView textView = (TextView) view;
                if (a == tmp && flag != 0) {
                    textView.setBackgroundColor(Color.WHITE);
                    //view.invalidate();
                    flag--;
                    Log.d("selectcourse", "flag:" + flag);
                } else if (a == tmp && flag == 0) {
                    textView.setBackgroundColor(Color.YELLOW);
                    tempname = ((TextView) view).getText().toString();
                    String temp = String.valueOf(a);
                    //arr[0]=temp;
                    for (int i = 0; i < count; i++) {
                        if (arr[i] == null || arr[i] == "") {
                            arr[i] = temp;
                            break;
                        }
                    }
                    flag++;
                    Log.d("selectcourse", "flag:" + flag);
                } else if (a != tmp) {
                    for (int i = 0; i < count; i++) {

                        String temp = String.valueOf(a);
                        if (temp.equals(arr[i])) {
                            arr[i] = null;
                            textView.setBackgroundColor(Color.WHITE);
                            view.invalidate();
                            tempname = ((TextView) view).getText().toString();
                            SFCZ = true;
                        }
                    }
                    if (!SFCZ) {
                        for (int i = 0; i < count; i++) {

                            String temp = String.valueOf(a);
                            if (arr[i] == null) {
                                arr[i] = temp;
                                textView.setBackgroundColor(Color.YELLOW);
                                tempname = ((TextView) view).getText().toString();
                                SFCZ = false;
                                break;
                            }
                        }
                    }
                }
                tempname = ((TextView) view).getText().toString();
                Toast.makeText(CheckSelectClassActivity.this, tempname, Toast.LENGTH_SHORT).show();//系统提示
            }
        });


        btnselectChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (int i = 0; i < counttemp; i++) {
                    if (arr[i] != null) {
                        int counttmp = arr.length;
                        count = counttmp;
                        int index = Integer.parseInt(arr[i]);
                        View view = listView.getChildAt(index);
                        classname = ((TextView) view).getText().toString() + ",";
                        itemname = itemname + classname;
                    }
                }
                //int a=listView.getFirstVisiblePosition();
                //View view=listView.getChildAt(5-listView.getFirstVisiblePosition());

                if (!itemname.isEmpty()) {
                    itemname = itemname.substring(0, itemname.length() - 1);
                    Intent intent = new Intent(CheckSelectClassActivity.this, TeacherCourseDetailActivity.class);
                    intent.putExtra("name", itemname);
                    intent.putExtra("count", count);
                    setResult(RESULT_OK, intent);
                    finish();//关闭当前窗口
                    //startActivity(intent);
                } else {
                    Log.d("cms.debug", "no class selected");
                    AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(CheckSelectClassActivity.this);
                    dlgBuilder.setTitle("错误");
                    dlgBuilder.setMessage("请选择班级");
                    dlgBuilder.show();
                }
            }
        });
    }
}
