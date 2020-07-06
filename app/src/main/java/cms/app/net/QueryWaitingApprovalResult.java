package cms.app.net;

import java.util.ArrayList;

public class QueryWaitingApprovalResult {
    boolean success;
    String msg;
    ArrayList<User> users;

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
