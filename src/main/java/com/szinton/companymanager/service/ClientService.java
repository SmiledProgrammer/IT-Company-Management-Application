package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.Client;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client with the specified identifier doesn't exist."));
    }

    public Page<Client> getClientsPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return clientRepository.findAll(pageable);
    }

    public void updateClient(Long id, Client client) {
        if (!id.equals(client.getId())) {
            throw new IllegalArgumentException("Client identifiers from path and body are mismatching.");
        }
        Client record = clientRepository.findById(client.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Client with the specified identifier doesn't exist."));
        record.setFirstName(client.getFirstName());
        record.setLastName(client.getLastName());
        record.setCompany(client.getCompany());
        record.setEmail(client.getEmail());
        record.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(record);
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client with the specified identifier doesn't exist."));
        clientRepository.deleteById(id);
    }

    public long getClientCount() {
        return clientRepository.count();
    }
}
