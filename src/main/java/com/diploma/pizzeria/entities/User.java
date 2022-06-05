package com.diploma.pizzeria.entities;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "s_users", initialValue = 15, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "s_users")
    private Long id;
    private String fullName;
    private String email;
    private String pass;
    private String userRole;
    private String phoneNumber;

    public User(String fullName, String email, String pass, String userRole, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.pass = pass;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", userRole='" + userRole + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}