package Information;

public class SubjectInfo {
    private String name;
    private String id;
    private String teacher;

    public SubjectInfo(String name, String id, String teacher) {
        this.name = name;
        this.id = id;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTeacher() {
        return teacher;
    }
}
