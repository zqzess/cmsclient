package cms.app.net;

import cms.app.cms.model.CourseInfo;

public class QueryStudentAllCoursesResult {
    boolean success;
    String msg;
    CourseInfo[] courses;

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public CourseInfo[] getCourses() {
        return courses;
    }
}
