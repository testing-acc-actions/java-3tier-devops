package com.company.employee.model;

import java.time.LocalDate;

public class OnboardingTask {

    private Long id;
    private Long employeeId;
    private String taskName;
    private String assignedTeam;
    private String status;
    private LocalDate dueDate;

    public OnboardingTask() {
    }

    public OnboardingTask(
            Long id,
            Long employeeId,
            String taskName,
            String assignedTeam,
            String status,
            LocalDate dueDate) {

        this.id = id;
        this.employeeId = employeeId;
        this.taskName = taskName;
        this.assignedTeam = assignedTeam;
        this.status = status;
        this.dueDate = dueDate;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
