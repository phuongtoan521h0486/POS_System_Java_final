package com.thd.pos_system_java_final.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    private String username;
    private String password;
    private String fullName;
    private String gmail;
    private boolean isActivate;
    private boolean status;
    private String role;
    private byte[] picture;
}
