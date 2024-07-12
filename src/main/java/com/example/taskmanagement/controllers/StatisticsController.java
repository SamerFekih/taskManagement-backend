package com.example.taskmanagement.controllers;

import com.example.taskmanagement.models.response.TaskPriorityStatisticsResponse;
import com.example.taskmanagement.services.AuthService;
import com.example.taskmanagement.services.StatisticsService;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.models.response.TasksStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final TaskService taskService;
    private final AuthService authService;
    private final StatisticsService statisticsService;
    @Autowired
    public StatisticsController(TaskService taskService, StatisticsService statisticsService,AuthService authService) {
        this.taskService = taskService;
        this.statisticsService = statisticsService;
        this.authService = authService;
    }

    /**
     * Endpoint to get task status statistics for the authenticated user.
     *
     * @return ResponseEntity containing the task status statistics.
     */
    @GetMapping("/status")
    public ResponseEntity<TasksStatisticsResponse> getTaskStatusStatistics() {
        String username = authService.getCurrentUsername();
        TasksStatisticsResponse statistics = taskService.getTaskStatusStatistics(username);
        return ResponseEntity.ok(statistics);
    }
    /**
     * Endpoint to get task priority statistics per category for the authenticated user.
     *
     * @return ResponseEntity containing the task priority statistics per category.
     */
    @GetMapping("/priority-per-category")
    public ResponseEntity<TaskPriorityStatisticsResponse> getTaskPriorityStatistics() {
        String username = authService.getCurrentUsername();
        TaskPriorityStatisticsResponse response = statisticsService.getTaskPriorityStatistics(username);
        return ResponseEntity.ok(response);
    }


}