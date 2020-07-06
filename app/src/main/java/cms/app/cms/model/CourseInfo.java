package cms.app.cms.model;


public class CourseInfo {
    public String id;
    public String name;
    public int type;
    public float credit;
    public String book;
    public String teacherId;
    public String teacherName;
    public String classroom;
    public String time;

    public CourseInfo(String id, String name, int type, float credit, String book, String teacherId, String teacherName, String classroom, String time) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.credit = credit;
        this.book = book;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classroom = classroom;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public float getCredit() {
        return credit;
    }

    public String getBook() {
        return book;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getTime() {
        return time;
    }
}
