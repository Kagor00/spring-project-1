package com.javarush.ikolybaba.controller;


import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.dto.TaskDTO;
import com.javarush.ikolybaba.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;


    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = service.getTasksFromPage(page, limit);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }


    @PutMapping("/{id}")
    public String edit(Model model,
                     @PathVariable Integer id,
                     @RequestBody TaskDTO taskDto) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id.");
        }

        Task task = service.edit(id, taskDto.getDescription(), taskDto.getStatus());
        return tasks(model, 1, 10);
    }


    @PostMapping("/")
    public String add(Model model, @RequestBody TaskDTO taskDto) {
        Task task = service.create(taskDto.getDescription(), taskDto.getStatus());
        return tasks(model, 1, 10);

    }

    @DeleteMapping("{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id.");
        }

        service.delete(id);

        return tasks(model, 1, 10);
    }

}
