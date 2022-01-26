package com.szinton.companymanager.view;

import com.szinton.companymanager.domain.Project;
import com.szinton.companymanager.service.ProjectService;
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
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects/add")
    public String addProjectView(Model model) {
        model.addAttribute("project", new Project());
        return "projects/add";
    }

    @PostMapping("/projects/add")
    public String addProject(Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String projectsView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<Project> projectsPage = projectService.getProjectsPage(page - 1, size).toList();
        long pageCount = projectService.getProjectCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        long prev = page - 1;
        long next = page + 1;
        model.addAttribute("projects", projectsPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "projects/list";
    }

    @GetMapping("/projects/{id}")
    public String updateProjectView(@PathVariable Long id, Model model) {
        Project project = projectService.getProject(id);
        model.addAttribute("project", project);
        return "projects/update";
    }

    @PostMapping("/projects/update/{id}")
    public String updateProject(@PathVariable Long id, Project project) {
        projectService.updateProject(id, project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
