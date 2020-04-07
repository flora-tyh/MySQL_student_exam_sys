import Privilege.Administrator;
import Privilege.Student;
import Privilege.Teacher;

public class Application {
  @SuppressWarnings("InfiniteLoopStatement")
  public static void main(String[] args) {
    String privilege = null;
    while (true) {
      boolean isLogout = false;
      showMainPage();
      privilege = login();
      while (!isLogout) {
        isLogout = getPrivilege(privilege);
      }
    }
  }

  public static String login() {
    Login login = new Login();
    User user = login.loginInfo();
    System.out.println(user.getName() + "登录成功");
    return user.getPrivilege();
  }
  public static void showMainPage() {
//    账号系统中账号为主键，这里改为了输入账号密码登录
    System.out.println("您好，欢迎登陆学生考试系统，请输入您的账号和密码(账号:密码)：\n" +
                        "例如：101:123\n" +
                        "PS:目前只有'101:123';'201:123';'301:123'三组可用");
  }

  public static boolean getPrivilege(String privilege) {
    boolean isLogout = false;
    switch (privilege) {
      case "学生" :
        Student student = new Student();
        System.out.println("学生系统暂未开放，敬请期待");
        isLogout = true;
        break;
      case "老师" :
        Teacher teacher = new Teacher();
        System.out.println("老师系统暂未开放，敬请期待");
        isLogout = true;
        break;
      case "超级管理员" :
        Administrator administrator = new Administrator();
        administrator.showAdminMenu();
        isLogout = administrator.getSelectOperate();
        break;
    }
    return isLogout;
  }
}
