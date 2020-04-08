package Information;

public class StudentInfo {
    private String id;
    private String name;
    private String sex;
    private int age;
    private String subject;
    private int score;

    public StudentInfo(String id, String name, String sex, int age, String subject, int score) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.subject = subject;
        this.score = score;
    }

    public StudentInfo(String id, String name, String sex, int age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public StudentInfo(String name, String subject, int score) {
        this.name = name;
        this.subject = subject;
        this.score = score;
    }

    public StudentInfo() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public StudentInfo(String id, String name, int age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }
}
