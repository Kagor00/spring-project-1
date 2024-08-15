package com.javarush.ikolybaba.service;

import com.javarush.ikolybaba.domain.Status;
import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.repository.TaskRepository;
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
    public Task getTaskById(Integer id) {
        Optional<Task> taskOptional = repository.findById(id);
        return taskOptional.orElseGet(Task::new);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksFromPage(int pageIndex, int limit) {
        Pageable pageable = PageRequest.of(pageIndex, limit);
        return repository.findAll(pageable).getContent();
    }


    @Transactional
    public Task createTask(String description, Status status) {
        return repository.save(new Task(description, status));
    }

}
