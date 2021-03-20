package com.example.gp2project;

public class user {

    String username;
    String email;
    String phonNum;
    String password;
    String Key;
    String role="";

    public user() {

    }


    public user(String username, String email, String phonNum, String password) {
        this.username = username;
        this.email = email;
        this.phonNum = phonNum;
        this.password = password;

    }

    public String getRole(){return role;}

    public void setRole(String role) {
        role = role;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getUsername() {
        return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPhonNum() {
            return phonNum;
        }

        public String getPassword() {
            return password;
        }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonNum(String phonNum) {
        this.phonNum = phonNum;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

