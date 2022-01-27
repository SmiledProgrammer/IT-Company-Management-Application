package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.IdentityCard;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.IdentityCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityCardService {

    private final IdentityCardRepository identityCardRepository;

    public void saveIdentityCard(IdentityCard identityCard) {
        identityCardRepository.save(identityCard);
    }

    public IdentityCard getIdentityCard(Long id) {
        return identityCardRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("IdentityCard with the specified identifier doesn't exist."));
    }

    public Page<IdentityCard> getIdentityCardsPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return identityCardRepository.findAll(pageable);
    }

    public void updateIdentityCard(Long id, IdentityCard identityCard) {
        if (!id.equals(identityCard.getId())) {
            throw new IllegalArgumentException("IdentityCard identifiers from path and body are mismatching.");
        }
        IdentityCard record = identityCardRepository.findById(identityCard.getId()).orElseThrow(() ->
                new ResourceNotFoundException("IdentityCard with the specified identifier doesn't exist."));
        record.setActivationDate(identityCard.getActivationDate());
        record.setOwner(identityCard.getOwner());
        identityCardRepository.save(record);
    }

    public void deleteIdentityCard(Long id) {
        identityCardRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("IdentityCard with the specified identifier doesn't exist."));
        identityCardRepository.deleteById(id);
    }

    public long getIdentityCardCount() {
        return identityCardRepository.count();
    }
}
