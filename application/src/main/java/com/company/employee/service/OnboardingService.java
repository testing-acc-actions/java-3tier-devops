
package com.company.employee.service;

import com.company.employee.model.Approval;
import com.company.employee.model.Employee;
import com.company.employee.model.OnboardingTask;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnboardingService {

    private final List<Employee> employees = new ArrayList<>();
    private final List<OnboardingTask> tasks = new ArrayList<>();
    private final List<Approval> approvals = new ArrayList<>();

    private Long employeeIdCounter = 1L;
    private Long taskIdCounter = 1L;
    private Long approvalIdCounter = 1L;

    public Employee registerEmployee(Employee employee) {

        employee.setId(employeeIdCounter++);
        employee.setStatus("PENDING_APPROVAL");

        employees.add(employee);

        generateOnboardingTasks(employee);

        // Create a manager approval record for this employee
        getPendingApprovals()
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
            .filter(approval -> approval.getStatus().equals("PENDING"))
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
}
