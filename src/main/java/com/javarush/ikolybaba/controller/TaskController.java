package com.javarush.ikolybaba.controller;


import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("/start")
    public String hello() {
        return "Hello Spring Project";
    }

    @GetMapping("/task-{id}")
    public Task getTask(@PathVariable Integer id) {
        return service.getTaskById(id);
    }

    @GetMapping("/tasks")
    public List<Task> getAll() {
        return service.getAllTasks();
    }
}
