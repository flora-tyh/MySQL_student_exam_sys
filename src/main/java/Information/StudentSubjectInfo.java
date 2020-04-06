package Information;

public class StudentSubjectInfo {
    private String studentId;
    private String SubjectId;
    private boolean isExam;
    private int score;

    public StudentSubjectInfo() {
    }

    public StudentSubjectInfo(String studentId, String subjectId, boolean isExam, int score) {
        this.studentId = studentId;
        SubjectId = subjectId;
        this.isExam = isExam;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
