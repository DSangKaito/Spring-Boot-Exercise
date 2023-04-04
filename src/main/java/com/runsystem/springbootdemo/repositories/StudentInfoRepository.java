package com.runsystem.springbootdemo.repositories;

import com.runsystem.springbootdemo.models.Student;
import com.runsystem.springbootdemo.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer>{
    StudentInfo findStudentInfoByStudent(Student student);
}
