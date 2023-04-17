package com.runsystem.springbootdemo.controllers;

import com.runsystem.springbootdemo.common.Message;
import com.runsystem.springbootdemo.models.FileDB;
import com.runsystem.springbootdemo.payloads.response.FileDBResponse;
import com.runsystem.springbootdemo.services.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileDBController {
    /** create fileDB bean */
    @Autowired
    private FileDBService fileDBService;

    /**
     * @param file MultipartFile style from request
     * @return ResponseEntity
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if (fileDBService.store(file) == null) {
                message = Message.INPUT_INVALID;
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            message = Message.ACTION_SUCCESS + " File: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = Message.ACTION_FAIL + "File: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /**
     * @return ResponseEntity
     */
    @GetMapping("/files")
    public ResponseEntity<List<FileDBResponse>> getListFiles() {
        List<FileDBResponse> files = fileDBService.getAllFiles().map(dbFile -> {
            String fileDownloadUri =
                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/files/").path(dbFile.getId().toString()).toUriString();

            return new FileDBResponse(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    /**
     * @param id String style from request
     * @return ResponseEntity
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") String id) {
        Long fileDBId = Long.parseLong(id);
        Optional<FileDB> optionalFileDB = fileDBService.getById(fileDBId);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + optionalFileDB.get().getName() + "\"")
                .body(optionalFileDB.get().getData());
    }

    /**
     * @param word String style from request
     * @return ResponseEntity
     */
    @PostMapping("/files/search/{word}")
    public ResponseEntity<List<FileDBResponse>> getFileByWord(@PathVariable("word") String word) {
        List<FileDBResponse> fileDBList = fileDBService.getFilesByWord(word).map(dbFile -> {
            String fileDownloadUri =
                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/files/").path(dbFile.getId().toString()).toUriString();

            return new FileDBResponse(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileDBList);
    }
}
