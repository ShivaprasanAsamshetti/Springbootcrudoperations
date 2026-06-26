package com.sbproject.crudoperations.dto;

public class EmployeeDto {

    private int id;

    private String ename;

    private long mobilenumber;

    private String role;

    private String activeSw = "YES";

    private String imagePath;

    private String resumePath;

    public EmployeeDto() {

    }

    public EmployeeDto(int id,
                       String ename,
                       long mobilenumber,
                       String role,
                       String activeSw,
                       String imagePath,
                       String resumePath) {

        this.id = id;
        this.ename = ename;
        this.mobilenumber = mobilenumber;
        this.role = role;
        this.activeSw = activeSw;
        this.imagePath = imagePath;
        this.resumePath = resumePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
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

    public String getActiveSw() {
        return activeSw;
    }

    public void setActiveSw(String activeSw) {
        this.activeSw = activeSw;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", mobilenumber=" + mobilenumber +
                ", role='" + role + '\'' +
                ", activeSw='" + activeSw + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", resumePath='" + resumePath + '\'' +
                '}';
    }
}