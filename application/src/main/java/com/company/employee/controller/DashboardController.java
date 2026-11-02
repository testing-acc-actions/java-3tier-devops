package com.company.employee.controller;

import com.company.employee.model.OnboardingDashboard;
import com.company.employee.service.OnboardingService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final OnboardingService onboardingService;

    public DashboardController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping("/employees/{employeeId}")
    public OnboardingDashboard getEmployeeDashboard(
            @PathVariable Long employeeId) {

        return onboardingService.getEmployeeDashboard(employeeId);
    }
}
