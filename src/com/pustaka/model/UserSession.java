/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import java.io.Serializable;

/**
 *
 * @author dwi
 */
public class UserSession implements Serializable{
    private static String username;
    private static String password;
    private static UserSession userSession;
    
    public synchronized static UserSession getInstance() throws ExceptionInInitializerError {
        UserSession tempUserSession = null;
        if (userSession == null) {
            userSession = new UserSession();
            tempUserSession = userSession;
        } else if (userSession.getUserSession() == null) {
            userSession = new UserSession();
            tempUserSession = userSession;
        } else {
            tempUserSession = userSession;
        }

        return tempUserSession;
    }

    public static UserSession getUserSession() {
        return userSession;
    }

    public static void setUserSession(UserSession userSession) {
        UserSession.userSession = userSession;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserSession.password = password;
    }
    
}
