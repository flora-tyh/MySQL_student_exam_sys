package Information;

public class TeacherInfo {
    private String name;
    private String id;

    public TeacherInfo(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "老师姓名：" + name +
                " 老师编号：" + id ;
    }
}
