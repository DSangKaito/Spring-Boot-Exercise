package com.runsystem.springbootdemo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "student")
@Getter
@Setter
//@Document(indexName = "studenInfot_index")
public class Student {
    /** id of student */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private int studentId;

    /** name of student */
    @Column(name = "student_name", nullable = false)
    private String studentName;

    /** code of student */
    @Column(name = "student_code", nullable = false)
    private String studentCode;

    /** informatiomn about student */
    @OneToOne(mappedBy = "student")
    private StudentInfo studentInfo;
}
