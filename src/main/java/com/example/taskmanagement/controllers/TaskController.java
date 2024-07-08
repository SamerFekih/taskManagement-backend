package com.example.taskmanagement.controllers;

import com.example.taskmanagement.models.request.CreateTaskRequest;
import com.example.taskmanagement.models.request.DeleteTasksRequest;
import com.example.taskmanagement.models.request.UpdateTaskRequest;
import com.example.taskmanagement.models.response.TaskResponse;
import com.example.taskmanagement.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/getTasks")
    public ResponseEntity<List<TaskResponse>> getTasksForCurrentUser(){
        List<TaskResponse> tasks = taskService.getTasksForCurrentUser();
        return ResponseEntity.ok(tasks);
    }
    @PostMapping("/addTask")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        TaskResponse taskDTO = taskService.createTask(createTaskRequest);
        return ResponseEntity.ok(taskDTO);
    }

    @PutMapping("/editTask")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest) {
        TaskResponse updatedTask = taskService.updateTask(updateTaskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/deleteTasks")
    public ResponseEntity<String> deleteTasks(@RequestBody DeleteTasksRequest deleteTasksRequest) {
        taskService.deleteTasks(deleteTasksRequest);
        return ResponseEntity.ok("Task(s) deleted successfully");
    }

}