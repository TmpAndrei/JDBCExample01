package org.andryushin.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDaoJdbcC3P0 implements UserDao{
    //todo: add Transaction
    //todo: realize simple UserDaoImpl with poll in properties
    private static Properties props;
    private ComboPooledDataSource cpds;
    private static boolean initialized;
    private static Logger log = Logger.getLogger(UserDaoJdbcC3P0.class);

    public UserDaoJdbcC3P0() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    private void init() {
        log.info("Reading datasource.properties fromm classpath");
        props = JdbcUtils.readPropeties();

        cpds = new ComboPooledDataSource();
//        cpds.setDriverClass(props.getProperty("driverClass"));
        cpds.setJdbcUrl(props.getProperty("jdbcUrl"));
        cpds.setUser(props.getProperty("userName"));
        cpds.setPassword(props.getProperty("password"));

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setInitialPoolSize(new Integer(props.getProperty("initialPoolSize")));
        cpds.setAcquireIncrement(new Integer(props.getProperty("acquireIncrement")));
        cpds.setMaxPoolSize(new Integer(props.getProperty("maxPoolSize")));
        cpds.setMinPoolSize(new Integer(props.getProperty("minPoolSize")));
        cpds.setMaxStatements(new Integer(props.getProperty("maxStatements")));

//        try {
//            cpds.getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//            cpds.getConnection().setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }

    @Override
    public List<User> selectAll() throws DBSystemException {
        String sql = props.getProperty("selectAll");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
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
//            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL = '" + sql + "'");
        } finally {
            JdbcUtils.closeQuietly(rs);
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
//        try {
//            DataSources.destroy(cpds);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        cpds.close();
        cpds = null;
        initialized = false;
    }
}
