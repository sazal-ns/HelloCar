package com.rtsoftbd.siddiqui.hellocar.models;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-27.
 */

public class User {
     private static int userID, engContact, pin, state, mobile;
    private static String fullName, userName, password, email, address, imageName, nID;

    public User() {
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }

    public static String  getnID() {
        return nID;
    }

    public static void setnID(String  nID) {
        User.nID = nID;
    }

    public static int getEngContact() {
        return engContact;
    }

    public static void setEngContact(int engContact) {
        User.engContact = engContact;
    }

    public static int getPin() {
        return pin;
    }

    public static void setPin(int pin) {
        User.pin = pin;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        User.state = state;
    }

    public static int getMobile() {
        return mobile;
    }

    public static void setMobile(int mobile) {
        User.mobile = mobile;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        User.fullName = fullName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static String getImageName() {
        return imageName;
    }

    public static void setImageName(String imageName) {
        User.imageName = imageName;
    }
}
