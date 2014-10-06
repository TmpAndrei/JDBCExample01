package org.andryushin.dao.impl;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.dataSource.Dbcp;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDaoJdbcDbcp implements UserDao{
    private static final Properties props;
    private static Dbcp datasources;

    static {
        props = JdbcUtils.readPropeties();
    }

    @Override
    public List<User> selectAll() throws DBSystemException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = props.getProperty("selectAll");
        try {
            datasources = Dbcp.getInstance();
            conn = datasources.getConnection();
            ps = conn.prepareStatement(sql);
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
            throw new DBSystemException("Can't execute SQL = '" + sql + "'");
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int deleteById(int id) throws DBSystemException {
        return 0;
    }

    @Override
    public int insert(User user) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        return 0;
    }

    @Override
    public void insert(List<User> users) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {

    }

    @Override
    public void shutdown() throws DBSystemException {
        try {
            datasources.close();
        } catch (SQLException e) {
            throw new DBSystemException("Can't close data source", e.getCause());
        }
        datasources = null;
    }
}
