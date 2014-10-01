package org.andryushin.dao;

import java.sql.*;

public class JdbcUtils {

    public static void initDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {

        }
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {

        }
    }

    public static void closeQuietly(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {

        }
    }

    public static void closeQuietly(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {

        }
    }

    public static void closeQuietly(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {

        }
    }
}
