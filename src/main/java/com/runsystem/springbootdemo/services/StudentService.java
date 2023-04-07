package com.runsystem.springbootdemo.services;

import com.runsystem.springbootdemo.payloads.request.StudentCodeAndNameAndDateRequest;
import com.runsystem.springbootdemo.payloads.request.StudentRequest;
import com.runsystem.springbootdemo.payloads.response.DataResponse;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import springfox.documentation.annotations.Cacheable;

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
