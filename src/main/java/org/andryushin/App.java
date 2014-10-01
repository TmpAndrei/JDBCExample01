package org.andryushin;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.impl.UserDaoJdbc;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        UserDao dao = new UserDaoJdbc();
        for (User user : dao.selectAll()){
            System.out.println(user);
        }
    }
}
