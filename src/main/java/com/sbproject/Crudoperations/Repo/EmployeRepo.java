package com.sbproject.Crudoperations.Repo;

import com.sbproject.Crudoperations.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepo extends JpaRepository<Employe,Integer> {
}
