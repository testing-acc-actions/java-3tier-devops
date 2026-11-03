package com.company.employee.repository;

import com.company.employee.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByEmployee_Id(Long employeeId);

    List<Notification> findByEmployee_IdAndStatus(
            Long employeeId,
            String status
    );
}
