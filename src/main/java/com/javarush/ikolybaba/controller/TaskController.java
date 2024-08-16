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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> edit(@PathVariable("id") Integer id,
                                  @RequestBody TaskDTO taskDto) {
        System.out.println("Edit");
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id.");
        }

        try {
            service.edit(id, taskDto.getDescription(), taskDto.getStatus());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Task by id " + id + " updated successfully.");
            response.put("task", taskDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody TaskDTO taskDto) {
        try {
            service.create(taskDto.getDescription(), taskDto.getStatus());
            return ResponseEntity.ok(taskDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id.");
        }

        try {
            service.delete(id);
            return ResponseEntity.ok("Task by id " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
