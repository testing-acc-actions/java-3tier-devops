package com.company.employee.model;

import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private Long employeeId;
    private String recipient;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private String status;

    public Notification() {
    }

    public Notification(
            Long id,
            Long employeeId,
            String recipient,
            String message,
            String type,
            LocalDateTime createdAt,
            String status) {

        this.id = id;
        this.employeeId = employeeId;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
}
