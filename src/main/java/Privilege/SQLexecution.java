package Privilege;

import Information.StudentInfo;
import Information.SubjectInfo;
import Information.TeacherInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Instrument.JDBCUtils;

public class SQLexecution {

    public List<StudentInfo> getStudentInfo(String studentId) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        List<StudentInfo> studentInfoList = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();
            if (studentId != null) {
                String sql = "SELECT id, name, age, sex FROM student WHERE id = ?";
                ptmt = conn.prepareStatement(sql);
                ptmt.setString(1, studentId);
            } else {
                String sql = "SELECT id, name, age, sex FROM student";
                ptmt = conn.prepareStatement(sql);
            }
            rs = ptmt.executeQuery();

            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo(rs.getString("id"), rs.getString("name"), rs.getInt("age"), rs.getString("sex"));
                studentInfoList.add(studentInfo);
            }
            return studentInfoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public List<StudentInfo> getStudentScore(String studentId) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        List<StudentInfo> studentInfoList = new ArrayList<>();

        String sql = "SELECT t2.name AS subject,\n" +
                "t1.score AS score,\n" +
                "t3.name AS name\n" +
                "FROM student_registration AS t1\n" +
                "INNER JOIN subject AS t2\n" +
                "\tON t1.subject_id = t2.id\n" +
                "RIGHT JOIN student AS t3\n" +
                "\tON t1.student_id = t3.id\n" +
                "WHERE t1.student_id = ?\n" +
                "AND t1.exam = 1;";
        try {
            conn = JDBCUtils.getConnection();
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, studentId);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo(rs.getString("name"), rs.getString("subject"), rs.getInt("score"));
                studentInfoList.add(studentInfo);
            }
            return studentInfoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public void getTeacherScore() {

        Scanner sc = new Scanner(System.in);
        String teacherId = sc.next();
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        TeacherInfo teacherInfo = getTeacherInfo(teacherId);
        System.out.println("教师姓名：" + teacherInfo.getName() + " 教师编号：" + teacherInfo.getId());

        String sql = "SELECT t1.name stu_name,\n" +
                "\tt1.id stu_id,\n" +
                "    t2.score,\n" +
                "    t3.name AS subject\n" +
                "FROM student AS t1\n" +
                "INNER JOIN\n" +
                "\tstudent_registration AS t2\n" +
                "ON t1.id = t2.student_id\n" +
                "INNER JOIN\n" +
                "    subject AS t3\n" +
                "ON t2.subject_id = t3.id    \n" +
                "WHERE t2.subject_id = (\n" +
                "\tSELECT subject_id\n" +
                "    FROM teacher_schedule\n" +
                "    WHERE teacher_id = ?)\n";
        try {
            conn = JDBCUtils.getConnection();
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, teacherId);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                System.out.println(String.format("科目：%s 学生：%s 学号：%s 分数：%d",
                        rs.getString("subject"), rs.getString("stu_name"), rs.getString("stu_id"), rs.getInt("score")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public List<SubjectInfo> getSubjectInfo(String subjectName) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        List<SubjectInfo> subjectInfoList = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();
            if (subjectName != null) {
                String sql = "SELECT t1.name AS sub_name,\n" +
                        "\tt1.id AS sub_id,\n" +
                        "    t2.name AS teac_name\n" +
                        "FROM subject AS t1\n" +
                        "LEFT JOIN teacher_schedule AS t3\n" +
                        "ON t1.id = t3.subject_id\n" +
                        "LEFT JOIN teacher AS t2\n" +
                        "ON t3.teacher_id = t2.id\n" +
                        "WHERE t1.name = ?;";
                ptmt = conn.prepareStatement(sql);
                ptmt.setString(1, subjectName);
            } else {
                String sql = "SELECT t1.name AS sub_name,\n" +
                        "\tt1.id AS sub_id,\n" +
                        "    t2.name AS teac_name\n" +
                        "FROM subject AS t1\n" +
                        "LEFT JOIN teacher_schedule AS t3\n" +
                        "ON t1.id = t3.subject_id\n" +
                        "LEFT JOIN teacher AS t2\n" +
                        "ON t3.teacher_id = t2.id;";
                ptmt = conn.prepareStatement(sql);
            }
            rs = ptmt.executeQuery();
            while (rs.next()) {
                SubjectInfo subjectInfo = new SubjectInfo(rs.getString("sub_name"), rs.getString("sub_id"), rs.getString("teac_name"));
                subjectInfoList.add(subjectInfo);
            }
            return subjectInfoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public List<SubjectInfo> getTeacherSubject(String teacherId) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        TeacherInfo teacherInfo = getTeacherInfo(teacherId);
        List<SubjectInfo> subjectInfoList = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT t1.id AS sub_id,\n" +
                    "\tt1.name AS sub_name\n" +
                    "FROM subject AS t1\n" +
                    "INNER JOIN\n" +
                    "\tteacher_schedule AS t2\n" +
                    "ON t1.id = t2.subject_id\n" +
                    "WHERE t2.teacher_id  = ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, teacherId);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                SubjectInfo subjectInfo = new SubjectInfo(rs.getString("sub_name"), rs.getString("sub_id"), teacherInfo.getName());
                subjectInfoList.add(subjectInfo);
            }
            return subjectInfoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public List<TeacherInfo> getTeacherInfo() {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        List<TeacherInfo> teacherInfoList = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT id, name FROM teacher";
            ptmt = conn.prepareStatement(sql);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                TeacherInfo teacherInfo = new TeacherInfo(rs.getString("name"), rs.getString("id"));
                teacherInfoList.add(teacherInfo);
            }
            return teacherInfoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public TeacherInfo getTeacherInfo(String teacherId) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        TeacherInfo teacherInfo = new TeacherInfo();

        try {
            conn = JDBCUtils.getConnection();
                String sql = "SELECT id, name FROM teacher WHERE id = ?";
                ptmt = conn.prepareStatement(sql);
                ptmt.setString(1, teacherId);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                teacherInfo = new TeacherInfo(rs.getString("name"), rs.getString("id"));
            }
            return teacherInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }

    public StudentInfo addStudentInfo(String newStudentInfo) {
        String[] stringArrayList = newStudentInfo.split("：");
        List<List<String>> infoSplit = Arrays.stream(stringArrayList)
                .map(s -> Arrays.asList(s.split("，")))
                .collect(Collectors.toList());
        StudentInfo studentInfo = new StudentInfo(infoSplit.get(1).get(0), infoSplit.get(2).get(0),
                Integer.parseInt(infoSplit.get(3).get(0)), Character.toString(stringArrayList[4].charAt(0)));

        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO student (id, name, sex, age) VALUES (?, ?, ?, ?);";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, studentInfo.getId());
            ptmt.setString(2, studentInfo.getName());
            ptmt.setString(3, studentInfo.getSex());
            ptmt.setInt(4, studentInfo.getAge());
            ptmt.executeUpdate();
            return studentInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(ptmt, conn);
        }
    }

    public SubjectInfo addSubjectInfo(String newSubjectInfo) {
        String[] strings = newSubjectInfo.split("-");
        SubjectInfo subjectInfo = new SubjectInfo(strings[0], strings[1], null);
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO subject (id, name) VALUES (?, ?);";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, subjectInfo.getId());
            ptmt.setString(2, subjectInfo.getName());
            ptmt.executeUpdate();
            return subjectInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(ptmt, conn);
        }
    }

    public void updateStudentScore() {
        Scanner sc = new Scanner(System.in);
        String updateInfo = sc.next();
        String[] updateInfos = updateInfo.split("-");
        Connection conn = null;
        PreparedStatement ptmt = null;

        String sql = "UPDATE student_registration " +
                "SET score = ? " +
                "WHERE subject_id = ? and student_id = ?;";
        try {
            conn = JDBCUtils.getConnection();
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, Integer.parseInt(updateInfos[2]));
            ptmt.setString(2, updateInfos[1]);
            ptmt.setString(3, updateInfos[0]);
            ptmt.executeUpdate();
            System.out.println("修改成功");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(ptmt, conn);
        }
    }

    public int deleteInfo(String type, String typeName) {
        Scanner sc = new Scanner(System.in);
        String id = sc.next();

        System.out.println("删除" + typeName + "之后，该" + typeName + "信息将不能恢复，是否要继续删除？\n" +
                "1.是 \n2.否");
        String confirmDelete = sc.next();

        if ("1".equals(confirmDelete)) {
            Connection conn = null;
            PreparedStatement ptmt = null;

            String sql = "DELETE FROM " + type + " WHERE id = ?;";
            try {
                conn = JDBCUtils.getConnection();
                ptmt = conn.prepareStatement(sql);
                ptmt.setString(1, id);
                int i = ptmt.executeUpdate();
                return i;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                JDBCUtils.close(ptmt, conn);
            }
        }
        return -1;
    }
}
