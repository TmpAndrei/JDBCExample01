package org.andryushin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReadTest {
    public static void main(String[] args) {
        Properties prop = null;
        try {
            prop = readProperties("datasource.properties");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Driver class = '" + prop.getProperty("driverClass") + "'");
        System.out.println("JDBC URL = '" + prop.getProperty("jdbcUrl") + "'");
        System.out.println("User = '" + prop.getProperty("userName") + "'");
        System.out.println("Password = '" + prop.getProperty("password") + "'");
        System.out.println();
        System.out.println("SQL select all = '" + prop.getProperty("selectAll") + "'");
        System.out.println("SQL delete by id = '" + prop.getProperty("SQL_DELETE_BY_ID") + "'");
        System.out.println("SQL insert = '" + prop.getProperty("SQL_INSERT") + "'");
        System.out.println("SQL select by login = '" + prop.getProperty("SQL_SELECT_BY_LOGIN") + "'");
        System.out.println("SQL select by email = '" + prop.getProperty("SQL_SELECT_BY_EMAIL") + "'");


    }

    private static Properties readProperties(String filename) throws IOException {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
        props.load(stream);
        return props;
    }
}
