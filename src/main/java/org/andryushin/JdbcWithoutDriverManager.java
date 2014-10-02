package org.andryushin;

import com.mysql.jdbc.Driver;
import org.andryushin.bean.User;

import java.sql.*;
import java.util.Properties;

public class JdbcWithoutDriverManager {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "S@msung5");
        Connection conn = driver.connect("jdbc:mysql://localhost:3306/testbase", info);
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement("SELECT id, login, email FROM Users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            User user = new User(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setEmail(rs.getString("email"));
            System.out.println(user);
        }
        conn.commit();
        rs.close();
        ps.close();
        conn.close();
    }
}
