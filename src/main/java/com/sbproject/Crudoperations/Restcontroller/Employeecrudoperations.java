package com.sbproject.Crudoperations.Restcontroller;

import com.sbproject.Crudoperations.Dto.EmployeeDto;
import com.sbproject.Crudoperations.Entity.Employe;
import com.sbproject.Crudoperations.response.Apiresponse;
import com.sbproject.Crudoperations.service.Empservice;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class Employeecrudoperations {

    private static final Logger logger = LoggerFactory.getLogger(Employeecrudoperations.class);
    @Autowired
    Empservice service;

    private int id;
    private String ename;
    private long mobilenumber;

    private String role;
    private String activeSw="YES";
    private String imageUrl;
    private String resumeUrl;



    //post employee
    //"Whenever a POST request comes to /, execute this method.Spring expects:Content-Type:multipart/form-data because the request contains files and so the request is not JSON. because it has image  and Resume
    @PostMapping(value="/",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)  //
    public ResponseEntity<Apiresponse<EmployeeDto>> saveEmp(@RequestParam String ename,
                                                            @RequestParam String role,
                                                            @RequestParam Long mobilenumber,
                                                            @RequestParam  String activeSw,
                                                            @RequestParam MultipartFile image,
                                                            @RequestParam MultipartFile Resume
                                                                                 )throws IOException {
        logger.info("Received request to insert employee");
        EmployeeDto emp = new EmployeeDto();
        emp.setMobilenumber(mobilenumber);
        emp.setEname(ename);
        emp.setRole(role);
        emp.setActiveSw(activeSw);
        EmployeeDto result=service.insert(emp,image,Resume);
        Apiresponse<EmployeeDto> response = new Apiresponse<>();
        response.setStatus(201);
        response.setMessage("Employee saved successfully");
        response.setData(emp);
        logger.info("request processed successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //get employee id
    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<EmployeeDto>> getEmployee(@PathVariable int id) {
        EmployeeDto emp = service.getEmp(id);
        Apiresponse<EmployeeDto> response = new Apiresponse<>();
        response.setStatus(200);
        response.setMessage("All employee details are fetched");
        response.setData(emp);
        logger.info("request processed to get the employee detail");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get employees
    @GetMapping("")
    public ResponseEntity<Apiresponse<List<EmployeeDto>>> getAllEmploye() {
        List<EmployeeDto> listEmps = service.getAllEmp();
        logger.info("request processed to get the all employee details");
        Apiresponse<List<EmployeeDto>> response = new Apiresponse<>();
        response.setStatus(200);
        response.setMessage("All employees are fetched");
        response.setData(listEmps);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //delete employee id
    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<EmployeeDto>> deleteById(@PathVariable Integer id) {
        EmployeeDto empdto = service.deleteEmp(id);
        Apiresponse<EmployeeDto> response = new Apiresponse<>();
        response.setStatus(200);
        response.setMessage("Employee is deleted");
        response.setData(empdto);
        logger.info("request processed Employee to delete using id");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //put employe id update entire
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<EmployeeDto>> updateby(@PathVariable Integer id,
                                                             @RequestParam String ename,
                                                             @RequestParam String role,
                                                             @RequestParam Long mobilenumber,
                                                             @RequestParam  String activeSw,
                                                             @RequestParam MultipartFile image,
                                                             @RequestParam MultipartFile Resume


                                                             )throws IOException{
        EmployeeDto emp = new EmployeeDto();
        emp.setMobilenumber(mobilenumber);
        emp.setEname(ename);
        emp.setRole(role);
        emp.setActiveSw(activeSw);
        EmployeeDto empdto=service.updateById(id,emp,image,Resume);
        logger.info("updated");
        Apiresponse<EmployeeDto> response=new Apiresponse<>();
        response.setData(empdto);
        response.setMessage("updated by value successfully");
        response.setStatus(200);

        logger.info("request processed to update the  employee details");
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }


}
