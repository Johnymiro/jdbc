package org.academiadecodigo.bootcamp.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private java.sql.Connection connection = null;
    private String dbURL = "jdbc:mysql://127.0.0.1:3306/jdbc";

    public Connection getConnection(String url, String root, String password){

        try{
            if (connection == null) {
                connection = DriverManager.getConnection(url, root, password);
            }


            }
            catch (SQLException e){
            e.getSQLState();
        }
        return connection;
    }


    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Failure to close database connections: " + ex.getMessage());
        }
    }

}
