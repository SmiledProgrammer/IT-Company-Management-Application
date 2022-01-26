package com.szinton.companymanager.view;

import com.szinton.companymanager.domain.Client;
import com.szinton.companymanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients/add")
    public String addClientView(Model model) {
        model.addAttribute("client", new Client());
        return "clients/add";
    }

    @PostMapping("/clients/add")
    public String addClient(Client client) {
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients")
    public String clientsView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Client> clientsPage = clientService.getClientsPage(page - 1, size).toList();
        long pageCount = clientService.getClientCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        long prev = page - 1;
        long next = page + 1;
        model.addAttribute("clients", clientsPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "clients/list";
    }

    @GetMapping("/clients/{id}")
    public String updateClientView(@PathVariable Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("client", client);
        return "clients/update";
    }

    @PostMapping("/clients/update/{id}")
    public String updateClient(@PathVariable Long id, Client client) {
        clientService.updateClient(id, client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
}
