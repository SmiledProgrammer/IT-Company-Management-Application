package com.szinton.companymanager.repo;

import com.szinton.companymanager.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
