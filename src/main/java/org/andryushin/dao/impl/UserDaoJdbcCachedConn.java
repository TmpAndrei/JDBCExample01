package org.andryushin.dao.impl;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcCachedConn implements UserDao {
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/testbase";
    public static final String DB_LOGIN = "root";
    public static final String DB_PASSWORD = "S@msung5";

    public static final String SQL_SELECT_ALL = "SELECT id, login, email FROM Users";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM Users WHERE id = ?";
    public static final String SQL_INSERT = "INSERT INTO Users (login, email) VALUES (?,?)";
    public static final String SQL_SELECT_BY_LOGIN = "SELECT id FROM Users WHERE login = ?";
    public static final String SQL_SELECT_BY_EMAIL = "SELECT id FROM Users WHERE email = ?";

    private Connection conn;

    private Connection getConnection() throws DBSystemException {
        Connection _conn;
        try {
            _conn = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD);
            _conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            _conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBSystemException("Can't get connection with URL = '" + JDBC_URL + "'");
        }
        return _conn;
    }


    @Override
    public List<User> selectAll() throws DBSystemException {
        if (conn == null) {
            conn = getConnection();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SQL_SELECT_ALL);
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
            throw new DBSystemException(e.getMessage(), e.getCause());
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(ps);
        }
    }

    @Override
    public int deleteById(int id) throws DBSystemException {
        throw new NotImplementedException();
    }

    @Override
    public int insert(User user) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        throw new NotImplementedException();
    }

    @Override
    public void insert(List<User> users) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        throw new NotImplementedException();
    }

    @Override
    public void shutdown() throws DBSystemException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DBSystemException(e.getMessage(), e.getCause());
        }
        conn = null;
    }
}
