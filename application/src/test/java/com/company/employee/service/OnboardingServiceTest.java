package com.company.employee.service;

import com.company.employee.model.Employee;
import com.company.employee.model.OnboardingTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnboardingServiceTest {

    private OnboardingService onboardingService;

    @BeforeEach
    void setUp() {
        onboardingService = new OnboardingService();
    }

    @Test
    void registeringEmployeeShouldGenerateFiveTasks() {

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

        Employee registeredEmployee =
                onboardingService.registerEmployee(employee);

        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(
                        registeredEmployee.getId()
                );

        assertNotNull(registeredEmployee.getId());
        assertEquals("PENDING_APPROVAL", registeredEmployee.getStatus());
        assertEquals(5, tasks.size());
    }

    @Test
    void generatedTasksShouldHaveCorrectAssignments() {

        Employee employee = new Employee(
                null,
                "Jane Smith",
                "jane@example.com",
                "IT",
                "Developer",
                LocalDate.now().plusDays(7),
                "Sarah Manager",
                null
        );

        Employee registeredEmployee =
                onboardingService.registerEmployee(employee);

        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(
                        registeredEmployee.getId()
                );

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

        Employee employee = new Employee(
                null,
                "Alex Brown",
                "alex@example.com",
                "Finance",
                "Analyst",
                LocalDate.now().plusDays(7),
                "Sarah Manager",
                null
        );

        Employee registeredEmployee =
                onboardingService.registerEmployee(employee);

        List<OnboardingTask> tasks =
                onboardingService.getTasksByEmployee(
                        registeredEmployee.getId()
                );

        assertTrue(tasks.stream()
                .allMatch(task -> "PENDING".equals(task.getStatus())));
    }
}
