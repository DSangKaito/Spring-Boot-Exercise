package com.runsystem.springbootdemo.services.impl;

import com.runsystem.springbootdemo.common.Message;
import com.runsystem.springbootdemo.models.Student;
import com.runsystem.springbootdemo.models.StudentInfo;
import com.runsystem.springbootdemo.payloads.mapping.response.StudentResponseMapper;
import com.runsystem.springbootdemo.payloads.request.StudentCodeAndNameAndDateRequest;
import com.runsystem.springbootdemo.payloads.request.StudentRequest;
import com.runsystem.springbootdemo.payloads.response.DataResponse;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import com.runsystem.springbootdemo.repositories.StudentInfoRepository;
import com.runsystem.springbootdemo.repositories.StudentRepository;
import com.runsystem.springbootdemo.services.StudentService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentInfoRepository studentInfoRepository;

    StudentResponseMapper studentResponseMapper = Mappers.getMapper(StudentResponseMapper.class);

    @Override
    public Page<StudentResponse> getAllStudent(Integer pageNumber) {
        List<Student> studentList = studentRepository.findAll();
        if (studentList.size() == 0) {
            return null;
        }
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        StudentResponse studentResponse;
        StudentInfo studentInfo;
        for (Student student : studentList){
            studentInfo = studentInfoRepository.findStudentInfoByStudent(student);
            studentResponse = studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
            studentResponseList.add(studentResponse);
        }
        // 1. PageListHolder
        PagedListHolder<StudentResponse> studentPagedListHolder = new PagedListHolder<StudentResponse>(studentResponseList);
        studentPagedListHolder.setPage(pageNumber - 1);
        studentPagedListHolder.setPageSize(2);

        //2. PropertyComparator
        List<StudentResponse> pageSlice = studentPagedListHolder.getPageList();
        //PropertyComparator.sort(pageSlice, new MutableSortDefinition("id", true, true));

        //3. PageImpl
        Pageable pageable = PageRequest.of(pageNumber - 1, studentResponseList.size());
        return new PageImpl<>(pageSlice, pageable, studentResponseList.size());
    }

    @Override
    public StudentResponse getStudentByStudentId(Integer studentId) {
        Student student = studentRepository.findStudentByStudentId(studentId);
        StudentInfo studentInfo = studentInfoRepository.findStudentInfoByStudent(student);
        return studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
    }

    @Override
    public DataResponse updateStudentByStudentId(StudentRequest studentRequest, Integer id) {
        Student student = studentRepository.findStudentByStudentId(id);
        if (student == null) {
            return new DataResponse("400", Message.NO_STUDENT);
        }
        StudentInfo studentInfo = studentInfoRepository.findStudentInfoByStudent(student);
        if (studentInfo == null) {
            studentInfo = new StudentInfo();
            studentInfo.setStudent(student);
            student.setStudentInfo(studentInfo);
        }
        // check just update when have new data (student)
        if (studentRequest.getName() != null) student.setStudentName(studentRequest.getName());
        student.setStudentInfo(studentInfo);
        // check just update when have new data (studentRequest)
        if (studentRequest.getAddress() != null) studentInfo.setAddress(studentRequest.getAddress());
        if (studentRequest.getAverageScore() != null) studentInfo.setAverageCode(studentRequest.getAverageScore());
        if (studentRequest.getDateOfBirth() != null) studentInfo.setDateOfBirth(studentRequest.getDateOfBirth());
        studentInfoRepository.save(studentInfo);
        studentRepository.save(student);
        StudentResponse studentResponse = studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
        return new DataResponse("200", studentResponse);
    }

    public StudentResponse deleteStudentByStudentId(Integer studentId){
        Student student = studentRepository.findStudentByStudentId(studentId);
        StudentInfo studentInfo = studentInfoRepository.findStudentInfoByStudent(student);
        studentInfoRepository.delete(studentInfo);
        studentRepository.delete(student);
        return studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
    }

    public Student convertStudentRequestToStudent(StudentRequest studentRequest){
        Student student = new Student();
        student.setStudentName(studentRequest.getName());
        long leftLimit = 1000000000L;
        long rightLimit = 9999999999L;
        long generatedCodeNumber = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        student.setStudentCode("STU" + generatedCodeNumber);
        return  student;
    }

    public StudentInfo convertStudetnRequestToStudentInfo(StudentRequest studentRequest){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setAddress(studentRequest.getAddress());
        studentInfo.setAverageCode(studentRequest.getAverageScore());
        studentInfo.setDateOfBirth(studentRequest.getDateOfBirth());
        return studentInfo;
    }

    @Override
    public StudentResponse addStudent(StudentRequest studentRequest) {
        Student student = convertStudentRequestToStudent(studentRequest);
        StudentInfo studentInfo = convertStudetnRequestToStudentInfo(studentRequest);
        studentInfo.setStudent(student);
        studentRepository.save(student);
        studentInfoRepository.save(studentInfo);
        return studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
    }

    @Override
    public Page<StudentResponse> getStudentByCodeAndNameAndDate(StudentCodeAndNameAndDateRequest studentCodeAndNameAndDateRequest, Integer pageNumber) {
        List<Student> studentList = new ArrayList<>();
        if (studentCodeAndNameAndDateRequest.getCode() == null && studentCodeAndNameAndDateRequest.getName() == null) {
            studentList = (studentRepository.findStudentsByStudentInfo_DateOfBirth(studentCodeAndNameAndDateRequest.getDateOfBirth()));
        } else if (studentCodeAndNameAndDateRequest.getCode() == null && studentCodeAndNameAndDateRequest.getDateOfBirth() == null) {
            studentList = (studentRepository.findStudentsByStudentName(studentCodeAndNameAndDateRequest.getName()));
        } else if (studentCodeAndNameAndDateRequest.getName() == null && studentCodeAndNameAndDateRequest.getDateOfBirth() == null) {
            studentList.add(studentRepository.findStudentByStudentCode(studentCodeAndNameAndDateRequest.getCode()));
        } else if (studentCodeAndNameAndDateRequest.getDateOfBirth() == null){
            studentList = (studentRepository.findStudentsByStudentCodeAndStudentName(studentCodeAndNameAndDateRequest.getCode(),studentCodeAndNameAndDateRequest.getName()));
        } else if (studentCodeAndNameAndDateRequest.getName() == null){
            studentList = (studentRepository.findStudentsByStudentInfo_DateOfBirthAndStudentCode(studentCodeAndNameAndDateRequest.getDateOfBirth(), studentCodeAndNameAndDateRequest.getCode()));
        } else if (studentCodeAndNameAndDateRequest.getCode() == null){
            studentList = (studentRepository.findStudentsByStudentInfo_DateOfBirthAndStudentName(studentCodeAndNameAndDateRequest.getDateOfBirth(), studentCodeAndNameAndDateRequest.getName()));
        } else
            studentList = (studentRepository.findStudentsByStudentInfo_DateOfBirthAndStudentCodeAndStudentName(studentCodeAndNameAndDateRequest.getDateOfBirth(), studentCodeAndNameAndDateRequest.getCode(), studentCodeAndNameAndDateRequest.getName()));
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        StudentResponse studentResponse;
        StudentInfo studentInfo;
        for (Student student : studentList){
            studentInfo = studentInfoRepository.findStudentInfoByStudent(student);
            studentResponse = studentResponseMapper.INSTANCE.getStudentResponse(student, studentInfo);
            studentResponseList.add(studentResponse);
        }
        // 1. PageListHolder
        PagedListHolder<StudentResponse> studentPagedListHolder = new PagedListHolder<StudentResponse>(studentResponseList);
        studentPagedListHolder.setPage(pageNumber - 1);
        studentPagedListHolder.setPageSize(2);

        //2. PropertyComparator
        List<StudentResponse> pageSlice = studentPagedListHolder.getPageList();
        //PropertyComparator.sort(pageSlice, new MutableSortDefinition("id", true, true));

        //3. PageImpl
        Pageable pageable = PageRequest.of(pageNumber - 1, studentResponseList.size());
        return new PageImpl<>(pageSlice, pageable, studentResponseList.size());
    }
}
