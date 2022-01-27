package com.szinton.companymanager.view;

import com.szinton.companymanager.domain.IdentityCard;
import com.szinton.companymanager.service.IdentityCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IdentityCardController {

    private final IdentityCardService identityCardService;

    @GetMapping("/identity-cards/add")
    public String addIdentityCardView(Model model) {
        model.addAttribute("identityCard", new IdentityCard());
        return "identity-cards/add";
    }

    @PostMapping("/identity-cards/add")
    public String addIdentityCard(IdentityCard identityCard) {
        identityCardService.saveIdentityCard(identityCard);
        return "redirect:/identity-cards";
    }

    @GetMapping("/identity-cards")
    public String identityCardsView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<IdentityCard> identityCardsPage = identityCardService.getIdentityCardsPage(page - 1, size).toList();
        long pageCount = identityCardService.getIdentityCardCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        long prev = page - 1;
        long next = page + 1;
        model.addAttribute("identityCards", identityCardsPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "identity-cards/list";
    }

    @GetMapping("/identity-cards/{id}")
    public String updateIdentityCardView(@PathVariable Long id, Model model) {
        IdentityCard identityCard = identityCardService.getIdentityCard(id);
        model.addAttribute("identityCard", identityCard);
        return "identity-cards/update";
    }

    @PostMapping("/identity-cards/update/{id}")
    public String updateIdentityCard(@PathVariable Long id, IdentityCard identityCard) {
        identityCardService.updateIdentityCard(id, identityCard);
        return "redirect:/identity-cards";
    }

    @GetMapping("/identity-cards/delete/{id}")
    public String deleteIdentityCard(@PathVariable Long id) {
        identityCardService.deleteIdentityCard(id);
        return "redirect:/identity-cards";
    }
}
