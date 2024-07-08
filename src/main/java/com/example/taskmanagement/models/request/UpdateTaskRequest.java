package com.example.taskmanagement.models.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {
    private Long id;
    private String status;
    private String priority;
}
