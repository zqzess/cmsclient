package cms.app.cms.admin.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.dialog.myDialog.MyAlertInputDialog;

import cms.app.R;
import cms.app.net.CMSClient;

/**
 * @author ZQZESS
 * @date 2020/6/25-18:48
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class AdminManagerClassFragment extends Fragment {

    String itemname;
    TextView lastTextView = null;//listView
    ListView listView;
    LinearLayout layoutbtnAdd;
    LinearLayout layoutbtnRenew;
    EditText editSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acticity_admin_manager_class, container, false);
        listView = (ListView) view.findViewById(R.id.list_admin_manager_class_view);
        layoutbtnAdd = (LinearLayout) view.findViewById(R.id.layoutbtn_admin_add);
        layoutbtnRenew = (LinearLayout) view.findViewById(R.id.layoutbtn_admin_renew);
        editSearch = (EditText) view.findViewById(R.id.edit_admin_manager_class_searchitem);


        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = editSearch.getText().toString();
                if (tmp.isEmpty()) {
                    String sendMsg = "queryClass";
                    GetAndSearchEvent(sendMsg);
                    //editSearch.setHint("请输入查询内容");
                } else {
                    String sendMsg = "queryClassSearch\n" + tmp;
                    GetAndSearchEvent(sendMsg);
                }

            }
        });

        layoutbtnAdd.setOnClickListener(new View.OnClickListener() {
            //添加课程
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(AdminManagerClassFragment.this.getActivity()).builder()
                        .setTitle("请输入")
                        .setEditText("请输入要创建的课程名称");
                myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String classnameTmp = myAlertInputDialog.getResult();
                        Log.d("classnameTmp:", classnameTmp);
                        CMSClient.sendAndReceive("createClass\n" + classnameTmp);
                        myAlertInputDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //return;
                        myAlertInputDialog.dismiss();
                    }
                });
                myAlertInputDialog.show();


            }
        });


        layoutbtnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMsg = "queryClass";
                GetAndSearchEvent(sendMsg);
                /*String retMsg= CMSClient.sendAndReceive("queryClass");
                String[] allClasses=retMsg.split("\n");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminManagerClassFragment.this.getActivity(), android.R.layout.simple_list_item_1, allClasses);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemname = ((TextView)view).getText().toString();
                        Intent intent=new Intent(AdminManagerClassFragment.this.getActivity(), AdminManagerClassDetailActivity.class);
                        intent.putExtra("classname",itemname);
                        startActivity(intent);
                    }
                });
*/
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String tmp = editSearch.getText().toString();
        if (tmp.isEmpty()) {
            String sendMsg = "queryClass";
            GetAndSearchEvent(sendMsg);
        } else {
            String sendMsg = "queryClassSearch\n" + tmp;
            GetAndSearchEvent(sendMsg);
        }

    }

    private void GetAndSearchEvent(String sendMsg) {
        String retMsg = CMSClient.sendAndReceive(sendMsg);
        String[] allClasses = retMsg.split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminManagerClassFragment.this.getActivity(), android.R.layout.simple_list_item_1, allClasses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemname = ((TextView) view).getText().toString();
                Intent intent = new Intent(AdminManagerClassFragment.this.getActivity(), AdminManagerClassDetailActivity.class);
                intent.putExtra("classname", itemname);
                startActivity(intent);
            }
        });
    }
}
