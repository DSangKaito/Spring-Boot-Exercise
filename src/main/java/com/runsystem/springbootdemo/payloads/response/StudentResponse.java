package com.runsystem.springbootdemo.payloads.response;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StudentResponse implements Serializable {
    private Integer id;
    private String code;;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private Double averageScore;
}
