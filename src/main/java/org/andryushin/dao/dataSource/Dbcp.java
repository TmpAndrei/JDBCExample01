package org.andryushin.dao.dataSource;

import org.andryushin.dao.impl.JdbcUtils;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Dbcp {
    private static Dbcp datasources;
    private BasicDataSource ds;
    private static Properties props;

    private Dbcp() {
        props = JdbcUtils.readPropeties();
        ds = new BasicDataSource();
        ds.setDriverClassName(props.getProperty("driverClass"));
        ds.setUrl(props.getProperty("jdbcUrl"));
        ds.setUsername(props.getProperty("userName"));
        ds.setPassword(props.getProperty("password"));

        // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(new Integer(props.getProperty("minldle")));
        ds.setMaxIdle(new Integer(props.getProperty("maxldle")));
        ds.setMaxOpenPreparedStatements(new Integer(props.getProperty("maxOpenPreparedStatements")));
    }

    public static Dbcp getInstance() {
        if (datasources == null) {
            datasources = new Dbcp();
        }
        return datasources;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = this.ds.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);
        return connection;
    }

    public void close() throws SQLException {
        ds.close();
    }
}
