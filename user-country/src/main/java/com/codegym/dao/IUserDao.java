package com.codegym.dao;

import com.codegym.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    public void insertUser(User user);
    public User selectUser(int id);
    public boolean deleteUser(int id) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
    public List<User> searchUser(int name);
    public List<User> selectAllUserPage(int offset, int noOfRecords,int id_country,String nameSearch);
    public List<User> selectAllUser();
}
