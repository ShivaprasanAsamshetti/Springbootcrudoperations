package com.sbproject.Crudoperations.Restcontroller;

import com.sbproject.Crudoperations.Entity.Employe;
import com.sbproject.Crudoperations.service.Empservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/apicrud")
public class Employeecrud {

    private static final Logger logger=LoggerFactory.getLogger(Employeecrud.class);
    @Autowired
    Empservice service;

    @PostMapping("/insert")
    public ResponseEntity<Employe> insert(@RequestBody Employe employe) {
        logger.info("Received request to insert employee");
        Employe emp=service.insert(employe);
        logger.info("request processed successfully");
        return ResponseEntity.ok(emp);

    }
    @GetMapping("/get/{id}")
    public Employe getEmployee(@PathVariable int id){
        Employe e=service.getEmp(id);
        logger.info("request processed to get the employee detail");
        return e;
    }
    @GetMapping("/getAll")
    public List<Employe> getAllEmploye(){
        logger.info("request processed to get the all employee details");
        return service.getAllEmp();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id){
        logger.info("request processed Employee to delete using id");
        return service.deleteEmp(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Integer id,@RequestBody Employe employe){
        logger.info("request processed to update the  employee details");
        return ResponseEntity.ok(service.updateById(id,employe));

    }




}
