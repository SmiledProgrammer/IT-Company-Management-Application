package com.szinton.companymanager.repo;

import com.szinton.companymanager.domain.ProjectMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMembershipRepository extends JpaRepository<ProjectMembership, Long> {
}
