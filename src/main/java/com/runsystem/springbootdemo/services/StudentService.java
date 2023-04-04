package com.runsystem.springbootdemo.services;

import com.runsystem.springbootdemo.payloads.request.StudentCodeAndNameAndDateRequest;
import com.runsystem.springbootdemo.payloads.request.StudentRequest;
import com.runsystem.springbootdemo.payloads.response.DataResponse;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentService {
    Page<StudentResponse> getAllStudent(Integer pageNumber);
    StudentResponse getStudentByStudentId(Integer studentId);
    DataResponse updateStudentByStudentId(StudentRequest studentRequest, Integer id);
    StudentResponse deleteStudentByStudentId(Integer studentId);
    StudentResponse addStudent(StudentRequest studentRequest);
    Page<StudentResponse> getStudentByCodeAndNameAndDate(StudentCodeAndNameAndDateRequest studentCodeAndNameAndDateRequest, Integer pageNumber);
}
