package com.adamtimpson.mobilityaid.util;

import com.adamtimpson.mobilityaid.database.model.User;

public class LogInUtils {

    private static LogInUtils instance;

    private User currentUser;

    private LogInUtils() { }

    public static LogInUtils getInstance() {
        if(instance == null) {
            instance = new LogInUtils();
        }

        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
