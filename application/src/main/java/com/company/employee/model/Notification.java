
package com.company.employee.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "employee_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_notification_employee")
    )
    private Employee employee;

    @Column(nullable = false, length = 150)
    private String recipient;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 30)
    private String status;

    public Notification() {
    }

    public Notification(
            Long id,
            Employee employee,
            String recipient,
            String message,
            String type,
            LocalDateTime createdAt,
            String status) {
        this.id = id;
        this.employee = employee;
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.status = status;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
