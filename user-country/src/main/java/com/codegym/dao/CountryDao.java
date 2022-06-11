package com.codegym.dao;

import com.codegym.model.Country;
import com.codegym.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDao implements ICountryDao{
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo_country?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";
    private static final String SELECT_ALL_COUNTRY = "SELECT * FROM country";
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
    @Override
    public List<Country> selectAllCountry() {
        List<Country> countries = new ArrayList<>();
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COUNTRY);
        ){
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name_country");
                countries.add(new Country(id,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
