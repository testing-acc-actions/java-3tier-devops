package com.company.employee.controller;

import com.company.employee.model.Employee;
import com.company.employee.model.OnboardingTask;
import com.company.employee.service.OnboardingService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final OnboardingService onboardingService;

    public EmployeeController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee registerEmployee(@RequestBody Employee employee) {
        return onboardingService.registerEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return onboardingService.getAllEmployees();
    }

    @GetMapping("/{employeeId}/tasks")
    public List<OnboardingTask> getEmployeeTasks(
            @PathVariable Long employeeId) {

        return onboardingService.getTasksByEmployee(employeeId);
    }

    @GetMapping("/{employeeId}/tasks")
    public List<OnboardingTask> getEmployeeTasks(
        @PathVariable Long employeeId) {

       if (!onboardingService.employeeExists(employeeId)) {
          throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee not found");
    }

      return onboardingService.getTasksByEmployee(employeeId);
    }
}
