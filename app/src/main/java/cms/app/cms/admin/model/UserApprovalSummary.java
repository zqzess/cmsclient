package cms.app.cms.admin.model;

public class UserApprovalSummary {
    int type;
    String userid;
    String username;

    public UserApprovalSummary(int type, String userid, String username) {
        this.type = type;
        this.userid = userid;
        this.username = username;
    }
}
