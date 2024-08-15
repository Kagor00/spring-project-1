package com.javarush.ikolybaba.service;

import com.javarush.ikolybaba.domain.Status;
import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    @Transactional(readOnly = true)
    public Task getTaskById(int id) {
        Optional<Task> taskOptional = repository.findById(id);
        return taskOptional.orElseGet(Task::new);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksFromPage(int pageIndex, int limit) {
        Pageable pageable = PageRequest.of(pageIndex, limit);
        return repository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public int getAllCount() {
        return Math.toIntExact(repository.count());
    }

    @Transactional
    public Task create(String description, Status status) {
        return repository.save(new Task(description, status));
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        Optional<Task> taskOptional = repository.findById(id);
        Task task;

        if (taskOptional.isPresent()) {
            task = taskOptional.get();
            task.setDescription(description);
            task.setStatus(status);
            return repository.save(task);
        } else {
            throw new EntityNotFoundException("Task with ID " + id + " not found");
        }
    }


    @Transactional
    public void delete(int id) {
        Optional<Task> taskOptional = repository.findById(id);

        if (taskOptional.isPresent()) {
            repository.delete(taskOptional.get());
        } else {
            throw new EntityNotFoundException("Task with ID " + id + " not found");
        }
    }

}
