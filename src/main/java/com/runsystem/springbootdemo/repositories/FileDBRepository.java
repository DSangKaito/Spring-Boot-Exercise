package com.runsystem.springbootdemo.repositories;

import com.runsystem.springbootdemo.models.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    FileDB findFileDBById(Long id);
}
