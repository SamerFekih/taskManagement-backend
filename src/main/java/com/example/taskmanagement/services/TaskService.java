
package com.example.taskmanagement.services;

import com.example.taskmanagement.mappers.TaskDTOMapper;
import com.example.taskmanagement.models.request.CreateTaskRequest;
import com.example.taskmanagement.models.request.DeleteTasksRequest;
import com.example.taskmanagement.models.request.UpdateTaskRequest;
import com.example.taskmanagement.models.response.TaskResponse;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.models.response.TasksStatisticsResponse;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskDTOMapper taskDTOMapper;
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskDTOMapper taskDTOMapper){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskDTOMapper = taskDTOMapper;
    }
    public List<TaskResponse> getTasksForCurrentUser() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findByUser(user);

        return tasks.stream().map(taskDTOMapper::convertToTaskDTO).collect(Collectors.toList());
    }


    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());
        task.setDueDate(createTaskRequest.getDueDate());
        task.setPriority(createTaskRequest.getPriority());
        task.setStatus("Pending");
        task.setCategory(createTaskRequest.getCategory());
        task.setUser(user);

        taskRepository.save(task);

        return taskDTOMapper.convertToTaskDTO(task);
    }
    public TaskResponse updateTask(UpdateTaskRequest updateTaskRequest) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Task> optionalTask = taskRepository.findById(updateTaskRequest.getId());
        if (optionalTask.isPresent() && optionalTask.get().getUser().equals(user)) {
            Task task = optionalTask.get();
            task.setStatus(updateTaskRequest.getStatus());
            task.setPriority(updateTaskRequest.getPriority());
            taskRepository.save(task);
            return taskDTOMapper.convertToTaskDTO(task);
        } else {
            throw new RuntimeException("Task not found or user not authorized");
        }
    }
    public void deleteTasks(DeleteTasksRequest deleteTasksRequest) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        taskRepository.deleteByTaskIdsAndUserId(deleteTasksRequest.getTaskIds(),user.getId());
    }

    public TasksStatisticsResponse getTaskStatusStatistics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findByUser(user);
        Map<String, Long> statusCount = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        return new TasksStatisticsResponse(statusCount);
    }
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }



}