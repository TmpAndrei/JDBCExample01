package org.andryushin.dao.impl;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/testbase";
    public static final String DB_LOGIN = "root";
    public static final String DB_PASSWORD = "S@msung5";

    public static final String SQL_SELECT_ALL = "SELECT id, login, email FROM Users";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM Users WHERE id = ?";
    public static final String SQL_INSERT = "INSERT INTO Users (login, email) VALUES (?,?)";
    public static final String SQL_SELECT_BY_LOGIN = "SELECT id FROM Users WHERE login = ?";
    public static final String SQL_SELECT_BY_EMAIL = "SELECT id FROM Users WHERE email = ?";

    static {
        JdbcUtils.initDriver(DRIVER_CLASS_NAME);
    }

    private Connection getConnection() throws DBSystemException {
        Connection conn;
        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException e) {
            throw new DBSystemException("Can't get connection with URL = '" + JDBC_URL + "'");
        }
        return conn;
    }

    public List<User> selectAll() throws DBSystemException {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
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
            throw new DBSystemException("Can't execute SQL = '" + SQL_SELECT_ALL + "'");
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int deleteById(int id) throws DBSystemException {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(SQL_DELETE_BY_ID);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL = '" + SQL_DELETE_BY_ID + id);
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int insert(User user) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            if (existWithLogin(conn, user.getLogin())) {
                throw new NotUniqueUserLoginException("Login '" + user.getLogin() + "'");
            }
            if (existWithEmail(conn, user.getEmail())) {
                throw new NotUniqueUserEmailException("Email '" + user.getEmail() + "'");
            }
            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getEmail());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) throw new DBSystemException("Creating user failed, no rows affected.");

            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                conn.commit();
                return generatedKeys.getInt(1);
            }
            throw new DBSystemException("Can't get generated key");

        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute sql = '" + SQL_INSERT + "' " + user, e);
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public void insert(List<User> users) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(SQL_INSERT);
            for (User user : users){
//                if (existWithLogin(conn, user.getLogin())) {
//                    throw new NotUniqueUserLoginException("Login '" + user.getLogin() + "'");
//                }
//                if (existWithEmail(conn, user.getEmail())) {
//                    throw new NotUniqueUserEmailException("Email '" + user.getEmail() + "'");
//                }
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getEmail());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute sql = '" + SQL_INSERT + "' " + users, e);
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    private boolean existWithEmail(Connection conn, String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_EMAIL);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private boolean existWithLogin(Connection conn, String login) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_LOGIN);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public void shutdown() throws DBSystemException {

    }
}
