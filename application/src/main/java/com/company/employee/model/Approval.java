package com.company.employee.model;

import java.time.LocalDate;

public class Approval {

    private Long id;
    private Long employeeId;
    private String managerName;
    private String status;
    private String comments;
    private LocalDate decisionDate;

    public Approval() {
    }

    public Approval(
            Long id,
            Long employeeId,
            String managerName,
            String status,
            String comments,
            LocalDate decisionDate) {

        this.id = id;
        this.employeeId = employeeId;
        this.managerName = managerName;
        this.status = status;
        this.comments = comments;
        this.decisionDate = decisionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }
}
