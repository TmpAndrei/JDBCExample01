package org.andryushin;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.dao.impl.*;
import org.andryushin.exception.DBSystemException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaoImplSpeedTest {

    public static final int COUNT = 100;

    public static void main(String[] args) throws DBSystemException {
//        UserDao dao = new UserDaoJdbc();
//        UserDao dao = new UserDaoJdbcProxol();
//        UserDao dao = new UserDaoJdbcC3P0();
        UserDao dao = new UserDaoJdbcCachedConn();
//        UserDao dao = new UserDaoJdbcCachedConnPs();

        List<Long> time = new ArrayList<>();

        for (int k = 0; k < COUNT; k++) {
            long tic = System.nanoTime();

            List<User> userList = dao.selectAll();
            long toc = System.nanoTime();
            long dTime = (toc - tic) / 1000;
            System.out.println(dTime);
            time.add(dTime);
        }

        Collections.sort(time);
        long sum = 0;
        for (int k = COUNT / 10; k < COUNT - COUNT/10; k++){
            sum += time.get(k);
        }
        System.out.println("avg = " + 10 * sum / (COUNT * 8));

        dao.shutdown();
    }
}
