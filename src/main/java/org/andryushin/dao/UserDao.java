package org.andryushin.dao;

import org.andryushin.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public List<User> selectAll() throws SQLException;

    public int deleteById(int id) throws SQLException;

    public void insert(User user) throws SQLException, NotUniqueUserLoginException, NotUniqueUserEmailException;

    public void shutdown();
}
