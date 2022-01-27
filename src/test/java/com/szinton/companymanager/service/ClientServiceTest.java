package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.Client;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @MockBean
    ClientRepository clientRepository;

    private static Client dummyClient() {
        return new Client(
                1L, "John", "Smith",
                "Googletflix",
                "john.smith@googletflix.com",
                "123456785"
        );
    }

    @Test
    void saveClient_success() {
        Client client = dummyClient();
        client.setId(null);
        Mockito.doReturn(dummyClient()).when(clientRepository).save(client);

        clientService.saveClient(client);

        Mockito.verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getClient_noClientWithTheId_throwResourceNotFoundException() {
        long id = 2L;
        Mockito.doThrow(new ResourceNotFoundException("")).when(clientRepository).findById(id);

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClient(id));
        Mockito.verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void getClient_success() {
        long id = 1L;
        Optional<Client> clientOptional = Optional.of(dummyClient());
        Mockito.doReturn(clientOptional).when(clientRepository).findById(id);

        Client client = clientService.getClient(id);

        assertEquals(dummyClient(), client);
        Mockito.verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void getClientsPage_success() {
        int page = 0;
        int pageSize = 5;
        Page<Client> expected = Page.empty();
        Pageable pageable = PageRequest.of(page, pageSize);
        Mockito.doReturn(expected).when(clientRepository).findAll(pageable);

        Page<Client> actual = clientService.getClientsPage(page, pageSize);

        assertEquals(expected, actual);
        Mockito.verify(clientRepository).findAll(pageable);
    }

    @Test
    void updateClient_mismatchingIds_throwIllegalArgumentException() {
        long id = 5L;
        Client client = dummyClient();

        assertThrows(IllegalArgumentException.class, () -> clientService.updateClient(id, client));
    }

    @Test
    void updateClient_noClientWithTheId_throwResourceNotFoundException() {
        long id = 1L;
        Client client = dummyClient();
        Mockito.doThrow(new ResourceNotFoundException("")).when(clientRepository).findById(id);

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(id, client));
        Mockito.verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void updateClient_success() {
        long id = 1L;
        Client old = dummyClient();
        Client expected = dummyClient();
        expected.setFirstName("Will");
        Optional<Client> clientOptional = Optional.of(old);
        Mockito.doReturn(clientOptional).when(clientRepository).findById(id);
        Mockito.doReturn(expected).when(clientRepository).save(expected);

        clientService.updateClient(id, expected);

        Mockito.verify(clientRepository, times(1)).findById(id);
        Mockito.verify(clientRepository, times(1)).save(expected);
    }

    @Test
    void deleteClient_noClientWithTheId_throwResourceNotFoundException() {
        long id = 1L;
        Mockito.doThrow(new ResourceNotFoundException("")).when(clientRepository).findById(id);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(id));
        Mockito.verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void deleteClient_success() {
        long id = 1L;
        Optional<Client> clientOptional = Optional.of(dummyClient());
        Mockito.doReturn(clientOptional).when(clientRepository).findById(id);
        Mockito.doNothing().when(clientRepository).deleteById(id);

        clientService.deleteClient(id);

        Mockito.verify(clientRepository, times(1)).findById(id);
        Mockito.verify(clientRepository, times(1)).deleteById(id);
    }
}
