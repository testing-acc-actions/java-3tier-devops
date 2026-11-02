package com.company.employee.model;

import java.time.LocalDate;

public class Employee {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String jobTitle;
    private LocalDate joiningDate;
    private String managerName;
    private String status;

    public Employee() {
    }

    public Employee(
            Long id,
            String name,
            String email,
            String department,
            String jobTitle,
            LocalDate joiningDate,
            String managerName,
            String status) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.jobTitle = jobTitle;
        this.joiningDate = joiningDate;
        this.managerName = managerName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
