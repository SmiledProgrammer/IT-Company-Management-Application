package com.szinton.companymanager.view;

import com.szinton.companymanager.domain.ProjectMembership;
import com.szinton.companymanager.service.ProjectMembershipService;
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
public class ProjectMembershipController {

    private final ProjectMembershipService projectMembershipService;

    @GetMapping("/project-memberships/add")
    public String addProjectMembershipView(Model model) {
        model.addAttribute("projectMembership", new ProjectMembership());
        return "project-memberships/add";
    }

    @PostMapping("/project-memberships/add")
    public String addProjectMembership(ProjectMembership projectMembership) {
        projectMembershipService.saveProjectMembership(projectMembership);
        return "redirect:/project-memberships";
    }

    @GetMapping("/project-memberships")
    public String projectMembershipsView(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<ProjectMembership> projectMembershipsPage = projectMembershipService.getProjectMembershipsPage(page - 1, size).toList();
        long pageCount = projectMembershipService.getProjectMembershipCount();
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * size) < pageCount;
        long prev = page - 1;
        long next = page + 1;
        model.addAttribute("projectMemberships", projectMembershipsPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "project-memberships/list";
    }

    @GetMapping("/project-memberships/{id}")
    public String updateProjectMembershipView(@PathVariable Long id, Model model) {
        ProjectMembership projectMembership = projectMembershipService.getProjectMembership(id);
        model.addAttribute("projectMembership", projectMembership);
        return "project-memberships/update";
    }

    @PostMapping("/project-memberships/update/{id}")
    public String updateProjectMembership(@PathVariable Long id, ProjectMembership projectMembership) {
        projectMembershipService.updateProjectMembership(id, projectMembership);
        return "redirect:/project-memberships";
    }

    @GetMapping("/project-memberships/delete/{id}")
    public String deleteProjectMembership(@PathVariable Long id) {
        projectMembershipService.deleteProjectMembership(id);
        return "redirect:/project-memberships";
    }
}
