
package com.company.employee.service;

import com.company.employee.model.Approval;
import com.company.employee.model.Employee;
import com.company.employee.model.OnboardingTask;
import com.company.employee.model.Notification;
import com.company.employee.model.OnboardingDashboard;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnboardingService {

    private final List<Employee> employees = new ArrayList<>();
    private final List<OnboardingTask> tasks = new ArrayList<>();
    private final List<Approval> approvals = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();

    private Long employeeIdCounter = 1L;
    private Long taskIdCounter = 1L;
    private Long approvalIdCounter = 1L;
    private Long notificationIdCounter = 1L;

    public Employee registerEmployee(Employee employee) {

        employee.setId(employeeIdCounter++);
        employee.setStatus("PENDING_APPROVAL");

        employees.add(employee);
        generateOnboardingTasks(employee);
        createApproval(employee);

        return employee;
    }

    private void generateOnboardingTasks(Employee employee) {

        createTask(employee, "Laptop allocation", "IT");
        createTask(employee, "Email setup", "IT");
        createTask(employee, "Software access", "IT");
        createTask(employee, "Security induction", "HR");
        createTask(employee, "Team introduction", "Manager");
    }

    private void createTask(
            Employee employee,
            String taskName,
            String assignedTeam) {

        OnboardingTask task = new OnboardingTask();

        task.setId(taskIdCounter++);
        task.setEmployeeId(employee.getId());
        task.setTaskName(taskName);
        task.setAssignedTeam(assignedTeam);
        task.setStatus("PENDING");
        task.setDueDate(LocalDate.now().plusDays(7));

        tasks.add(task);
    }

    private void createApproval(Employee employee) {

        Approval approval = new Approval();

        approval.setId(approvalIdCounter++);
        approval.setEmployeeId(employee.getId());
        approval.setManagerName(employee.getManagerName());
        approval.setStatus("PENDING");
        approval.setComments("Awaiting manager review");
        approval.setDecisionDate(null);

        approvals.add(approval);
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public List<Approval> getPendingApprovals() {

        return approvals.stream()
                .filter(approval -> "PENDING".equals(approval.getStatus()))
                .toList();
    }

    public List<OnboardingTask> getTasksByEmployee(Long employeeId) {

        return tasks.stream()
                .filter(task -> task.getEmployeeId().equals(employeeId))
                .toList();
    }

    public boolean employeeExists(Long employeeId) {

        return employees.stream()
                .anyMatch(employee -> employee.getId().equals(employeeId));
    }

    public Approval approveRequest(Long approvalId) {

        Approval approval = findPendingApproval(approvalId);

        approval.setStatus("APPROVED");
        approval.setComments("Approved by manager");
        approval.setDecisionDate(LocalDate.now());

        Employee employee = findEmployee(approval.getEmployeeId());
        employee.setStatus("APPROVED");

        createWelcomeNotification(employee);

        return approval;
    }

    public Approval rejectRequest(Long approvalId, String comments) {

        if (comments == null || comments.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rejection comments are required"
            );
        }

        Approval approval = findPendingApproval(approvalId);

        approval.setStatus("REJECTED");
        approval.setComments(comments);
        approval.setDecisionDate(LocalDate.now());

        Employee employee = findEmployee(approval.getEmployeeId());
        employee.setStatus("REJECTED");

        return approval;
    }

    private Approval findPendingApproval(Long approvalId) {

        Approval approval = approvals.stream()
                .filter(item -> item.getId().equals(approvalId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Approval not found"
                ));

        if (!"PENDING".equals(approval.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This approval has already been processed"
            );
        }

        return approval;
    }

    private Employee findEmployee(Long employeeId) {

        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found"
                ));
    }

    private void createWelcomeNotification(Employee employee) {

        Notification notification = new Notification();

        notification.setId(notificationIdCounter++);
        notification.setEmployeeId(employee.getId());
        notification.setRecipient(employee.getEmail());
        notification.setMessage(
                "Welcome " + employee.getName()
                + "! Your onboarding request has been approved."
        );
        notification.setType("WELCOME");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus("GENERATED");

        notifications.add(notification);
    }

    public List<Notification> getNotificationsByEmployee(Long employeeId) {

        return notifications.stream()
                .filter(notification ->
                        notification.getEmployeeId().equals(employeeId))
                .toList();
    }

    public OnboardingTask updateTaskStatus(Long taskId, String newStatus) {

        OnboardingTask task = tasks.stream()
                .filter(item -> item.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Task not found"
                ));

        if (!"PENDING".equals(task.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Only pending tasks can be updated"
            );
        }

        if (!"COMPLETED".equals(newStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only COMPLETED status is supported"
            );
        }

        Employee employee = findEmployee(task.getEmployeeId());

        if ("IT".equals(task.getAssignedTeam())
                && !"APPROVED".equals(employee.getStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Manager approval is required before IT provisioning"
            );
        }

        if (!"APPROVED".equals(employee.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Employee onboarding must be approved before task completion"
            );
        }

        task.setStatus("COMPLETED");

        return task;
    }

    // Calculate onboarding progress for one employee
    public OnboardingDashboard getEmployeeDashboard(Long employeeId) {

        Employee employee = findEmployee(employeeId);

        List<OnboardingTask> employeeTasks =
                getTasksByEmployee(employeeId);

        int totalTasks = employeeTasks.size();

        int completedTasks = (int) employeeTasks.stream()
                .filter(task -> "COMPLETED".equals(task.getStatus()))
                .count();

        int pendingTasks = (int) employeeTasks.stream()
                .filter(task -> "PENDING".equals(task.getStatus()))
                .count();

        int overdueTasks = (int) employeeTasks.stream()
                .filter(task ->
                        "PENDING".equals(task.getStatus())
                        && task.getDueDate() != null
                        && task.getDueDate().isBefore(LocalDate.now()))
                .count();

        double progressPercentage = totalTasks == 0
                ? 0.0
                : (completedTasks * 100.0) / totalTasks;

        return new OnboardingDashboard(
                employee,
                totalTasks,
                completedTasks,
                pendingTasks,
                overdueTasks,
                progressPercentage
        );
    }
}
