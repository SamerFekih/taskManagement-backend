package com.example.taskmanagement.controllers;

import com.example.taskmanagement.models.request.CreateTaskRequest;
import com.example.taskmanagement.models.request.DeleteTasksRequest;
import com.example.taskmanagement.models.request.UpdateTaskRequest;
import com.example.taskmanagement.models.response.TaskResponse;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.models.response.TasksStatisticsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final TaskService taskService;

    public StatisticsController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/tasks-status")
    public ResponseEntity<TasksStatisticsResponse> getTaskStatusStatistics() {
        String username = taskService.getCurrentUsername();
        TasksStatisticsResponse statistics = taskService.getTaskStatusStatistics(username);
        return ResponseEntity.ok(statistics);
    }



}