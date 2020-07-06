package cms.app.cms.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cms.app.R;
import cms.app.RegisterActivity;
import cms.app.net.CMSClient;

public class SelectClassActivity extends AppCompatActivity {

    //private String[] data ={"软件技术1701","软件技术1801","计算机网络1701","计算机网络1801","电子信息1701","电子信息1801","电子工程1701","电子工程1801"};
    String itemname;
    TextView lastTextView = null;//listView

    /* String[] netQueryClass(){
         String[] ret=new String[]{};
         Socket sock=null;
         DataInputStream in=null;
         DataOutputStream out=null;
         String sendMsg="queryClass";
         AlertDialog.Builder dlg=new AlertDialog.Builder(SelectClassActivity.this);
         try{
             sock=new Socket("192.168.31.238",20000);
             //NetUtil.displaySocketAdddress(sock);
             out=new DataOutputStream(sock.getOutputStream());
             out.writeUTF(sendMsg);
             in = new DataInputStream(sock.getInputStream());
             String retMsg=in.readUTF();
             //AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
             //dlg.setMessage(retMsg);
             //dlg.show();
             ret=retMsg.split("\n");
         }catch (IOException ex){
             Log.d("debug",ex.toString());
         }finally {
             try {
                 if(in!=null)in.close();
                 if(out!=null)out.close();
                 if(sock!=null)sock.close();
             }catch(IOException ex){
                 Log.d("debug",ex.toString());
             }
         }
         return  ret;
     };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectclass);
        getSupportActionBar().hide();
        //String[] data=netQueryClass();
        String retMsg = CMSClient.sendAndReceive("queryClass");
        String[] allClasses = retMsg.split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectClassActivity.this, android.R.layout.simple_list_item_1, allClasses);
        final ListView listView = (ListView) findViewById(R.id.list_selectclass_view);
        Button btnselectChoose = findViewById(R.id.btn_selectclass_choose);
        listView.setAdapter(adapter);//将构建好的适配器对象传进去
        //获取选中item的值


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemname = ((TextView) view).getText().toString();
                //选中变色
                /*TextView textView=(TextView) view;
                textView.setBackgroundColor(Color.YELLOW);
                if(lastTextView!=null)
                {
                    lastTextView.setBackgroundColor(Color.WHITE);
                }
                lastTextView=textView;*/
                Toast.makeText(SelectClassActivity.this, itemname, Toast.LENGTH_SHORT).show();//系统提示
            }
        });
        //传回上一个窗体
        btnselectChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemname != null) {
                    Intent intent = new Intent(SelectClassActivity.this, RegisterActivity.class);
                    intent.putExtra("name", itemname);
                    setResult(RESULT_OK, intent);
                    finish();//关闭当前窗口
                    //startActivity(intent);
                } else {
                    Log.d("cms.debug", "no class selected");
                    AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SelectClassActivity.this);
                    dlgBuilder.setTitle("错误");
                    dlgBuilder.setMessage("请选择班级");
                    dlgBuilder.show();
                }

            }
        });
    }
}
