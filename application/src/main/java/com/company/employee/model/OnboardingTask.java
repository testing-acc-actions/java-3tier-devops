
package com.company.employee.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "onboarding_tasks")
public class OnboardingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "employee_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_task_employee")
    )
    private Employee employee;

    @Column(name = "task_name", nullable = false, length = 150)
    private String taskName;

    @Column(name = "assigned_team", nullable = false, length = 50)
    private String assignedTeam;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "due_date")
    private LocalDate dueDate;

    public OnboardingTask() {
    }

    public OnboardingTask(
            Long id,
            Employee employee,
            String taskName,
            String assignedTeam,
            String status,
            LocalDate dueDate) {
        this.id = id;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    // Temporary compatibility methods for the existing service.
    @Transient
    public Long getEmployeeId() {
        return employee == null ? null : employee.getId();
    }

    @Transient
    public void setEmployeeId(Long employeeId) {
        if (employeeId == null) {
            this.employee = null;
        } else {
            Employee employeeReference = new Employee();
            employeeReference.setId(employeeId);
            this.employee = employeeReference;
        }
    }
}
