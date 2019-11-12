package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

public class TestService {

    public static void main(String[] args) {


        JdbcUserService jdbc = new JdbcUserService(new ConnectionManager());
        jdbc.add(new User("Lola", "johny@gmail.com", "12345", "Jakhongir", "Miralimov", "91312321"));

      // System.out.println(jdbc.findByName("johny").getPassword());
       // System.out.println(jdbc.authenticate("JohnyMiro", "123456"));
    }
}
