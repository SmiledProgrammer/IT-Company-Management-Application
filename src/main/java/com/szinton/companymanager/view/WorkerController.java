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

    @GetMapping("/workers/add")
    public String addWorkerView(Model model) {
        model.addAttribute("worker", new Worker());
        return "workers/add";
    }

    @PostMapping("/workers/add")
    public String addWorker(Worker worker) {
        workerService.saveWorker(worker);
        return "redirect:/workers";
    }

    @GetMapping("/workers")
    public String workersView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Worker> workersPage = workerService.getWorkersPage(page - 1, size).toList();
        long pageCount = workerService.getWorkerCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        long prev = page - 1;
        long next = page + 1;
        model.addAttribute("workers", workersPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "workers/list";
    }

    @GetMapping("/workers/{id}")
    public String updateWorkerView(@PathVariable Long id, Model model) {
        Worker worker = workerService.getWorker(id);
        model.addAttribute("worker", worker);
        return "workers/update";
    }

    @PostMapping("/workers/update/{id}")
    public String updateWorker(@PathVariable Long id, Worker worker) {
        workerService.updateWorker(id, worker);
        return "redirect:/workers";
    }

    @GetMapping("/workers/delete/{id}")
    public String deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return "redirect:/workers";
    }
}
