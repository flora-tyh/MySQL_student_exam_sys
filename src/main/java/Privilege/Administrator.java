package Privilege;

import Information.StudentInfo;
import Information.SubjectInfo;
import Information.TeacherInfo;

import java.util.List;
import java.util.Scanner;

public class Administrator {
    public void showAdminMenu() {
        System.out.println("您好，超级管理员，请选择你需要进行的操作：\n" +
                "    1. 查询   \n" +
                "      1.1 查询学生信息以及成绩  \n" +
                "        1.1.1 所有学生信息  \n" +
                "        1.1.2 指定学生姓名的信息以及所有课程的成绩  \n" +
                "        1.1.3 指定老师的所有学生及其成绩  \n" +
                "      1.2 查询课程信息  \n" +
                "        1.2.1 所有课程信息  \n" +
                "        1.2.2 指定课程名称的信息  \n" +
                "        1.2.3 指定老师的所有课程信息   \n" +
                "      1.3 查询老师信息  \n" +
                "        1.3.1 所有老师信息  \n" +
                "        1.3.2 指定老师信息  \n" +
                "    2. 新增  \n" +
                "      2.1 新增学生信息  \n" +
                "      2.2 新增课程信息     \n" +
                "    3. 修改    \n" +
                "      3.1 修改指定学生的成绩  \n" +
                "    4. 删除  \n" +
                "      4.1 删除指定学生  \n" +
                "      4.2 删除指定课程  \n" +
                "      4.3 删除指定老师 \n" +
                "    5. 退出登录");
    }

    public boolean getSelectOperate() {
        Scanner sc = new Scanner(System.in);
        String selectOperate = sc.next();
        SQLexecution sqLexecution = new SQLexecution();
        boolean isLogout = false;
        switch (selectOperate) {
            case "1.1.1" :
                List<StudentInfo> studentInfoList = sqLexecution.getStudentInfo(null);
                for(StudentInfo s : studentInfoList) {
                    System.out.println("学号：" + s.getId() + "，姓名： " + s.getName() +
                            ", 年龄： " + s.getAge() + ", 性别： " + s.getSex());
                }
                break;
            case "1.1.2" :
                System.out.println("请输入学生学号：");
                String studentId = sc.next();
                List<StudentInfo> studentInfo = sqLexecution.getStudentScore(studentId);
                for(StudentInfo s : studentInfo) {
                    System.out.println("姓名： " + s.getName() +
                            ", 科目： " + s.getSubject() + ", 成绩： " + s.getScore());
                }
                break;
            case "1.1.3" :
                System.out.println("请输入老师工号（目前只有201，202,203三个老师）：");
                sqLexecution.getTeacherScore();
                break;
            case "1.2.1" :
                List<SubjectInfo> subjectInfoList = sqLexecution.getSubjectInfo(null);
                subjectInfoList.forEach(s ->
                        System.out.println("科目:" + s.getName()  + " 科目编号:" + s.getId() + " 任课老师 :" + s.getTeacher()));
                break;
            case "1.2.2" :
                System.out.println("请输入科目名称（目前只有语文，数学，物理）：");
                String subjectName = sc.next();
                List<SubjectInfo> subjectInfo = sqLexecution.getSubjectInfo(subjectName);
                subjectInfo.forEach(s ->
                        System.out.println("科目:" + s.getName()  + " 科目编号:" + s.getId() + " 任课老师 :" + s.getTeacher()));
                break;
            case "1.2.3" :
                System.out.println("请输入老师工号：");
                String aTeacherId = sc.next();
                TeacherInfo teacherInfo = sqLexecution.getTeacherInfo(aTeacherId);
                List<SubjectInfo> subjectInfoList1 = sqLexecution.getTeacherSubject(aTeacherId);
                System.out.println("老师姓名:" + teacherInfo.getName() + " 工号:" + teacherInfo.getId());
                subjectInfoList1.forEach(s -> System.out.println("科目:" + s.getName()  + " 科目编号:" + s.getId()));
                break;
            case "1.3.1" :
                List<TeacherInfo> teacherInfoList = sqLexecution.getTeacherInfo();
                teacherInfoList.forEach(t -> System.out.println("老师姓名:" + t.getName() + " 工号:" + t.getId()));
                break;
            case "1.3.2" :
                System.out.println("请输入老师工号：");
                String bTeacherId = sc.next();
                sqLexecution.getTeacherInfo(bTeacherId);
                break;
            case "2.1" :
                System.out.println("请输入学生信息(例如：学号：1001，姓名：小明，年龄：18，性别：男)：");
                String newStudentInfo = sc.next();
                StudentInfo studentInfo1 = sqLexecution.addStudentInfo(newStudentInfo);
                System.out.println(String.format("添加学生%s %s成功", studentInfo1.getName(), studentInfo1.getId()));
                break;
            case "2.2" :
                System.out.println("请输入科目信息(科目名称 科目编号)：例如 物理-03");
                String newSubjectInfo = sc.next();
                SubjectInfo subjectInfo1 = sqLexecution.addSubjectInfo(newSubjectInfo);
                System.out.println(String.format("添加课程%s %s成功", subjectInfo1.getName(), subjectInfo1.getId()));
                break;
            case "3.1" :
                System.out.println("请输入修改信息（学生学号-科目编号-成绩）：例如 101-01-20");
                sqLexecution.updateStudentScore();
                break;
            case "4.1":
                System.out.println("请输入您需要删除的学生学号（初始有101,102,103）：");
                int deleteRow = sqLexecution.deleteInfo("student", "学生");
                if (deleteRow > 0){
                    System.out.println("删除成功！");
                } else if (deleteRow == 0) {
                    System.out.println("没有该学生！");
                };
                break;
            case "4.2":
                System.out.println("请输入您需要删除的课程编号（初始有01,02,03：");
                deleteRow = sqLexecution.deleteInfo("subject", "课程");
                if (deleteRow > 0){
                    System.out.println("删除成功！");
                } else if (deleteRow == 0) {
                    System.out.println("没有该课程！");
                };
                break;
            case "4.3":
                System.out.println("请输入您需要删除的老师工号（初始有201,202,203）：");
                deleteRow = sqLexecution.deleteInfo("teacher", "老师");
                if (deleteRow > 0){
                    System.out.println("删除成功！");
                } else if (deleteRow == 0) {
                    System.out.println("没有该老师！");
                };
                break;
            case "5":
                isLogout = true;
                break;
        }
        return isLogout;
    }
}
