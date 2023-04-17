package com.runsystem.springbootdemo.services;

import com.runsystem.springbootdemo.payloads.request.StudentCodeAndNameAndDateRequest;
import com.runsystem.springbootdemo.payloads.request.StudentRequest;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentService {

    List<StudentResponse> getAllStudent();

    StudentResponse getStudentByStudentId(Integer studentId);

    StudentResponse updateStudentByStudentId(StudentRequest studentRequest, Integer id);

    StudentResponse deleteStudentByStudentId(Integer studentId);

    StudentResponse addStudent(StudentRequest studentRequest);

    List<StudentResponse> getStudentByCodeAndNameAndDate(StudentCodeAndNameAndDateRequest studentCodeAndNameAndDateRequest, Integer pageNumber);
}
