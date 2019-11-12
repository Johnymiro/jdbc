package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

public class TestService {

    public static void main(String[] args) {


        JdbcUserService jdbc = new JdbcUserService(new ConnectionManager());

       System.out.println(jdbc.findByName("Denised").getPassword());
       // System.out.println(jdbc.authenticate("JohnyMiro", "123456"));
    }
}
