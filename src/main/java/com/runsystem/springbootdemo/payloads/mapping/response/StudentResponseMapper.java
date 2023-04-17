package com.runsystem.springbootdemo.payloads.mapping.response;

import com.runsystem.springbootdemo.models.Student;
import com.runsystem.springbootdemo.models.StudentInfo;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentResponseMapper {
    /** create INSTANCE of StudentResponseMapper */
    StudentResponseMapper INSTANCE = Mappers.getMapper(StudentResponseMapper.class);

    @Mapping(source = "student.studentId", target = "id")
    @Mapping(source = "student.studentCode", target = "code")
    @Mapping(source = "student.studentName", target = "name")
    @Mapping(source = "studentInfo.address", target = "address")
    @Mapping(source = "studentInfo.averageScore", target = "averageScore")
    @Mapping(source = "studentInfo.dateOfBirth", target = "dateOfBirth")
    StudentResponse getStudentResponse(Student student, StudentInfo studentInfo);
}