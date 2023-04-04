package com.runsystem.springbootdemo.repositories;

import com.runsystem.springbootdemo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();
    Student findStudentByStudentId(Integer studentId);
    List<Student> findStudentsByStudentInfo_DateOfBirthAndStudentCodeAndStudentName(LocalDate dateOfBirth, String studentCode, String studentName);
    List<Student> findStudentsByStudentInfo_DateOfBirthAndStudentCode(LocalDate dateOfBirth, String studentCode);
    List<Student> findStudentsByStudentInfo_DateOfBirthAndStudentName(LocalDate dateOfBirth, String studentName);
    List<Student> findStudentsByStudentCodeAndStudentName(String studentCode, String studentName);
    List<Student> findStudentsByStudentInfo_DateOfBirth(LocalDate dateOfBirth);
    List<Student> findStudentsByStudentName(String studentName);
    Student findStudentByStudentCode(String studentCode);
}
