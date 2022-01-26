package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.Client;
import com.szinton.companymanager.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return null;
    }

    public Client getClient(Long id) {
        return null;
    }

    public Page<Client> getClientsPage(int page, int pageSize) {
        return null;
    }

    public Client updateClient(Long id, Client client) {
        return null;
    }

    public void deleteClient(Long id) {

    }
}
