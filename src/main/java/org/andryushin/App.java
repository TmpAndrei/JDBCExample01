package org.andryushin;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.impl.UserDaoJdbc;
import org.andryushin.exception.DBException;

public class App {
    public static void main(String[] args) throws DBException {
        UserDao dao = new UserDaoJdbc();
        System.out.println("All current users:");
        for (User user : dao.selectAll()){
            System.out.println(" " + user);
        }
    }
}
