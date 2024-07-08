
package com.example.taskmanagement.services;

import com.example.taskmanagement.mappers.TaskDTOMapper;
import com.example.taskmanagement.models.request.CreateTaskRequest;
import com.example.taskmanagement.models.request.DeleteTasksRequest;
import com.example.taskmanagement.models.response.TaskResponse;
import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
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

    public void deleteTasks(DeleteTasksRequest deleteTasksRequest) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        taskRepository.deleteByTaskIdsAndUserId(deleteTasksRequest.getTaskIds(),user.getId());
    }
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }



}