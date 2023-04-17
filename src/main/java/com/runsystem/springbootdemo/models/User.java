package com.runsystem.springbootdemo.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "user")
@Data
public class User {
    /** id of account (user) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /** username of this account (user) */
    @Column(name = "user_name", nullable = false)
    private String username;

    /** password of this account (user) */
    @Column(name = "password", nullable = false)
    private String password;
}
