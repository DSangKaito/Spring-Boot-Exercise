package com.runsystem.springbootdemo.payloads.response;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StudentResponse implements Serializable {
    /** id of student for response */
    private Integer id;
    /** code of student for response */
    private String code;
    /** name of student for response */
    private String name;
    /** student's date of birth for response */
    private LocalDate dateOfBirth;
    /** address of student for response */
    private String address;
    /** student's average score for response */
    private Double averageScore;
}
