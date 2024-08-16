package com.javarush.ikolybaba.controller;


import com.javarush.ikolybaba.domain.Task;
import com.javarush.ikolybaba.dto.TaskDTO;
import com.javarush.ikolybaba.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

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
        model.addAttribute("currentPage", page);
        int totalPages = (int) Math.ceil(1.0 * service.getAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }

        return "tasks";
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(Model model,
                                        @PathVariable("id") Integer id,
                                        @RequestBody TaskDTO taskDto) {
        System.out.println("Edit");
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id.");
        }

        try {
            Task task = service.edit(id, taskDto.getDescription(), taskDto.getStatus());
            return ResponseEntity.ok(taskDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
