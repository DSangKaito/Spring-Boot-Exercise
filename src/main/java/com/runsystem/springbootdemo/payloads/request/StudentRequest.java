package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class StudentRequest {
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private Double averageScore;
}
