package com.thd.pos_system_java_final.models.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private boolean isActivate;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private AccountRole role;
    private String phone;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;
}
