package com.example.taskmanagement.controllers;

import com.example.taskmanagement.models.response.TaskPriorityStatisticsResponse;
import com.example.taskmanagement.services.StatisticsService;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.models.response.TasksStatisticsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final TaskService taskService;
    private final StatisticsService statisticsService;
    public StatisticsController(TaskService taskService, StatisticsService statisticsService) {
        this.taskService = taskService;
        this.statisticsService = statisticsService;
    }
    @GetMapping("/status")
    public ResponseEntity<TasksStatisticsResponse> getTaskStatusStatistics() {
        String username = taskService.getCurrentUsername();
        TasksStatisticsResponse statistics = taskService.getTaskStatusStatistics(username);
        return ResponseEntity.ok(statistics);
    }
    @GetMapping("/priority-per-category")
    public ResponseEntity<TaskPriorityStatisticsResponse> getTaskPriorityStatistics() {
        String username = taskService.getCurrentUsername();
        TaskPriorityStatisticsResponse response = statisticsService.getTaskPriorityStatistics(username);
        return ResponseEntity.ok(response);
    }


}