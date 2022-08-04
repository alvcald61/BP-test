package com.bp.fullstackbp.repository;

import com.bp.fullstackbp.entities.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

  @Query(value = "SELECT b FROM Movement m INNER JOIN m.bankAccount as b " +
    "WHERE b.client.id = ?1 AND m.transactionDate >= ?2 AND m.transactionDate <= ?3")
  Page<Movement> getReport(Long clientId, Date startDate, Date endDate, Pageable pageable);

  List<Movement> findAllByIsActiveIsTrue();
}
