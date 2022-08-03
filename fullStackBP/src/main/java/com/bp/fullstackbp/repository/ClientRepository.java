package com.bp.fullstackbp.repository;

import com.bp.fullstackbp.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
