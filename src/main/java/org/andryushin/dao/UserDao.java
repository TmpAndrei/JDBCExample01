package org.andryushin.dao;

import org.andryushin.bean.User;
import org.andryushin.exception.DBSystemException;
import org.andryushin.exception.NotUniqueUserEmailException;
import org.andryushin.exception.NotUniqueUserLoginException;

import java.util.List;

public interface UserDao {
    public List<User> selectAll() throws DBSystemException;

    public int deleteById(int id) throws DBSystemException;

    public int insert(User user) throws DBSystemException, NotUniqueUserLoginException, NotUniqueUserEmailException;
}
