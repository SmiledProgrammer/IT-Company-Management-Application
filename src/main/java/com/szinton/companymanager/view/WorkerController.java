package com.szinton.companymanager.view;

import com.szinton.companymanager.domain.Worker;
import com.szinton.companymanager.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping("/workers")
    public String getWorkersView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Worker> workersPage = workerService.getWorkersPage(page, size).toList();
        long pageCount = workerService.getWorkerCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        model.addAttribute("workers", workersPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        return "workers";
    }
}
