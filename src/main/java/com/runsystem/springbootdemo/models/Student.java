package com.runsystem.springbootdemo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "student")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id",nullable = false)
    private int studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_code", nullable = false)
    private String studentCode;

    @OneToOne(mappedBy = "student")
    private StudentInfo studentInfo;
}
