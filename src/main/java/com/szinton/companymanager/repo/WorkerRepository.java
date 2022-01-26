package com.szinton.companymanager.repo;

import com.szinton.companymanager.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
