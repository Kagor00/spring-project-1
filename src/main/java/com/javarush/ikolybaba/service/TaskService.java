package com.javarush.ikolybaba.service;

import com.javarush.ikolybaba.domain.Status;
import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Task getTaskById(Integer id) {
        Optional<Task> taskOptional = repository.findById(id);
        return taskOptional.orElseGet(Task::new);
   }

   public List<Task> getTasksFromPage(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       return repository.findAll(pageable).getContent();
   }

   public List<Task> getAllTasks() {
      return repository.findAll();
   }

   public Task createTask(String description, Status status) {
       return repository.save(new Task(description, status));
   }

   public void deleteTaskById(Integer id) {
        repository.deleteById(id);
   }

}
