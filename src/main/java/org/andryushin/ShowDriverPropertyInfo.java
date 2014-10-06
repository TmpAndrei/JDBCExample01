package org.andryushin;



import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Arrays;

public class ShowDriverPropertyInfo {
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/testbase";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        Driver driver = DriverManager.getDriver(JDBC_URL);
        DriverPropertyInfo[] propertyInfos = driver.getPropertyInfo(JDBC_URL, null);
        int i;
        for (i=0; i<propertyInfos.length; i++){
            String name = propertyInfos[i].name;
            boolean required = propertyInfos[i].required;
            String  value = propertyInfos[i].value;
            String description = propertyInfos[i].description;
            String[] choices = propertyInfos[i].choices;
            System.out.println("Property : " + name + "\nRequired : " + required + "\nValue : "
                    + value + "\nDescription : " + description + "\nChoices : "
                    + (choices!=null? Arrays.asList(choices):null) + "\n");
        }
        System.out.println("Properties = " + i);
    }
}
