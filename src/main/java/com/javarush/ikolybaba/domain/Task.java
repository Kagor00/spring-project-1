package com.javarush.ikolybaba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;
}
