
package com.company.employee.controller;

import com.company.employee.model.Notification;
import com.company.employee.service.OnboardingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final OnboardingService onboardingService;

    public NotificationController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    // Retrieve notifications for an employee
    @GetMapping("/employee/{employeeId}")
    public List<Notification> getEmployeeNotifications(
            @PathVariable Long employeeId) {

        return onboardingService.getNotificationsByEmployee(employeeId);
    }
}
