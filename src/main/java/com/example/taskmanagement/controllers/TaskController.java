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

    /**
     * Endpoint to get tasks for the current authenticated user.
     *
     * @return ResponseEntity containing a list of task responses.
     */
    @GetMapping("/getTasks")
    public ResponseEntity<List<TaskResponse>> getTasksForCurrentUser(){
        List<TaskResponse> tasks = taskService.getTasksForCurrentUser();
        return ResponseEntity.ok(tasks);
    }
    /**
     * Endpoint to create a new task.
     *
     * @param createTaskRequest Request body containing details of the task to be created.
     * @return ResponseEntity containing the created task response.
     */
    @PostMapping("/addTask")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        TaskResponse taskResponse = taskService.createTask(createTaskRequest);
        return ResponseEntity.ok(taskResponse);
    }
    /**
     * Endpoint to update the status and priority of an existing task.
     *
     * @param updateTaskRequest Request body containing status and priority of the task to be updated.
     * @return ResponseEntity containing the updated task response.
     */
    @PutMapping("/editTask")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest) {
        TaskResponse updatedTask = taskService.updateTask(updateTaskRequest);
        return ResponseEntity.ok(updatedTask);
    }
    /**
     * Endpoint to delete tasks based on given task IDs.
     *
     * @param deleteTasksRequest Request body containing IDs of tasks to be deleted.
     * @return ResponseEntity containing a confirmation message.
     */
    @DeleteMapping("/deleteTasks")
    public ResponseEntity<String> deleteTasks(@RequestBody DeleteTasksRequest deleteTasksRequest) {
        taskService.deleteTasks(deleteTasksRequest);
        return ResponseEntity.ok("Task(s) deleted successfully");
    }

}