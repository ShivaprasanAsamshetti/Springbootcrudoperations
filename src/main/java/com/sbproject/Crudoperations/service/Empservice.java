package com.sbproject.Crudoperations.service;


import com.sbproject.Crudoperations.Dto.EmployeeDto;
import com.sbproject.Crudoperations.Entity.Employe;
import com.sbproject.Crudoperations.Exception.EmployeeNotFoundException;
import com.sbproject.Crudoperations.Repo.EmployeRepo;
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
public class Empservice {
    @Autowired
    EmployeRepo repo;

    @Value("${file.image.path}")
    private String imageFolder;

    @Value("${file.resume.path}")
    private String resumeFolder;

    public EmployeeDto insert(EmployeeDto req, MultipartFile image, MultipartFile resume) throws IOException {
        Files.createDirectories(Paths.get(imageFolder)); //path to create if not exist C:\Users\Belen ITS\Downloads\springcrudfiles/Images/
        Files.createDirectories(Paths.get(resumeFolder));
        String imagepath = imageFolder + image.getOriginalFilename();//C:\Users\Belen ITS\Downloads\springcrudfiles/Images/  +  photo.jpg
        String resumepath = resumeFolder + resume.getOriginalFilename();
        image.transferTo(new File(imagepath)); //transferrig original image to path photo.jpg
        resume.transferTo(new File(resumepath));
        Employe emp = new Employe();
        BeanUtils.copyProperties(req, emp); //by this entity has paths now not dto right C:\Users\Belen ITS\Downloads\springcrudfiles
        emp.setImagePath(imagepath);
        emp.setResumePath(resumepath);
        Employe result = repo.save(emp);
        BeanUtils.copyProperties(result, req);
        return req;
    }

    public EmployeeDto getEmp(Integer id) {

        Employe empentity = repo.findById(id).orElseThrow(() -> new RuntimeException("Employee id is incorrect"));  //repo functions always returns entity only
        EmployeeDto dtoobj = new EmployeeDto();
        BeanUtils.copyProperties(empentity, dtoobj);
        return dtoobj;
    }

    public List<EmployeeDto> getAllEmp() {
        List<Employe> e = repo.findAll();
        List<EmployeeDto> empdtoresponse = new ArrayList<>();
        for (Employe emp : e) {
            if (emp.getActiveSw().equals("Yes")) {
                EmployeeDto empdto = new EmployeeDto();
                BeanUtils.copyProperties(emp, empdto);  //instresting debug you will understand
                empdtoresponse.add(empdto);
            }
        }
        BeanUtils.copyProperties(e, empdtoresponse);
        return empdtoresponse;
    }

    //2
    public EmployeeDto deleteEmp(Integer id) {
        Employe e = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        e.setActiveSw("No");
        Employe emp = repo.save(e);
        EmployeeDto empdto = new EmployeeDto();
        BeanUtils.copyProperties(e, empdto);
        return empdto;
    }

    //below patch mapping
    public EmployeeDto updateById(Integer id, EmployeeDto emp, MultipartFile image, MultipartFile resume) throws IOException {

        Employe empentity = repo.findById(id).orElseThrow(() -> new RuntimeException("Employee Not found")); //supplier lamda expression and repo methods always returns entity
        Files.deleteIfExists(Paths.get(empentity.getImagePath()));
        Files.deleteIfExists(Paths.get(empentity.getResumePath()));
        String imagepath = imageFolder + image.getOriginalFilename();//C:\Users\Belen ITS\Downloads\springcrudfiles/Images/  +  photo.jpg
        String resumepath = resumeFolder + resume.getOriginalFilename();
        image.transferTo(new File(imagepath)); //transferrig original image to path photo.jpg
        resume.transferTo(new File(resumepath));
        Employe entity = new Employe();
        BeanUtils.copyProperties(emp, entity);
        entity.setImagePath(imagepath);
        entity.setResumePath(resumepath);
        entity.setId(id);
        Employe res = repo.save(entity);
        BeanUtils.copyProperties(res, emp);

        return emp;
    }

}
