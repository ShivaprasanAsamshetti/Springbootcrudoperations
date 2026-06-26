package com.sbproject.crudoperations.service;

import com.sbproject.crudoperations.dto.EmployeeDto;
import com.sbproject.crudoperations.entity.Employee;
import com.sbproject.crudoperations.exception.EmployeeNotFoundException;
import com.sbproject.crudoperations.repo.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${file.image.path}")
    private String imageFolder;

    @Value("${file.resume.path}")
    private String resumeFolder;

    // Save Employee
    public EmployeeDto saveEmployee(EmployeeDto employeeDto,
                                    MultipartFile imageFile,
                                    MultipartFile resumeFile) throws IOException {

        Files.createDirectories(Paths.get(imageFolder));
        Files.createDirectories(Paths.get(resumeFolder));

        String imagePath = imageFolder + imageFile.getOriginalFilename();
        String resumePath = resumeFolder + resumeFile.getOriginalFilename();

        imageFile.transferTo(new File(imagePath));
        resumeFile.transferTo(new File(resumePath));

        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDto, employee);

        employee.setImagePath(imagePath);
        employee.setResumePath(resumePath);

        Employee savedEmployee = employeeRepository.save(employee);

        BeanUtils.copyProperties(savedEmployee, employeeDto);

        return employeeDto;
    }

    // Get Employee By Id
    public EmployeeDto getEmployee(Integer employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId+" Employee not found"));

        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(employee, employeeDto);

        return employeeDto;
    }

    // Get All Employees
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employeeList = employeeRepository.findAll();

        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {

            if ("YES".equalsIgnoreCase(employee.getActiveSw())) {

                EmployeeDto employeeDto = new EmployeeDto();

                BeanUtils.copyProperties(employee, employeeDto);

                employeeDtoList.add(employeeDto);
            }
        }

        return employeeDtoList;
    }

    // Soft Delete
    public EmployeeDto deleteEmployee(Integer employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId+" Employee not found"));

        employee.setActiveSw("NO");

        Employee deletedEmployee = employeeRepository.save(employee);

        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(deletedEmployee, employeeDto);

        return employeeDto;
    }

    // Update Employee
    public EmployeeDto updateEmployee(EmployeeDto employeeDto,
                                      MultipartFile imageFile,
                                      MultipartFile resumeFile) throws IOException {

        Employee existingEmployee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Files.deleteIfExists(Paths.get(existingEmployee.getImagePath()));
        Files.deleteIfExists(Paths.get(existingEmployee.getResumePath()));

        String imagePath = imageFolder + imageFile.getOriginalFilename();
        String resumePath = resumeFolder + resumeFile.getOriginalFilename();

        imageFile.transferTo(new File(imagePath));
        resumeFile.transferTo(new File(resumePath));

        BeanUtils.copyProperties(employeeDto, existingEmployee);

        existingEmployee.setImagePath(imagePath);
        existingEmployee.setResumePath(resumePath);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        BeanUtils.copyProperties(updatedEmployee, employeeDto);

        return employeeDto;
    }
}