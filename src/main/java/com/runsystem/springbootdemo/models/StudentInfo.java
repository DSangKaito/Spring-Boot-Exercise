package com.runsystem.springbootdemo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "student_info")
@Getter
@Setter
//@Document(indexName = "student_index")
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id", nullable = false)
    private int infoId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    private Student student;

    @Column(name = "address")
    private String address;

    @Column(name = "average_score")
    private Double averageCode;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
