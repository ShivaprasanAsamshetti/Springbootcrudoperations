package com.sbproject.Crudoperations.service;


import com.sbproject.Crudoperations.Entity.Employe;
import com.sbproject.Crudoperations.Repo.EmployeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Empservice {
    @Autowired
    EmployeRepo repo;

    public Employe  insert(Employe req){
        return repo.save(req);
    }

    public Employe getEmp(Integer id){
        return repo.findById(id).orElseThrow(()-> new RuntimeException("Employee id is incorrect"));
    }

    public List<Employe> getAllEmp(){
        List<Employe> e=repo.findAll();
        return e;
    }

    public String deleteEmp(Integer id){
        Employe e=repo.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        repo.delete(e);
        return "deleted successfully";
    }

   //below patch mapping
    public Employe updateById(Integer id,Employe up){
        Employe e=repo.findById(id).orElseThrow(()-> new RuntimeException("Employee Not found")); //supplier lamda expression

       e.setEname(up.getEname());
       e.setMobilenumber(up.getMobilenumber());

       return repo.save(e);
    }

}
