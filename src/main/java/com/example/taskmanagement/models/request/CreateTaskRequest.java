package com.example.taskmanagement.models.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String category;
}
