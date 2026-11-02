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
}
