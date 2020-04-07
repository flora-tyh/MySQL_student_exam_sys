import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import Instrument.JDBCUtils;

public class Login {

    public User loginInfo() {
        String loginInfo = null;
        while (true) {
            Scanner sc = new Scanner(System.in);
            loginInfo = sc.next();

            if (Pattern.matches(".*:.*", loginInfo)) {
                String[] loginInfoArr = loginInfo.split(":");
                String id = loginInfoArr[0];
                String password = loginInfoArr[1];
                User user = loginCertification(id, password);
                if (user != null) {
                    return user;
                }
            }
            System.out.println("请输入正确的账号密码");
        }
    }



    public User loginCertification(String id, String password) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT privilege, name FROM account WHERE id = ? AND password = ?;";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, id);
            ptmt.setString(2, password);
            rs = ptmt.executeQuery();

            if (rs.next()) {
                return new User(id, rs.getString("privilege"), rs.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(rs, ptmt, conn);
        }
    }
}
