
package com.company.employee.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "approvals",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_approval_employee",
            columnNames = "employee_id"
        )
    }
)
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "employee_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_approval_employee")
    )
    private Employee employee;

    @Column(name = "manager_name", nullable = false, length = 100)
    private String managerName;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 500)
    private String comments;

    @Column(name = "decision_date")
    private LocalDate decisionDate;

    public Approval() {
    }

    public Approval(
            Long id,
            Employee employee,
            String managerName,
            String status,
            String comments,
            LocalDate decisionDate) {
        this.id = id;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
