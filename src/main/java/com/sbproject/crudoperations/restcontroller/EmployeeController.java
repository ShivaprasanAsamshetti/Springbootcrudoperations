package com.sbproject.crudoperations.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbproject.crudoperations.dto.EmployeeDto;
import com.sbproject.crudoperations.response.ApiResponse;
import com.sbproject.crudoperations.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // Save Employee
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDto>> saveEmployee(

            @RequestParam String employeeJson,

            @RequestParam MultipartFile imageFile,

            @RequestParam MultipartFile resumeFile

    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        logger.info("Received request to insert employee");

        EmployeeDto employeeDto =
                objectMapper.readValue(employeeJson, EmployeeDto.class);

        EmployeeDto savedEmployee =
                employeeService.saveEmployee(employeeDto, imageFile, resumeFile);

        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>();

        apiResponse.setStatus(201);
        apiResponse.setMessage("Employee saved successfully");
        apiResponse.setData(savedEmployee);

        logger.info("Request processed successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // Get Employee By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployee(
            @PathVariable Integer id) {

        EmployeeDto employeeDto = employeeService.getEmployee(id);

        ApiResponse<EmployeeDto> apiResponse = new ApiResponse<>();

        apiResponse.setStatus(200);
        apiResponse.setMessage("Employee details fetched successfully");
        apiResponse.setData(employeeDto);

        logger.info("Employee fetched successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Get All Employees
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees() {

        List<EmployeeDto> employeeDtos =
                employeeService.getAllEmployees();

        logger.info("All employee details fetched");

        ApiResponse<List<EmployeeDto>> apiResponse =
                new ApiResponse<>();

        apiResponse.setStatus(200);
        apiResponse.setMessage("All employees fetched successfully");
        apiResponse.setData(employeeDtos);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> deleteEmployee(
            @PathVariable Integer id) {

        EmployeeDto employeeDto =
                employeeService.deleteEmployee(id);

        ApiResponse<EmployeeDto> apiResponse =
                new ApiResponse<>();

        apiResponse.setStatus(200);
        apiResponse.setMessage("Employee deleted successfully");
        apiResponse.setData(employeeDto);

        logger.info("Employee deleted successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Update Employee
    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(

            @RequestParam String employeeJson,

            @RequestParam MultipartFile imageFile,

            @RequestParam MultipartFile resumeFile

    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        EmployeeDto employeeDto =
                objectMapper.readValue(employeeJson, EmployeeDto.class);

        EmployeeDto updatedEmployee =
                employeeService.updateEmployee(employeeDto, imageFile, resumeFile);

        logger.info("Employee updated successfully");

        ApiResponse<EmployeeDto> apiResponse =
                new ApiResponse<>();

        apiResponse.setStatus(200);
        apiResponse.setMessage("Employee updated successfully");
        apiResponse.setData(updatedEmployee);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}