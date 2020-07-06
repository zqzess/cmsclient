package cms.app.net;

import android.util.Log;

import com.hb.dialog.dialog.LoadingDialog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import cms.app.MainActivity;

public class CMSClient {
    static String server_addr = "175.24.4.102";
    //static  String server_addr="106.52.47.41";
    //static  String server_addr="192.168.232.233";
    //static  String server_addr="10.10.105.32";
    static int server_port = 20000;

    public static String sendAndReceive(String msg) {
        String retMsg = "";
        Socket sock = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            sock = new Socket(server_addr, server_port);
            //NetUtil.displaySocketAdddress(sock);
            out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF(msg);
            in = new DataInputStream(sock.getInputStream());
            retMsg = in.readUTF();
            //String[] arr = retMsg.split("\n");

        } catch (IOException ex) {
            Log.d("debug", ex.toString());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (sock != null) sock.close();
            } catch (IOException ex) {
                Log.d("debug", ex.toString());
            }
        }
        return retMsg;
    }

}
