
package com.company.employee.service;

import com.company.employee.model.Approval;
import com.company.employee.model.Employee;
import com.company.employee.model.Notification;
import com.company.employee.model.OnboardingTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnboardingServiceTest {

    private OnboardingService onboardingService;

    @BeforeEach
    void setUp() {
        onboardingService = new OnboardingService();
    }

    private Employee registerTestEmployee() {
        Employee employee = new Employee(
                null,
                "John Smith",
                "john@example.com",
                "Engineering",
                "Software Engineer",
                LocalDate.now().plusDays(7),
                "Sarah Manager",
                null
        );

        return onboardingService.registerEmployee(employee);
    }

    @Test
    void registeringEmployeeShouldGenerateFiveTasks() {
        Employee employee = registerTestEmployee();
        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(employee.getId());

        assertNotNull(employee.getId());
        assertEquals("PENDING_APPROVAL", employee.getStatus());
        assertEquals(5, tasks.size());
    }

    @Test
    void generatedTasksShouldHaveCorrectAssignments() {
        Employee employee = registerTestEmployee();
        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(employee.getId());

        assertEquals("Laptop allocation", tasks.get(0).getTaskName());
        assertEquals("IT", tasks.get(0).getAssignedTeam());
        assertEquals("Email setup", tasks.get(1).getTaskName());
        assertEquals("IT", tasks.get(1).getAssignedTeam());
        assertEquals("Software access", tasks.get(2).getTaskName());
        assertEquals("IT", tasks.get(2).getAssignedTeam());
        assertEquals("Security induction", tasks.get(3).getTaskName());
        assertEquals("HR", tasks.get(3).getAssignedTeam());
        assertEquals("Team introduction", tasks.get(4).getTaskName());
        assertEquals("Manager", tasks.get(4).getAssignedTeam());
    }

    @Test
    void generatedTasksShouldStartAsPending() {
        Employee employee = registerTestEmployee();
        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(employee.getId());

        assertTrue(tasks.stream()
                .allMatch(task -> "PENDING".equals(task.getStatus())));
    }

    @Test
    void managerApprovalShouldUpdateEmployeeStatus() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        Approval result =
                onboardingService.approveRequest(approval.getId());

        assertEquals("APPROVED", result.getStatus());
        assertEquals("APPROVED", employee.getStatus());
        assertEquals(LocalDate.now(), result.getDecisionDate());
    }

    @Test
    void approvalShouldGenerateWelcomeNotification() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        List<Notification> notifications =
                onboardingService.getNotificationsByEmployee(employee.getId());

        assertEquals(1, notifications.size());
        assertEquals("WELCOME", notifications.get(0).getType());
        assertEquals("GENERATED", notifications.get(0).getStatus());
        assertEquals("john@example.com",
                notifications.get(0).getRecipient());
    }

    @Test
    void managerRejectionShouldSaveComments() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        Approval result = onboardingService.rejectRequest(
                approval.getId(),
                "Joining date needs confirmation"
        );

        assertEquals("REJECTED", result.getStatus());
        assertEquals("REJECTED", employee.getStatus());
        assertEquals("Joining date needs confirmation",
                result.getComments());
    }

    @Test
    void rejectionShouldRequireComments() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        assertThrows(ResponseStatusException.class, () ->
                onboardingService.rejectRequest(approval.getId(), " ")
        );

        assertEquals("PENDING", approval.getStatus());
        assertEquals("PENDING_APPROVAL", employee.getStatus());
    }

    @Test
    void processedApprovalCannotBeApprovedAgain() {
        registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        assertThrows(ResponseStatusException.class, () ->
                onboardingService.approveRequest(approval.getId())
        );
    }

    @Test
    void taskCannotBeCompletedBeforeManagerApproval() {
        Employee employee = registerTestEmployee();
        OnboardingTask task =
                onboardingService.getTasksByEmployee(employee.getId()).get(0);

        assertThrows(ResponseStatusException.class, () ->
                onboardingService.updateTaskStatus(task.getId(), "COMPLETED")
        );

        assertEquals("PENDING", task.getStatus());
    }

    @Test
    void taskCanBeCompletedAfterManagerApproval() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        OnboardingTask task =
                onboardingService.getTasksByEmployee(employee.getId()).get(0);

        OnboardingTask result =
                onboardingService.updateTaskStatus(task.getId(), "COMPLETED");

        assertEquals("COMPLETED", result.getStatus());
    }

    @Test
    void completedTaskCannotBeCompletedAgain() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        OnboardingTask task =
                onboardingService.getTasksByEmployee(employee.getId()).get(0);

        onboardingService.updateTaskStatus(task.getId(), "COMPLETED");

        assertThrows(ResponseStatusException.class, () ->
                onboardingService.updateTaskStatus(task.getId(), "COMPLETED")
        );
    }

    @Test
    void invalidTaskStatusShouldBeRejected() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        OnboardingTask task =
                onboardingService.getTasksByEmployee(employee.getId()).get(0);

        assertThrows(ResponseStatusException.class, () ->
                onboardingService.updateTaskStatus(task.getId(), "IN_PROGRESS")
        );

        assertEquals("PENDING", task.getStatus());
    }

    @Test
    void dashboardShouldShowCorrectInitialProgress() {
        Employee employee = registerTestEmployee();

        var dashboard =
                onboardingService.getEmployeeDashboard(employee.getId());

        assertEquals(5, dashboard.getTotalTasks());
        assertEquals(0, dashboard.getCompletedTasks());
        assertEquals(5, dashboard.getPendingTasks());
        assertEquals(0, dashboard.getOverdueTasks());
        assertEquals(0.0, dashboard.getProgressPercentage());
    }

    @Test
    void dashboardShouldUpdateProgressAfterTaskCompletion() {
        Employee employee = registerTestEmployee();
        Approval approval =
                onboardingService.getPendingApprovals().get(0);

        onboardingService.approveRequest(approval.getId());

        OnboardingTask task =
                onboardingService.getTasksByEmployee(employee.getId()).get(0);

        onboardingService.updateTaskStatus(task.getId(), "COMPLETED");

        var dashboard =
                onboardingService.getEmployeeDashboard(employee.getId());

        assertEquals(5, dashboard.getTotalTasks());
        assertEquals(1, dashboard.getCompletedTasks());
        assertEquals(4, dashboard.getPendingTasks());
        assertEquals(20.0, dashboard.getProgressPercentage());
        @Test
        void dashboardShouldCountOverduePendingTasks() {
        Employee employee = registerTestEmployee();

        OnboardingTask task =
            onboardingService.getTasksByEmployee(employee.getId()).get(0);

            task.setDueDate(LocalDate.now().minusDays(1));

        var dashboard =
            onboardingService.getEmployeeDashboard(employee.getId());

        assertEquals(5, dashboard.getTotalTasks());
        assertEquals(5, dashboard.getPendingTasks());
        assertEquals(1, dashboard.getOverdueTasks());
        assertEquals(0, dashboard.getCompletedTasks());
        assertEquals(0.0, dashboard.getProgressPercentage());
    }
}
