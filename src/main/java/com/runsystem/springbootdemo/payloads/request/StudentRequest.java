package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class StudentRequest {
    /** name of student from request */
    private String name;
    /** date of birth student from request */
    private LocalDate dateOfBirth;
    /** address of student from request */
    private String address;
    /** average score of student from request */
    private Double averageScore;
}
