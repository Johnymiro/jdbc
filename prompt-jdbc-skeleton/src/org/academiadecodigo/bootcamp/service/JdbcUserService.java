package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.controller.UserListController;
import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

import java.sql.*;
import java.util.List;


public class JdbcUserService implements UserService {

    private Connection dbConnection;
    private ConnectionManager connectionManager;
    private User user;
    private String dbURL = "jdbc:mysql://127.0.0.1:3306/jdbc";
    private String root = "root";
    private String password = "";


    public JdbcUserService(ConnectionManager connectionManager) {

        this.connectionManager = connectionManager;
        this.dbConnection = connectionManager.getConnection(dbURL, root, password);

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

            resultSet = statement.executeQuery(query);

            result = resultSet.next();

        }
        catch (SQLException e){
            e.getSQLState();
        }
        return result;

    }


    @Override
    public void add(User user) {

        String query = "INSERT INTO users (username, password, email, firstname, lastname, phone)"+
                "values( ?, ?, ?, ? , ?, ? );";
        PreparedStatement statement = null;

        try {
            statement = dbConnection.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getPhone());

            statement.executeQuery(query);

        }
        catch (SQLException e){
            e.getSQLState();
        }
        finally {
           closeStatement(statement);
        }
    }


    @Override
    public User findByName(String username) {

        String query = "SELECT * FROM users WHERE username=?;";




        try {


            PreparedStatement statement = dbConnection.prepareStatement(query);

            statement.setString(1, username);


            // execute the query
            ResultSet resultSet = statement.executeQuery(query);

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
        return null;
    }

    @Override
    public int count() {

        int result = 0;
        PreparedStatement statement = null;

        try {

            // create a query
            String query = "SELECT COUNT(*) FROM users";

            statement = makePreparedStatement(query);


            // execute the query
            ResultSet resultSet = statement.executeQuery(query);

            // get the results
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.getSQLState();
        }
        finally {
            closeStatement(statement);
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


    private void closeStatement(PreparedStatement statement){
        try {
            statement.close();
        }catch (SQLException e){
            System.out.println("Error while closing state");
            e.getSQLState();
        }
    }


}
