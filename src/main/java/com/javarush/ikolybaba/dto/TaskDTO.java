package com.javarush.ikolybaba.dto;

import com.javarush.ikolybaba.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDTO {
    private String description;
    private Status status;
}
