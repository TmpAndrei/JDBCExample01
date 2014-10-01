package org.andryushin.dao;

import org.andryushin.bean.User;

import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    @Override
    public List<User> selectAll() throws SQLException {
        return null;
    }

    @Override
    public int deleteById(int id) throws SQLException {
        return 0;
    }

    @Override
    public void insert(User user) throws SQLException, NotUniqueUserLoginException, NotUniqueUserEmailException {

    }

    @Override
    public void shutdown() {

    }
}
