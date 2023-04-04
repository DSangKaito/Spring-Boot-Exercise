package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class StudentCodeAndNameAndDateRequest {
    String code;
    String name;
    LocalDate dateOfBirth;
}
