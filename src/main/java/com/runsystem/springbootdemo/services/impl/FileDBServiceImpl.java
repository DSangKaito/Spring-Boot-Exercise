package com.runsystem.springbootdemo.services.impl;

import com.runsystem.springbootdemo.models.FileDB;
import com.runsystem.springbootdemo.repositories.FileDBRepository;
import com.runsystem.springbootdemo.services.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileDBServiceImpl implements FileDBService {

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB image = new FileDB(fileName, file.getContentType(), file.getBytes());
        // check type of file - just allow world, pdf, excel files.
        if (!(Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || Objects.equals(file.getContentType(), "application/msword")
                || Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                || Objects.equals(file.getContentType(), "application/vnd.ms-excel")
                || Objects.equals(file.getContentType(), "application/pdf")))
            return null;
        return fileDBRepository.save(image);
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
