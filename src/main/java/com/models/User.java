package com.models;

import java.time.LocalDate;

public class User {

    private int id;
    private String email;
    private String password;
    private String username;
    private LocalDate birthday;
    private String gender;
    private String country;
    private String postalCode;

    public User(String email, String password, String username,
                LocalDate birthday, String gender, String country, String postalCode) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
