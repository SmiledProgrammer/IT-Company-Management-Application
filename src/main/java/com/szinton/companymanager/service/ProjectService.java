package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.Project;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project with the specified identifier doesn't exist."));
    }

    public Page<Project> getProjectsPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return projectRepository.findAll(pageable);
    }

    public void updateProject(Long id, Project project) {
        if (!id.equals(project.getId())) {
            throw new IllegalArgumentException("Project identifiers from path and body are mismatching.");
        }
        Project record = projectRepository.findById(project.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Project with the specified identifier doesn't exist."));
        record.setName(project.getName());
        record.setActive(project.isActive());
        record.setClient(project.getClient());
        projectRepository.save(record);
    }

    public void deleteProject(Long id) {
        projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project with the specified identifier doesn't exist."));
        projectRepository.deleteById(id);
    }

    public long getProjectCount() {
        return projectRepository.count();
    }
}
