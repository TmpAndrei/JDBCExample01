package org.andryushin;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.impl.UserDaoJdbc;
import org.andryushin.exception.DBException;

public class App {
    public static void main(String[] args) throws DBException {
        UserDao dao = new UserDaoJdbc();

        User user1 = new User("John", "john@mail.com");
        user1.setId(dao.insert(user1));
        System.out.println(user1);

        dao.deleteById(user1.getId());

        System.out.println("All current users:");
        for (User user : dao.selectAll()){
            System.out.println(" " + user);
        }
    }
}
