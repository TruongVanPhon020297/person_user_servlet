package com.codegym.dao;

import com.codegym.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao{
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo_country?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";
    private int noOfRecords;

    private static final String INSERT_USERS_SQL = "INSERT INTO person (person_name, email, id_country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM person WHERE id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM person AS u INNER JOIN country AS c ON u.id_country = c.id  =  WHERE c.name_country = ?";
    private static final String DELETE_USERS_SQL = "DELETE FROM person WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE person SET name = ?,email= ?, id_country =? WHERE id = ?;";
    private static final String SEARCH_USERS_SQL = "SELECT * FROM person WHERE name = ?;";
    private static final String SELECT_ALL_USERS_PAGE = "SELECT SQL_CALC_FOUND_ROWS * FROM person WHERE id_country = ? AND person_name LIKE ? LIMIT ?,?";

    protected Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    private void printSQLException(SQLException ex) {
        for(Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @Override
    public void insertUser(User user) {
        System.out.println(INSERT_USERS_SQL);
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
        ){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setInt(3,user.getId_country());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        ){
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int country_id = rs.getInt("id_country");
                user = new User(id,name,email,country_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);
        ){
            preparedStatement.setInt(1,id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);
        ){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setInt(3,user.getId_country());
            preparedStatement.setInt(4,user.getId());
            rowUpdate = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdate;
    }

    @Override
    public List<User> searchUser(int name) {
        return null;
    }

    @Override
    public List<User> selectAllUserPage(int offset, int noOfRecords,int id_country,String nameSearch) {
        List<User> users = new ArrayList<>();
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_PAGE);
        ){
            preparedStatement.setInt(1,id_country);
            preparedStatement.setString(2,'%' + nameSearch + '%');
            preparedStatement.setInt(3,offset);
            preparedStatement.setInt(4,noOfRecords);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(preparedStatement);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("person_name");
                String email = rs.getString("email");
                int country_id = rs.getInt("id_country");
                users.add(new User(id,name,email,country_id));
            }
            rs = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> selectAllUser() {
        return null;
    }
    public int getNoOfRecords() {
        return noOfRecords;
    }
}
