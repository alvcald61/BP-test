package com.bp.fullstackbp.repository;

import com.bp.fullstackbp.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
  List<Client> findAllByIsActiveIsTrue();
}
