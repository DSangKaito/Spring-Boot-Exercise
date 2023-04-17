package com.runsystem.springbootdemo.payloads.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class StudentCodeAndNameAndDateRequest {
    /** code of student from  request */
    String code;
    /** name of student from request */
    String name;
    /** date of birth from request */
    LocalDate dateOfBirth;
}
