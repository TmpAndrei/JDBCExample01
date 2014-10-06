package org.andryushin.dao.impl;

import org.andryushin.bean.User;
import org.andryushin.dao.UserDao;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;

import java.util.List;

public class UserDaoJdbcDbcp implements UserDao{
    @Override
    public List<User> selectAll() throws DBSystemException {

        return null;
    }

    @Override
    public int deleteById(int id) throws DBSystemException {
        return 0;
    }

    @Override
    public int insert(User user) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {
        return 0;
    }

    @Override
    public void insert(List<User> users) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException {

    }

    @Override
    public void shutdown() throws DBSystemException {

    }
}
