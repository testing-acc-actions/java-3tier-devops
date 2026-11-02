
package com.company.employee.controller;

import com.company.employee.model.OnboardingTask;
import com.company.employee.service.OnboardingService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final OnboardingService onboardingService;

    public TaskController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    // Complete an onboarding task
    @PutMapping("/{taskId}/complete")
    public OnboardingTask completeTask(
            @PathVariable Long taskId,
            @RequestBody Map<String, String> request) {

        return onboardingService.updateTaskStatus(
                taskId,
                request.getOrDefault("status", "COMPLETED")
        );
    }
}
