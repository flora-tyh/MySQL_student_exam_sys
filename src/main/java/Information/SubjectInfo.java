package Information;

public class SubjectInfo {
    private String name;
    private String id;
    private String teacher;

    public SubjectInfo() {
    }

    public SubjectInfo(String name, String id, String teacher) {
        this.name = name;
        this.id = id;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "科目:" + name  + " 科目编号:" + id + " 任课老师 :" + teacher;
    }
}
