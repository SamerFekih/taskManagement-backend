package com.example.taskmanagement.models.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
    private String category;
}
