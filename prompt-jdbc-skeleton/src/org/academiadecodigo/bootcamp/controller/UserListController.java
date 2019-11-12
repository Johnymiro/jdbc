package org.academiadecodigo.bootcamp.controller;

import org.academiadecodigo.bootcamp.model.User;

import java.util.HashMap;
import java.util.List;

public class UserListController extends AbstractController {

    private HashMap<String, String> list;

    public List<User> getUserList() {
        return userService.findAll();
    }

}
