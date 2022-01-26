package com.szinton.companymanager.repo;

import com.szinton.companymanager.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
