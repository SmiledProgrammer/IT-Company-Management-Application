package com.szinton.companymanager.service;

import com.szinton.companymanager.domain.Worker;
import com.szinton.companymanager.exception.ResourceNotFoundException;
import com.szinton.companymanager.repo.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker getWorker(Long id) {
        return workerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Worker with the specified identifier doesn't exist."));
    }

    public Page<Worker> getWorkersPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return workerRepository.findAll(pageable);
    }

    public Worker updateWorker(Long id, Worker worker) {
        if (!id.equals(worker.getId())) {
            throw new IllegalArgumentException("Worker identifiers from path and body are mismatching.");
        }
        Worker record = workerRepository.findById(worker.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Worker with the specified identifier doesn't exist."));
        record.setFirstName(worker.getFirstName());
        record.setLastName(worker.getLastName());
        record.setJob(worker.getJob());
        record.setSalary(worker.getSalary());
        record.setEmail(worker.getEmail());
        record.setPhoneNumber(worker.getPhoneNumber());
        return workerRepository.save(record);
    }

    public void deleteWorker(Long id) {
        workerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Worker with the specified identifier doesn't exist."));
        workerRepository.deleteById(id);
    }

    public long getWorkerCount() {
        return workerRepository.count();
    }
}
