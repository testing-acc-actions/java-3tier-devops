package com.company.employee.repository;

import com.company.employee.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository
        extends JpaRepository<Approval, Long> {

    List<Approval> findByStatus(String status);

    Optional<Approval> findByEmployee_Id(Long employeeId);
}
