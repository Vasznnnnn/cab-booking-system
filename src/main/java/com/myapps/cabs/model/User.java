package com.myapps.cabs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

    @jakarta.persistence.Id
    @Column(name = "ID")
    private String Id;

    @Column(name = "USER_EMAIL_ADDRESS")
    private String userEmail;

    @Column(name = "ACCESS_CODE")
    private String accessCode;

    @Column(name = "KERBEROS_ID")
    private String kerberosId;

    @Override
    public String toString() {
        return "User [userEmail: " + userEmail + " accessCode: " + accessCode + "]";
    }
}
