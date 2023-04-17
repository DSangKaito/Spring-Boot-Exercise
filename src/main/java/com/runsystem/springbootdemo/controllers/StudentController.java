package com.runsystem.springbootdemo.controllers;

import com.runsystem.springbootdemo.common.Message;
import com.runsystem.springbootdemo.common.PageReponse;
import com.runsystem.springbootdemo.handler.CustomException;
import com.runsystem.springbootdemo.payloads.request.StudentCodeAndNameAndDateRequest;
import com.runsystem.springbootdemo.payloads.request.StudentRequest;
import com.runsystem.springbootdemo.payloads.response.DataResponse;
import com.runsystem.springbootdemo.payloads.response.StudentResponse;
import com.runsystem.springbootdemo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    /** create bean studentService from service of project */
    @Autowired
    StudentService studentService;

    /**
     * @param pageNumber String style from request
     * @return DataResponse have status and data
     */
    @GetMapping("/all/{pageNumber}")
    public DataResponse getAllStudent(@PathVariable("pageNumber") String pageNumber) {
        int pageNumberNew;
        if (pageNumber == null || pageNumber.equals("")) {
            pageNumberNew = 1;
        } else {
            pageNumberNew = Integer.parseInt(pageNumber);
        }
        List<StudentResponse> studentResponseList = studentService.getAllStudent();
        if (studentResponseList == null) {
            return new DataResponse("401", Message.NO_STUDENT);
        }
        PageReponse<StudentResponse> pageReponse = new PageReponse<StudentResponse>(studentResponseList, pageNumberNew);
        return new DataResponse("200", pageReponse.pagingResponse());
    }

    /**
     * @param id String style from request
     * @return DataResponse have status and (data or message)
     * @throws CustomException if input data is invalid
     */
    @GetMapping("/{id}")
    public DataResponse getStudentById(@PathVariable("id") String id) {
        if (id == null) {
            throw new CustomException("401", Message.INPUT_DATA_NULL);
        }
        int studentId = Integer.parseInt(id);
        StudentResponse studentResponse = studentService.getStudentByStudentId(studentId);
        if (studentResponse == null) {
            throw new CustomException("401", Message.NO_STUDENT);
        }
        return new DataResponse("200", studentResponse);
    }

    /**
     * @param studentRequest StudentRequest object style from request
     * @param id String style from request
     * @return DataResponse when successful - have status and (data or message)
     * @throws CustomException when input data is invalid
     */
    @PutMapping("/{id}")
    public DataResponse updateStudentById(@PathVariable("id") String id, @RequestBody StudentRequest studentRequest) {
        if ((studentRequest == null) || (id == null)) {
            throw new CustomException("401", Message.INPUT_DATA_NULL);
        }
        Integer studentId = Integer.parseInt(id);
        StudentResponse studentResponse = studentService.updateStudentByStudentId(studentRequest, studentId);
        if (studentResponse == null) {
            throw new CustomException("401", Message.NO_STUDENT);
        }
        return new DataResponse("200", studentResponse);
    }

    /**
     * @param id String style from request
     * @return DataResponse when successful - have status and (data or message)
     * @throws CustomException when input data is invalid - have status and message
     */
    @DeleteMapping("/{id}")
    public DataResponse deleteStudentById(@PathVariable("id") String id) {
        if (id == null) {
            throw new CustomException("401", Message.INPUT_DATA_NULL);
        }
        int studentId = Integer.parseInt(id);
        StudentResponse studentResponse = studentService.deleteStudentByStudentId(studentId);
        return new DataResponse("200", studentResponse);
    }

    /**
     * @param studentRequest StudentRequest style from request
     * @return DataResponse when successful - have status and (data or message)
     * @throws CustomException when input data is invalid - have status and message
     */
    @PostMapping("/add")
    public DataResponse addStudent(@RequestBody StudentRequest studentRequest) {
        if (studentRequest == null) {
            throw new CustomException("401", Message.INPUT_DATA_NULL);
        }
        StudentResponse studentResponse = studentService.addStudent(studentRequest);
        if (studentResponse == null) {
            throw new CustomException("401", Message.ACTION_FAIL);
        }
        return new DataResponse("200", studentResponse, Message.ACTION_SUCCESS);
    }

    /**
     * @param pageNumber String style from request
     * @param studentCodeAndNameAndDateRequest StudentCodeAndNameAndDateRequest style from request
     * @return DataResponse when successful - have status and (data or message)
     * @throws CustomException when input data is invalid or don't have data like request from database - have status and message
     */
    @RequestMapping(value = "{/find-by-code-and-name-and-date/{pageNumber}", method = RequestMethod.GET)
    public DataResponse getStudentByPropertiesPagination(@PathVariable String pageNumber,
            @RequestBody StudentCodeAndNameAndDateRequest studentCodeAndNameAndDateRequest) {
        if (studentCodeAndNameAndDateRequest == null) {
            throw new CustomException("401", Message.INPUT_DATA_NULL);
        }
        int pageNumberNew;
        if (pageNumber == null || pageNumber.equals("")) {
            pageNumberNew = 1;
        } else {
            pageNumberNew = Integer.parseInt(pageNumber);
        }
        if (Objects.equals(studentCodeAndNameAndDateRequest.getCode(), "")) {
            studentCodeAndNameAndDateRequest.setCode(null);
        }
        if (Objects.equals(studentCodeAndNameAndDateRequest.getName(), "")) {
            studentCodeAndNameAndDateRequest.setName(null);
        }
        List<StudentResponse> studentResponseList = studentService.getStudentByCodeAndNameAndDate(studentCodeAndNameAndDateRequest, pageNumberNew);
        if (studentResponseList == null) {
            throw new CustomException("401", Message.NO_STUDENT);
        }
        PageReponse<StudentResponse> studentResponse = new PageReponse<StudentResponse>(studentResponseList, pageNumberNew);
        return new DataResponse("200", studentResponse.pagingResponse());
    }
}
