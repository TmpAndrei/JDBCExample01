package org.andryushin.dao;

import java.sql.*;

public class JdbcUtils {
    private static boolean initialized;

    public static void initDriver(String driverClass) {
        if (!initialized) {
            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your MySQL JDBC Driver?");
                e.printStackTrace();
                throw new RuntimeException("Can't initialize driver: '" + driverClass + "'");
            }
            initialized = true;
        }
    }

    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeQuietly(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeQuietly(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }
}
