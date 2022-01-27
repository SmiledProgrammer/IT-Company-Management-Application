package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.ProjectMembership;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.ProjectMembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMembershipService {

    private final ProjectMembershipRepository projectMembershipRepository;

    public void saveProjectMembership(ProjectMembership projectMembership) {
        projectMembershipRepository.save(projectMembership);
    }

    public ProjectMembership getProjectMembership(Long id) {
        return projectMembershipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("ProjectMembership with the specified identifier doesn't exist."));
    }

    public Page<ProjectMembership> getProjectMembershipsPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return projectMembershipRepository.findAll(pageable);
    }

    public void updateProjectMembership(Long id, ProjectMembership projectMembership) {
        if (!id.equals(projectMembership.getId())) {
            throw new IllegalArgumentException("ProjectMembership identifiers from path and body are mismatching.");
        }
        ProjectMembership record = projectMembershipRepository.findById(projectMembership.getId()).orElseThrow(() ->
                new ResourceNotFoundException("ProjectMembership with the specified identifier doesn't exist."));
        record.setWorker(projectMembership.getWorker());
        record.setProject(projectMembership.getProject());
        projectMembershipRepository.save(record);
    }

    public void deleteProjectMembership(Long id) {
        projectMembershipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("ProjectMembership with the specified identifier doesn't exist."));
        projectMembershipRepository.deleteById(id);
    }

    public long getProjectMembershipCount() {
        return projectMembershipRepository.count();
    }
}
