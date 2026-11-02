
package com.company.employee.controller;

import com.company.employee.model.Approval;
import com.company.employee.service.OnboardingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final OnboardingService onboardingService;

    public ApprovalController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    // View pending approvals
    @GetMapping("/pending")
    public List<Approval> getPendingApprovals() {
        return onboardingService.getPendingApprovals();
    }

    // Approve an onboarding request
    @PutMapping("/{approvalId}/approve")
    public Approval approveRequest(@PathVariable Long approvalId) {
        return onboardingService.approveRequest(approvalId);
    }

    // Reject an onboarding request
    @PutMapping("/{approvalId}/reject")
    public Approval rejectRequest(
            @PathVariable Long approvalId,
            @RequestBody Map<String, String> request) {

        return onboardingService.rejectRequest(
                approvalId,
                request.get("comments")
        );
    }
}
