
package com.company.employee.controller;

import com.company.employee.model.Approval;
import com.company.employee.service.OnboardingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final OnboardingService onboardingService;

    public ApprovalController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    // Retrieve all pending manager approval requests
    @GetMapping("/pending")
    public List<Approval> getPendingApprovals() {
        return onboardingService.getPendingApprovals();
    }
}
