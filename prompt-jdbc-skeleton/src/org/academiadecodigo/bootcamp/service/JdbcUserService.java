package org.academiadecodigo.bootcamp.service;


import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserService implements UserService {

    private Connection dbConnection;
    private ConnectionManager connectionManager;


    public JdbcUserService(ConnectionManager connectionManager) {

        this.connectionManager = connectionManager;
        this.dbConnection = connectionManager.getConnection();

    }


    @Override
    public boolean authenticate(String username, String password) {

        ResultSet resultSet;
        boolean result = false;

        String query = "SELECT * FROM users WHERE username=? AND password=?;";

        PreparedStatement statement;

        try{
            statement = dbConnection.prepareStatement(query);

            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            result = resultSet.next();

        }
        catch (SQLException e){
            e.getSQLState();
        }
        return result;

    }


    @Override
    public void add(User user) {


        try {
            String query = "INSERT INTO users (username, password, email, firstname, lastname, phone) values( ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = dbConnection.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getPhone());

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.getSQLState();
        }
    }

    @Override
    public User findByName(String username) {

        String query = "SELECT * FROM users WHERE username=?;";
        User user = null;

        try {


            PreparedStatement statement = dbConnection.prepareStatement(query);

            statement.setString(1, username);


            // execute the query
            ResultSet resultSet = statement.executeQuery();

            // user exists
            if (resultSet.next()) {

                String usernameValue = resultSet.getString("username");
                String passwordValue = resultSet.getString("password");
                String emailValue = resultSet.getString("email");
                String firstNameValue = resultSet.getString("firstname");
                String lastNameValue = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passwordValue,
                        firstNameValue, lastNameValue, phone);
            }
        } catch (SQLException e) {
            e.getSQLState();
        }
        return user;
    }


    @Override
    public List<User> findAll() {

        String query = "select * from users;";
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String username = resultSet.getString("username");
                String password= resultSet.getString("password");
                String email= resultSet.getString("email");
                String firstname = resultSet.getString("firstname");
                String lastname= resultSet.getString("lastname");
                String  phone = resultSet.getString("phone");

                userList.add(new User(username, email, password, firstname, lastname, phone));
            }
        }
        catch (SQLException e){
            e.getSQLState();
            System.out.println("In find all");
        }
        return userList;
    }

    @Override
    public int count() {

        int result = 0;
        PreparedStatement statement;

        try {

            // create a query
            String query = "SELECT COUNT(*) FROM users";

            statement = dbConnection.prepareStatement(query);

            // execute the query
            ResultSet resultSet = statement.executeQuery();

            // get the results
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.getSQLState();
        }
        return result;
    }


    // Methods that help to write less and cleaner code



    private PreparedStatement makePreparedStatement(String query){

        try {
            return dbConnection.prepareStatement(query);
        }
        catch  (SQLException e){
            e.getSQLState();
        }
        return null;
    }



}
