package com.sbproject.Crudoperations.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ename;
    private long mobilenumber;
    private String role;

    public Employe(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", mobilenumber=" + mobilenumber +
                ", role='" + role + '\'' +
                '}';
    }

    public String getEname() {
        return ename;
    }

    public Employe(int id, String ename, long mobilenumber, String role) {
        this.id = id;
        this.ename = ename;
        this.mobilenumber = mobilenumber;
        this.role = role;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public long getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
