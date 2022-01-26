package com.szinton.companymanager.repo;

import com.szinton.companymanager.domain.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {
}
