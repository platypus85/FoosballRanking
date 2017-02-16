package com.albertocamillo.foosballranking.model;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class Player {
    private int id;
    private String fullName;
    private String emailAddress;

    public Player() {

    }

    public Player(String emailAddress, int id, String fullName) {
        this.emailAddress = emailAddress;
        this.id = id;
        this.fullName = fullName;
    }

    public Player(String fullName,String emailAddress) {
        this.emailAddress = emailAddress;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toString(){
        return getFullName()+", "+getEmailAddress();
    }
}
