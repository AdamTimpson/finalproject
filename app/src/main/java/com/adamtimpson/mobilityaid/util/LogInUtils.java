package com.adamtimpson.mobilityaid.util;

import com.adamtimpson.mobilityaid.database.model.User;

import java.util.List;

public class LogInUtils {

    private static LogInUtils instance;

    private User currentUser;

    private List<String> keysToBeSaved = null;
    private List<String> valuesToBeSaved = null;

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

    public List<String> getKeysToBeSaved() {
        return keysToBeSaved;
    }

    public void setKeysToBeSaved(List<String> keysToBeSaved) {
        this.keysToBeSaved = keysToBeSaved;
    }

    public List<String> getValuesToBeSaved() {
        return valuesToBeSaved;
    }

    public void setValuesToBeSaved(List<String> valuesToBeSaved) {
        this.valuesToBeSaved = valuesToBeSaved;
    }
}
