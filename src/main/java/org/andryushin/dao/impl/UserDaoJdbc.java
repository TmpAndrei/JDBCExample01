package org.andryushin.dao.impl;

import org.andryushin.bean.User;
import org.andryushin.dao.JdbcUtils;
import org.andryushin.dao.UserDao;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    static {
        JdbcUtils.initDriver("com.mysql.jdbc.Driver");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/testbase","root","S@msung5");
    }

    public List<User> selectAll_() throws SQLException {
        Connection conn = getConnection();
        System.out.println(conn);

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("SELECT id, login, email FROM User");
            rs = ps.executeQuery();
            ArrayList<User> result = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String email = rs.getString("email");
                User user = new User(id);
                user.setLogin(login);
                user.setEmail(email);
                result.add(user);
            }
            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw e;
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public List<User> selectAll() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, login, email FROM User");
            ArrayList<User> result = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String email = rs.getString("email");
                User user = new User(id);
                user.setLogin(login);
                user.setEmail(email);
                result.add(user);
            }
            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw e;
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(stmt);
            JdbcUtils.closeQuietly(conn);
        }
    }


    @Override
    public int deleteById(int id) throws SQLException {
        return 0;
    }

    @Override
    public void insert(User user) throws SQLException, NotUniqueUserLoginException, NotUniqueUserEmailException {

    }

    @Override
    public void shutdown() {

    }
}
