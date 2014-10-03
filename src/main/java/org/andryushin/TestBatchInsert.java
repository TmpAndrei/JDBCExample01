package org.andryushin;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.impl.UserDaoJdbc;
import org.andryushin.exception.DBException;
import org.andryushin.exception.DBSystemException;

import java.util.LinkedList;
import java.util.List;

public class TestBatchInsert {
    public static void main(String[] args) throws DBException {
        UserDao dao = new UserDaoJdbc();

        showAllUsers(dao);

        User user1 = new User("John", "john@hotmail.com");
        User user2 = new User("Tina", "tina@mail.com");
        User user3 = new User("Kelly", "kelly@g.com");
        List<User> users = new LinkedList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        dao.insert(users);

        showAllUsers(dao);
    }

    private static void showAllUsers(UserDao dao) throws DBSystemException {
        System.out.println("All current users:");
        for (User user : dao.selectAll()) {
            System.out.println(" " + user);
        }
    }
}
