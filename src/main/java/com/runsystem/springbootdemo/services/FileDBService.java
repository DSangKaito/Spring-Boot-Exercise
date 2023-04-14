package com.runsystem.springbootdemo.services;

import com.runsystem.springbootdemo.models.FileDB;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public interface FileDBService {
    FileDB store(MultipartFile file) throws IOException;
    Stream<FileDB> getAllFiles();
    Optional<FileDB> getById(Long id);
    Stream<FileDB> getFilesByWord(String word);
}
