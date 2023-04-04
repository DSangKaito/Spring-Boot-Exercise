package com.runsystem.springbootdemo.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
