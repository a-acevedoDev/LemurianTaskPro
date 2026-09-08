package com.example.lemuriantaskpro.utils;

import com.example.lemuriantaskpro.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private List<User> userList;
    private User currentUser;

    private UserManager() {
        userList = new ArrayList<>();
        userList.add(new User("admin", "12345"));
        userList.add(new User("user", "password"));
        userList.add(new User("test", "test123"));
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean registerUser(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        userList.add(new User(username, password));
        return true;
    }

    public boolean loginUser(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}